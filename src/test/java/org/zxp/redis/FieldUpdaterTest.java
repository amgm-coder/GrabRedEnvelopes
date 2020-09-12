package org.zxp.redis;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class FieldUpdaterTest {

    public static void main(String[] args) {
        AtomicIntegerFieldUpdater<User> id = AtomicIntegerFieldUpdater.newUpdater(User.class, "id");
        User user = new User();
        int andIncrement = id.incrementAndGet(user);
        System.out.println(andIncrement);
    }
}
