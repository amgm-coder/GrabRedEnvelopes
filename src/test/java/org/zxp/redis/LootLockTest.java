package org.zxp.redis;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @program: redis
 * @create: 2019-04-06 12:16
 **/
public class LootLockTest {
    public static final String[] LOCKS = new String[128];

    static {
        for (int i = 0; i < 128; i++) {
            LOCKS[i] = "lock_" + i;
        }
    }

    @Test
    public void testMname() {
        String userId = "251000000a";
        String[] locks = LOCKS;
        System.out.println(locks.length - 1);
        int index = userId.hashCode() & (locks.length - 1);
        System.out.println("[" + userId + "]" + " hashCode is:" + userId.hashCode());
        System.out.println("[" + userId + "]" + " hash index is:" + index);
        System.out.println(userId.hashCode() % (locks.length - 1));


        System.out.println(39 / 5);//商
        System.out.println(39 % 5);//余数 范围在0 1 2 3 4
        System.out.println(Math.floorMod(39, 5));//余数 范围在0 1 2 3 4

        System.out.println("===================");
        Set set = new HashSet();
        for (int i = 0; i < 10000; i++) {
            //当hashtable的长度是2的幂的情况下，这两者是等价的
//            set.add(i&7);//与运算 余数<=8
            set.add(i % 8);//与运算 余数<=8
        }
        for (Object o : set) {
            System.out.println(o);
        }
    }
}
