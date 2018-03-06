package com.qyh.designpatternsexample.iterator.example;

/**
 * Created by adminn on 2018/3/6.
 */

public class CTO extends AbstractLeader {
    @Override
    public int limitDay() {
        return 5;
    }

    @Override
    public void handle(double day) {
        System.out.println("CTO审批假期天数 "+day);
    }
}
