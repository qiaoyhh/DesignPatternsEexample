package com.qyh.designpatternsexample.abstrafactory.concrete_factory;


import com.qyh.designpatternsexample.abstrafactory.abstract_product.Human;
import com.qyh.designpatternsexample.abstrafactory.concrete_product.FemaleBlackHuman;
import com.qyh.designpatternsexample.abstrafactory.concrete_product.FemaleWhiteHuman;
import com.qyh.designpatternsexample.abstrafactory.creator.HumanFactory;

/**
 * Created by adminn on 2018/2/28.
 * 具体工厂，抽象工厂子类，被外界调用
 */

// 生产美女的工厂
public class FemaleHumanFactory implements HumanFactory {
    @Override
    public Human createWhiteHuman() {
        return new FemaleWhiteHuman();
    }

    @Override
    public Human createBlackHuman() {
        return new FemaleBlackHuman();
    }
}
