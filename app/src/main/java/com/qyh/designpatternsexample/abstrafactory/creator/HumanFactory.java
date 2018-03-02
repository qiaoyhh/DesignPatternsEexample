package com.qyh.designpatternsexample.abstrafactory.creator;


import com.qyh.designpatternsexample.abstrafactory.abstract_product.Human;

/**
 * Created by qyh on 2018/2/28.
 * 抽象工厂类（接口抽象类都可以），具体工厂的父类
 */

public interface HumanFactory {
    // 创建白皮肤产品族
    Human createWhiteHuman();
    // 创建黑皮肤产品族
    Human createBlackHuman();
}
