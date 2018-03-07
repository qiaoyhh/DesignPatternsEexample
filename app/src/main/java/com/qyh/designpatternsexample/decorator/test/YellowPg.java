package com.qyh.designpatternsexample.decorator.test;

import com.qyh.designpatternsexample.decorator.concretedecorator.Pg;

/**
 * Created by adminn on 2018/3/6.
 */

public class YellowPg extends Pg {
    @Override
    public String seat() {
        System.out.println("黄皮肤的pg");
        return "YellowPg";
    }
}
