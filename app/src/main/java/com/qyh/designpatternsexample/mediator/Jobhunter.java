package com.qyh.designpatternsexample.mediator;

/**
 * Created by qyh on 2018/3/2.
 */


// 同事类1： 求职者
public class Jobhunter extends AbstractPerson {
    public Jobhunter(String name, Mediator mediator) {
        super(name, mediator);
    }

    public void contact(String msg) {
        mMediator.contact(msg, this);
    }

    @Override
    void receiveMessage(String msg) {
        System.out.println("求职者" + mName + "收到消息： "+msg);
    }
}
