package org.zxp.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zxp.redis.utils.RedisUtil;

/**
 * @program: redis
 * @create: 2019-03-29 15:34
 **/
@RestController
public class RedisController {
    @Autowired
    RedisUtil redisUtil;

    @GetMapping("/redis/set")
    public String set(String key, String value) {
        redisUtil.set(key, value);
        return "成功";
    }


    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @GetMapping("/redis/set2")
    public String set2(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        if (redisTemplate.opsForValue().get(key).equals(value))
            return "成功";
        return "失败";
    }


    @GetMapping("/redis/get")
    public String set2(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
