package com.qyh.designpatternsexample.strategy;

/**
 * Created by adminn on 2018/3/7.
 */

// 黄金会员，10%折扣
public class GoldStrategy implements IDiscountStrategy {
    @Override
    public double calcDiscount(double money) {
        return money - money * 0.1;
    }
}
