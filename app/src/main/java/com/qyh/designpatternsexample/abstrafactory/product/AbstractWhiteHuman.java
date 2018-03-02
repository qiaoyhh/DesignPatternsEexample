package com.qyh.designpatternsexample.abstrafactory.product;


import com.qyh.designpatternsexample.abstrafactory.abstract_product.Human;

/**
 * Created by adminn on 2018/2/28.
 * 具体产品的父类，描述具体产品的公共接口
 */

public abstract class AbstractWhiteHuman implements Human {

    @Override
    public void getColor() {
        System.out.println("AbstractWhiteHuman white");
    }

    @Override
    public void talk() {
        System.out.println("AbstractWhiteHuman talk");
    }
}
