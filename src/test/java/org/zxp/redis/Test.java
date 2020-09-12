package org.zxp.redis;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    static LongAdder longAdder = new LongAdder();
    static AtomicLong atomicLong = new AtomicLong();
    static AtomicInteger atomicInteger = new AtomicInteger();
    static ReentrantLock lock = new ReentrantLock();


    public static void main(String[] args) throws Exception {
        Condition condition = lock.newCondition();
        condition.await();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 200; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    atomicInteger.incrementAndGet();
//                    atomicLong.incrementAndGet();
//                    longAdder.increment();
                }
            }).start();
        }
        System.out.println("启动完毕");

//        Thread.sleep(5000L);

        System.out.println(atomicInteger.get());
        System.out.println(atomicLong.get());
        System.out.println(longAdder.sum());
        System.out.println(System.currentTimeMillis() - begin);

    }
}
