package com.qyh.designpatternsexample.strategy;

/**
 * Created by adminn on 2018/3/7.
 */

// 计算打折后价钱类（对应环境类）
public class DiscountNum {
    private IDiscountStrategy mIDiscountStrategy;

    //构造参数，传入具体的策略对象
    public DiscountNum(IDiscountStrategy iDiscountStrategy) {
        this.mIDiscountStrategy = iDiscountStrategy;
    }

    // 计算打折后价钱
    public double calculateTax(double money) {
        return this.mIDiscountStrategy.calcDiscount(money);
    }
}
