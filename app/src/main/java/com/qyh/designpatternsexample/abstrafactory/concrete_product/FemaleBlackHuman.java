package com.qyh.designpatternsexample.abstrafactory.concrete_product;


import com.qyh.designpatternsexample.abstrafactory.product.AbstractBlackHuman;

/**
 * Created by adminn on 2018/2/28.
 * 具体产品，描述生产的具体产品
 */

//
public class FemaleBlackHuman extends AbstractBlackHuman {

    @Override
    public void getSex() {
        System.out.println("FemaleBlackHuman 生产一个黑皮肤美女");
    }
}
