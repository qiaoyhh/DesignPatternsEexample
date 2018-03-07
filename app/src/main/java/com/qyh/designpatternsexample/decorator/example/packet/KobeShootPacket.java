package com.qyh.designpatternsexample.decorator.example.packet;

import com.qyh.designpatternsexample.decorator.AbstractSeat;
import com.qyh.designpatternsexample.decorator.example.SeatDecorator;

/**
 * Created by adminn on 2018/3/7.
 */

// 科比投篮包
public class KobeShootPacket extends SeatDecorator {

    public KobeShootPacket(AbstractSeat abstractSeat) {
        super(abstractSeat);
    }

    @Override
    public String seat() {
        return super.seat()+addPacket();
    }

    private String addPacket(){
        return "KobeShootPacket";
    }
}
