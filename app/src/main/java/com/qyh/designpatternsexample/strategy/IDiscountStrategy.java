package com.qyh.designpatternsexample.strategy;

/**
 * Created by adminn on 2018/3/7.
 */

public interface IDiscountStrategy {
    // 传入总价，计算出打折后的价钱
    double calcDiscount(double money);
}
