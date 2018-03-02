package com.qyh.designpatternsexample.abstrafactory.concrete_factory;

import com.qyh.designpatternsexample.abstrafactory.abstract_product.Human;
import com.qyh.designpatternsexample.abstrafactory.concrete_product.MaleBlackHuman;
import com.qyh.designpatternsexample.abstrafactory.concrete_product.MaleWhiteHuman;
import com.qyh.designpatternsexample.abstrafactory.creator.HumanFactory;

/**
 * Created by ayh on 2018/2/28.
 * 具体工厂，抽象工厂子类，被外界调用
 */

// 生产男性的工厂（区分颜色）
public class MaleHumanFactory implements HumanFactory {
    @Override
    public Human createWhiteHuman() {
        return new MaleWhiteHuman();
    }

    @Override
    public Human createBlackHuman() {
        return new MaleBlackHuman();
    }
}
