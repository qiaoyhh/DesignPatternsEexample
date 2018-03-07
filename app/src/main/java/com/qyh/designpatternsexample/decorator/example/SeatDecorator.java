package com.qyh.designpatternsexample.decorator.example;

import com.qyh.designpatternsexample.decorator.AbstractSeat;

/**
 * Created by adminn on 2018/3/6.
 */

// 抽象装饰者
public  class SeatDecorator extends AbstractSeat {

    private AbstractSeat mAbstractSeat;

    public SeatDecorator(AbstractSeat abstractSeat) {
        this.mAbstractSeat = abstractSeat;
    }

    @Override
    public String seat() {
      return mAbstractSeat.seat();
    }
}
