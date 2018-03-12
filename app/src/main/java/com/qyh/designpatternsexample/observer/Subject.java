package com.qyh.designpatternsexample.observer;

import java.util.ArrayList;

/**
 * Created by qyh on 2018/3/12.
 */

// 被观察者
public abstract class Subject {
    ArrayList<IObserver> observers = new ArrayList<>();

    //增加观察者
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    //删除一个观察者

    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    // 自身发生改变，通知所有观察者
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update();
        }
    }
}
