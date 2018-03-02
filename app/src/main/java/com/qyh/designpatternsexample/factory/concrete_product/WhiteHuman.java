package com.qyh.designpatternsexample.factory.concrete_product;


import com.qyh.designpatternsexample.factory.product.Human;

/**
 * Created by qyh on 2018/2/28.
 */

public class WhiteHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("WhiteHuman  white=========");
    }

    @Override
    public void talk() {
        System.out.println("WhiteHuman  talk=========");
    }
}
