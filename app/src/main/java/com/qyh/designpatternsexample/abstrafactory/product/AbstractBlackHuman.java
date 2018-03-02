package com.qyh.designpatternsexample.abstrafactory.product;


import com.qyh.designpatternsexample.abstrafactory.abstract_product.Human;

/**
 * Created by qyh on 2018/2/28.
 *具体产品的父类，描述具体产品的公共接口
 */

//负责人种的公共属性的定义。
public abstract class AbstractBlackHuman implements Human {

    @Override
    public void getColor() {
        System.out.println("AbstractBlackHuman black");
    }

    @Override
    public void talk() {
        System.out.println("AbstractBlackHuman talk");
    }
}
