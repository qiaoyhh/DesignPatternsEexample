package com.qyh.designpatternsexample.mediator;

/**
 * Created by qyh on 2018/3/2.
 */

//抽象同事类
public abstract class AbstractPerson {
    protected String mName;
    protected Mediator mMediator;

    public AbstractPerson(String name,Mediator mediator) {
        this.mName = name;
        this.mMediator = mediator;
    }

    abstract void receiveMessage(String msg);
}
