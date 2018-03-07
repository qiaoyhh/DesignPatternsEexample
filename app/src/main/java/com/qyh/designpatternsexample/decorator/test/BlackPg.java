package com.qyh.designpatternsexample.decorator.test;

import com.qyh.designpatternsexample.decorator.concretedecorator.Pg;

/**
 * Created by adminn on 2018/3/6.
 */

public class BlackPg extends Pg {
    @Override
    public String seat() {
        System.out.println("黑皮肤位置的pg");
        return "black pg";
    }
}
