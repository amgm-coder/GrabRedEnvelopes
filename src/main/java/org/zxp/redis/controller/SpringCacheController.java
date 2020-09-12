package org.zxp.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zxp.redis.service.CacheTestService;

/**
 * @program: redis
 * @create: 2019-04-01 15:35
 **/
@RestController
public class SpringCacheController {
    @Autowired
    CacheTestService cacheTestService;

    @GetMapping("/springcache/get")
    public String getData(String key) {
        return cacheTestService.getData(key);
    }

    @GetMapping("/springcache/put")
    public String putData(String key, String value) {
        cacheTestService.putData(key, value);
        return "ok";
    }

    @GetMapping("/springcache/del")
    public String delData(String key) {
        cacheTestService.delData(key);
        return "ok";
    }

    @GetMapping("/springcache/test/get")
    public String testGetData(String key) {
        return cacheTestService.testGetData(key);
    }

    /**
     * 多参数测试一组get
     *
     * @param a
     * @param b
     * @return
     */
    @GetMapping("/springcache/get2")
    public String getData2(String a, String b) {
        return cacheTestService.getData2(a, b);
    }

    /**
     * 多参数测试一组get
     *
     * @param a
     * @param b
     * @param a_value
     * @param b_value
     * @return
     */
    @GetMapping("/springcache/put2")
    public String putData2(String a, String b, String a_value, String b_value) {
        return cacheTestService.putData2(a, b, a_value, b_value);
    }
}
