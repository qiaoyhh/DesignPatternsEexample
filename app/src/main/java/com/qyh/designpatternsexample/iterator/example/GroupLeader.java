package com.qyh.designpatternsexample.iterator.example;

/**
 * Created by adminn on 2018/3/6.
 */

// 组长
public class GroupLeader extends AbstractLeader {
    @Override
    public int limitDay() {
        return 3;
    }

    @Override
    public void handle(double day) {
        // 省略逻辑
        System.out.println("组长审批假期天数 "+day);
    }
}
