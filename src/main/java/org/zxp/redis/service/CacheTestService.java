package org.zxp.redis.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: redis
 * @create: 2019-04-01 15:30
 **/
@Service
@CacheConfig(cacheNames = "cacheTestService")
public class CacheTestService {
    private static Map<String, String> data = new HashMap();

    public CacheTestService() {
        data.put("a", "001");
        data.put("b", "002");
        data.put("c", "003");
    }

    @Cacheable(key = "#param")
    public String getData(String param) {
        System.out.println("执行获取数据");
        return data.get(param);
    }


    @CachePut(key = "#p0")
    public String putData(String param, String value) {
        System.out.println("更新数据");
        data.put(param, value);
        return value;
    }

    @CacheEvict(key = "#p0")
    public void delData(String param) {
        System.out.println("删除数据");
        data.remove(param);
    }

    @Cacheable(value = "ttttt", key = "#param")
    public String testGetData(String param) {
        System.out.println("执行获取数据");
        return data.get(param);
    }


    @Cacheable(key = "#a+'-'+#b", condition = "#result != null")
    public String getData2(String a, String b) {
        System.out.println("执行获取数据");
        return data.get(a + b);
    }


    @CachePut(key = "#a+'-'+#b")
    public String putData2(String a, String b, String a_value, String b_value) {
        System.out.println("更新数据");
        data.put(a + b, a_value + b_value);
        return a_value + b_value;
    }

}
