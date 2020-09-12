package org.zxp.redis.redpacket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.RandomValuePropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zxp.redis.redpacket.domain.GrabResult;
import org.zxp.redis.redpacket.domain.RedPacketInfo;
import org.zxp.redis.redpacket.service.RedPacketCallBackService;
import org.zxp.redis.redpacket.service.RedPacketService;

import java.util.Date;

/**
 * @program: redis
 * @create: 2019-04-30 10:30
 **/
@RestController
@Slf4j
public class RedPacketController {
    @Autowired
    RedPacketService redPacketService;
    @Autowired
    RandomValuePropertySource randomValuePropertySource;

    /**
     * 发红包
     *
     * @param user_id
     * @param total_count
     * @param totol_amount 单位元
     * @return
     */
    @GetMapping("/hand_out")
    public RedPacketInfo handOut(String user_id, int total_count, double totol_amount) {
        return redPacketService.handOut(user_id, Integer.parseInt(totol_amount * 100 + ""), total_count);
    }


    /**
     * 抢红包
     *
     * @param redPacketId
     * @return
     */
    @GetMapping("/grab")
    public GrabResult grab(String redPacketId) {
        String userId = "user_" + randomValuePropertySource.getProperty("random.int(10000)").toString();
        return redPacketService.grab(userId, redPacketId);
    }


    @Autowired
    RedPacketCallBackService redPacketCallBackService;

    @GetMapping("/concurrent")
    public String concurrent() {
        RedPacketInfo redPacketInfo = redPacketService.handOut("zxp", 1000, 20);
        String redPacketId = redPacketInfo.getRed_packet_id();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                String userId = "user_" + randomValuePropertySource.getProperty("random.int(10000)").toString();
                Date begin = new Date();
                GrabResult grabResult = redPacketService.grab(userId, redPacketId);
                Date end = new Date();
                log.info(grabResult.getMsg() + ",本次消耗：" + (end.getTime() - begin.getTime()));
            });
            thread.start();
        }
        return "ok";
    }

}
