package com.qyh.designpatternsexample.strategy;

/**
 * Created by adminn on 2018/3/7.
 */

// 钻石会员，20%折扣
public class JewelStrategy implements IDiscountStrategy {
    @Override
    public double calcDiscount(double money) {
        return money - money * 0.2;
    }
}
