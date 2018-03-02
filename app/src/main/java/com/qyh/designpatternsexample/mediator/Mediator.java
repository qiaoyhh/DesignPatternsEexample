package com.qyh.designpatternsexample.mediator;

/**
 * Created by qyh on 2018/3/2.
 */

// 定义抽象中介者
public abstract class Mediator {

    protected Jobhunter mJobhunter;
    protected Boss mBoss;

    // 让对象之间进行通讯
    public abstract void contact(String msg, AbstractPerson abstractPerson);

    // 设置求职者
    public void setJobhunter(Jobhunter jobhunter) {
      this.mJobhunter = jobhunter;
    }

    // 设置老板
    public void setBoss(Boss boss){
        this.mBoss = boss;
    }
}
