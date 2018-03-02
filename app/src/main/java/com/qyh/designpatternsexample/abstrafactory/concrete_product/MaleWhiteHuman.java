package com.qyh.designpatternsexample.abstrafactory.concrete_product;


import com.qyh.designpatternsexample.abstrafactory.product.AbstractWhiteHuman;

/**
 * Created by qyh on 2018/2/28.
 * 具体产品，描述生产的具体产品
 */

public class MaleWhiteHuman extends AbstractWhiteHuman {
    @Override
    public void getSex() {
        System.out.println("MaleWhiteHuman 生产一个白皮肤大汉");
    }
}
