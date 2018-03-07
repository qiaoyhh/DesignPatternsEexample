package com.qyh.designpatternsexample.decorator.example.packet;

import com.qyh.designpatternsexample.decorator.AbstractSeat;
import com.qyh.designpatternsexample.decorator.example.SeatDecorator;

/**
 * Created by adminn on 2018/3/7.
 */

// 艾佛森上篮包
public class IversonLayupPacket extends SeatDecorator {

    public IversonLayupPacket(AbstractSeat abstractSeat) {
        super(abstractSeat);
    }

    @Override
    public String seat() {
        return super.seat()+addPacket();
    }

    private String addPacket() {
        return "IversonLayupPacket";
    }
}
