package com.qyh.designpatternsexample.factory.creator;


import com.qyh.designpatternsexample.factory.product.Human;

/**
 * Created by qyh on 2018/2/28.
 *
 *  步骤一：创建抽象工厂类，定义具体工厂的公共接口
 */

public abstract class AbstractHumanFactory {
    // Class<T> 两层含义  1.必须是class类型 2.必须是human的实现类
    public abstract <T extends Human> T createHuman(Class<T> tClass);
}
