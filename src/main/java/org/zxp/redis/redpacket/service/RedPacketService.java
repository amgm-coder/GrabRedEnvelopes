package org.zxp.redis.redpacket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.RandomValuePropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zxp.redis.redpacket.dao.RedPacketInfoRepository;
import org.zxp.redis.redpacket.domain.GrabResult;
import org.zxp.redis.redpacket.domain.RedPacketInfo;
import org.zxp.redis.redpacket.domain.RedPacketRecord;
import org.zxp.redis.utils.RedisUtil;

import java.util.Date;

/**
 * @program: redis
 * @description: 抢红包业务
 * @create: 2019-04-30 10:29
 **/
@Service
@Slf4j
public class RedPacketService {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    RandomValuePropertySource randomValuePropertySource;

    @Autowired
    RedPacketCallBackService redPacketCallBackService;

    @Autowired
    RedPacketInfoRepository redPacketInfoRepository;

    public final String TOTAL_AMOUNT = "_TOTAL_AMOUNT";
    public final String TAL_PACKET = "_TAL_PACKET";

    /**
     * 发红包
     *
     * @param userId
     * @param total_amount 单位为分，不允许有小数点
     * @param tal_packet
     * @return
     */
    @Transactional
    public RedPacketInfo handOut(String userId, int total_amount, int tal_packet) {
        RedPacketInfo redPacketInfo = new RedPacketInfo();
        redPacketInfo.setRed_packet_id(genRedPacketId(userId));
        redPacketInfo.setId(redPacketInfo.getRed_packet_id());
        redPacketInfo.setTotal_amount(total_amount);
        redPacketInfo.setTotal_packet(tal_packet);
        redPacketInfo.setUser_id(userId);
        redPacketInfoRepository.save(redPacketInfo);

        redisUtil.set(redPacketInfo.getRed_packet_id() + TAL_PACKET, tal_packet + "");
        redisUtil.set(redPacketInfo.getRed_packet_id() + TOTAL_AMOUNT, total_amount + "");

        return redPacketInfo;
    }

    /**
     * 抢红包
     *
     * @param userId
     * @param redPacketId
     * @return
     */
    public GrabResult grab(String userId, String redPacketId) {
        String msg = "很遗憾，红包已经被抢完";
        boolean resultFlag = false;
        double amountdb = 0.00;
        RedPacketRecord redPacketRecord = new RedPacketRecord().builder().red_packet_id(redPacketId).user_id(userId).build();
        // 抢红包的过程必须保证原子性，此处加分布式锁
        // 但是用分布式锁，阻塞时间太久，导致部分线程需要阻塞10s以上，性能非常不好
        // 如果没有红包了，则返回
        if (Integer.parseInt(redisUtil.get(redPacketId + TAL_PACKET)) > 0) {//有红包，才能有机会去真正的抢
            // 真正抢红包的过程，通过lua脚本处理保证原子性，并减少与redis交互的次数
            // lua脚本逻辑中包含了计算抢红包金额
            // 任何余额等瞬时信息都从这里快照取出，否则不准
            // 如果我们在这里分开写逻辑，不保证原子性的情况下有可能造成前面获取的金额后面用的时候红包已经不是原来获取金额时的情况了，并且多次与redis交互耗时严重
            String result = grubFromRedis(redPacketId + TAL_PACKET, redPacketId + TOTAL_AMOUNT, userId, redPacketId);
            // 准备返回结果
            if (!result.equals("0")) {
                String resultAmount = result.split("SPLIT")[0];
                String resultRemaining = result.split("SPLIT")[1];
                int amount = Integer.parseInt(resultAmount);
                redPacketRecord.setAmount(amount);
                amountdb = amount / 100.00;
                msg = "恭喜你抢到红包，红包金额" + amountdb + "元！"
                        +
                        ",剩余红包：" + (Integer.parseInt(resultRemaining) - 1) + "个";
                resultFlag = true;
                // 异步记账
                try {
                    redPacketCallBackService.callback(userId, redPacketId, amount);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            } else if (result.equals("0")) {
                redPacketRecord.setAmount(0);
                amountdb = 0.00;
                msg = "很遗憾，红包已经被抢完";
            } else if (result.equals("1")) {
                redPacketRecord.setAmount(0);
                amountdb = 0.00;
                msg = "您已经抢过红包";
            } else {
                redPacketRecord.setAmount(0);
                amountdb = 0.00;
                msg = "系统错误";
            }
        }
        return new GrabResult().builder().msg(msg).resultFlag(resultFlag).amount(amountdb).red_packet_id(redPacketId).user_id(userId).build();
    }

    /**
     * 组织红包ID
     *
     * @return
     */
    private String genRedPacketId(String userId) {
        String redpacketId = userId + "_" + new Date().getTime() + "_" + redisUtil.incrBy("redpacketid", 1);
        return redpacketId;
    }


    /**
     * 抢红包原子操作，返回本次抢红包金额
     *
     * @param packetCountKey  红包余量 key
     * @param packetAmountKey 红包余额 key
     * @param userId          用户ID 用于校验是否已经抢过红包
     * @param redPacketId     红包ID 用于校验是否已经抢过红包
     * @return
     */
    public String grubFromRedis(String packetCountKey, String packetAmountKey, String userId, String redPacketId) {
        DefaultRedisScript<String> redisScript;
        redisScript = new DefaultRedisScript();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("grub.lua")));
        redisScript.setResultType(String.class);
        return redisUtil.excuteLua(redisScript, packetCountKey, packetAmountKey, userId, redPacketId).toString();
    }
}
