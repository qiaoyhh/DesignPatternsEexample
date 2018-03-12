package com.qyh.designpatternsexample.observer;

/**
 * Created by qyh on 2018/3/12.
 */

// 具体被观察者
public class ConcreteSubject extends Subject {

    public void changeState(){
       super.notifyObservers();
    }
}
