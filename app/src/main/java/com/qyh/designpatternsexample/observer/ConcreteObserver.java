package com.qyh.designpatternsexample.observer;

/**
 * Created by qyh on 2018/3/12.
 */

public class ConcreteObserver implements IObserver {
    @Override
    public void update() {
        System.out.println("收到被观察者状态改变");
    }
}
