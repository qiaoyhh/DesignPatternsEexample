package com.qyh.designpatternsexample.strategy;

/**
 * Created by adminn on 2018/3/7.
 */

// 普通用户 不打折
public class CommonStrategy implements IDiscountStrategy{
    @Override
    public double calcDiscount(double money) {
        // 省略一系列复杂算法
        return money;
    }
}
