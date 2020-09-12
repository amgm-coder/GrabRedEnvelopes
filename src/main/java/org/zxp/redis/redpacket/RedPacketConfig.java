package org.zxp.redis.redpacket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.env.RandomValuePropertySource;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.zxp.redis.redpacket.service.RedPacketService;

/**
 * @program: redis
 * @description: 抢红包demo相关配置，启动自动发红包+配置异步引入
 **/
@Component
@EnableAsync
public class RedPacketConfig implements ApplicationRunner {
    @Autowired
    RedPacketService redPacketService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        String userId = "001";
//        redPacketService.handOut(userId,10000,20);
    }

    /**
     * 引入随机数组件
     *
     * @return
     */
    @Bean
    public RandomValuePropertySource randomValuePropertySource() {
        return new RandomValuePropertySource("RedPackeRandom");
    }
}
