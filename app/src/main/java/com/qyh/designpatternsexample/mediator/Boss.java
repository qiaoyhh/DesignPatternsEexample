package com.qyh.designpatternsexample.mediator;

/**
 * Created by qyh on 2018/3/2.
 */

//同事类2： 老板
public class Boss extends AbstractPerson {
    public Boss(String name, Mediator mediator) {
        super(name, mediator);
    }

    public void contact(String msg) {
        mMediator.contact(msg, this);
    }

    @Override
    void receiveMessage(String msg) {
        System.out.println("老板" + mName + "收到消息： " + msg);
    }
}
