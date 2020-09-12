package org.zxp.redis.redpacket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zxp.redis.redpacket.dao.RedPacketInfoRepository;
import org.zxp.redis.redpacket.dao.RedPacketRecordRepository;
import org.zxp.redis.redpacket.domain.RedPacketRecord;
import org.zxp.redis.utils.RedisUtil;

import java.util.UUID;

/**
 * @program: redis
 * @description: 回写信息
 * @create: 2019-04-30 11:36
 **/
@Service
@Slf4j
public class RedPacketCallBackService {
    @Autowired
    private RedPacketInfoRepository redPacketInfoRepository;

    @Autowired
    private RedPacketRecordRepository redPacketRecordRepository;

    @Autowired
    RedisUtil redisUtil;

    public final String TOTAL_AMOUNT = "_TOTAL_AMOUNT";
    public final String TAL_PACKET = "_TAL_PACKET";

    /**
     * 回写红包信息表、抢红包表
     * 可以使用 RabbitMQ 进一步解耦
     */
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void callback(String userId, String redPacketId, int amount) throws Exception {
        log.info("用户：{}，抢到当前红包：{}，金额：{}，回写成功！", userId, redPacketId, amount);
        // 新增抢红包信息
        // 不能用自增ID，在高并发情况下 JPA + 自增ID 会造成死锁问题
        RedPacketRecord redPacketRecord = new RedPacketRecord().builder().user_id(userId).red_packet_id(redPacketId).amount(amount).build();
        redPacketRecord.setId(UUID.randomUUID().toString());
        redPacketRecordRepository.save(redPacketRecord);
//        List<AggsInfo> list = redPacketRecordRepository.getSumAmount(redPacketId);
//        RedPacketInfo redPacketInfo = redPacketInfoRepository.findByRedPacketId(redPacketId);
//        if(redPacketInfo.getTotal_amount() == Integer.parseInt(list.get(0).getValue() + "")){
//              不要在这里删除，否则影响没抢到红包的人
//            log.info("红包抢完，删除redis数据");
//            redisUtil.delete(redPacketId + TAL_PACKET);
//            redisUtil.delete(redPacketId + TOTAL_AMOUNT);
//            redisUtil.delete(redPacketId + "_BLOOM_GRAB_REDPACKET");
//        }
    }
}
