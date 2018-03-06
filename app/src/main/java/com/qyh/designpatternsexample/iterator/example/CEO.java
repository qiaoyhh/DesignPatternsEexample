package com.qyh.designpatternsexample.iterator.example;

/**
 * Created by adminn on 2018/3/6.
 */

public class CEO extends AbstractLeader {
    @Override
    public int limitDay() {
        return 7;
    }

    @Override
    public void handle(double day) {
        System.out.println("CEO审批假期天数 "+day);
    }
}
