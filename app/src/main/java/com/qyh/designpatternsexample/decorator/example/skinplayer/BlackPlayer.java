package com.qyh.designpatternsexample.decorator.example.skinplayer;

import com.qyh.designpatternsexample.decorator.AbstractSeat;
import com.qyh.designpatternsexample.decorator.example.SeatDecorator;

/**
 * Created by adminn on 2018/3/6.
 */

public class BlackPlayer extends SeatDecorator {
    public BlackPlayer(AbstractSeat abstractSeat) {
        super(abstractSeat);
    }

    @Override
    public String seat() {
        System.out.println("创建一个 "+getChangeSkin() + super.seat());
        return getChangeSkin();
    }

    //自定义的修饰方法
    private String getChangeSkin() {
        return "black";
    }
}
