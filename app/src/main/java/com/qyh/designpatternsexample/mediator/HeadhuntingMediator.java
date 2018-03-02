package com.qyh.designpatternsexample.mediator;

/**
 * Created by qyh on 2018/3/2.
 *
 */

//猎头：具体的中介者，抽象的实现，用来协调各个同事之间的调用
public class HeadhuntingMediator extends Mediator{

    @Override
   public void contact(String msg, AbstractPerson person) {
        // 如果是老板，则求职者收到消息
        if(person.equals(mBoss)){
            mJobhunter.receiveMessage(msg);
        }else{
        // 反之，老板收到消息
            mBoss.receiveMessage(msg);
        }
    }
}
