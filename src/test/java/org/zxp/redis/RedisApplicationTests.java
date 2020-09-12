package org.zxp.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.RandomValuePropertySource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zxp.redis.redpacket.dao.RedPacketInfoRepository;
import org.zxp.redis.redpacket.dao.RedPacketRecordRepository;
import org.zxp.redis.redpacket.domain.AggsInfo;
import org.zxp.redis.redpacket.service.RedPacketCallBackService;
import org.zxp.redis.redpacket.service.RedPacketService;
import org.zxp.redis.utils.RedisUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {
    @Autowired
    RedPacketInfoRepository redPacketInfoRepository;

    @Autowired
    RandomValuePropertySource randomValuePropertySource;

    @Autowired
    RedPacketService redPacketService;

    @Autowired
    RedPacketCallBackService redPacketCallBackService;
    @Autowired
    RedisUtil redisUtil;

    @Test
    public void testBloomFilter() {
        redisUtil.bloomFilterAddLua("myBloomFilter", "a");
        redisUtil.bloomFilterAddLua("myBloomFilter", "b");
        System.out.println(redisUtil.bloomFilterExistsLua("myBloomFilter", "a"));
        System.out.println(redisUtil.bloomFilterExistsLua("myBloomFilter", "b"));
        System.out.println(redisUtil.bloomFilterExistsLua("myBloomFilter", "c"));
    }

    @Test
    public void testMname1() {
        System.out.println("-2068||1".split("||"));
    }

    @Test
    public void testConcurrent() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testRedis() {
        System.out.println(redisUtil.get("zxp"));
        redisUtil.set("zxp2", "zxp2");

    }

    @Test
    public void testLock() {
        String key = "A42";
        boolean result = redisUtil.setIfAbsentEx(key, "b", 15, TimeUnit.SECONDS);
        System.out.println("获取锁结果：" + result);
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = redisUtil.unLock(key, "C");
        System.out.println("释放锁结果：" + result);
    }

    @Test
    public void testLockLua() {
        String key = "A42";
        boolean result = redisUtil.getLockLua(key, "b", "15");
        System.out.println("获取锁结果：" + result);
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = redisUtil.unLockLua(key, "b");
        System.out.println("释放锁结果：" + result);
    }

    @Test
    public void testluagrub() {
        redisUtil.set("1111111", "2");
        redisUtil.set("1111112", "50");
        String k1 = "1111111";
        String k2 = "1111112";
        System.out.println(redPacketService.grubFromRedis(k1, k2, "user3", "redpacketid"));
        System.out.println(redPacketService.grubFromRedis(k1, k2, "user4", "redpacketid"));
    }

    @Autowired
    RedPacketRecordRepository redPacketRecordRepository;

    @Test
    public void testMname() {
        List<AggsInfo> list = redPacketRecordRepository.getSumAmount("zxp_1557031434423_1");
        list.forEach(aggsInfo -> System.out.println(aggsInfo));
    }


}
