package org.zxp.redis;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    public static void main(String[] args) throws Exception{

        Thread t1 = new Thread(() -> {
            try {
                System.out.println("thread1");
                cyclicBarrier.await();
                System.out.println("t1 going");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                System.out.println("thread2");
                double a = 1 / 0;
                cyclicBarrier.await();
                System.out.println("t2 going");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        t1.start();
        Thread.sleep(1000L);
        t2.start();
//        t1.interrupt();
//        Thread.sleep(1000L);
        System.out.println(cyclicBarrier.getNumberWaiting());

    }
}
