package com.qyh.designpatternsexample.abstrafactory.concrete_product;


import com.qyh.designpatternsexample.abstrafactory.product.AbstractWhiteHuman;

/**
 * Created by qyh on 2018/2/28.
 * 具体产品，描述生产的具体产品
 */

public class FemaleWhiteHuman extends AbstractWhiteHuman {
    @Override
    public void getSex() {
        System.out.println("FemaleWhiteHuman 生产一个白皮肤美女");
    }
}
