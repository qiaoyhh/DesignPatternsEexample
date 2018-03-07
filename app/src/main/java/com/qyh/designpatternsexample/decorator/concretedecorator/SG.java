package com.qyh.designpatternsexample.decorator.concretedecorator;

import com.qyh.designpatternsexample.decorator.AbstractSeat;

/**
 * Created by adminn on 2018/3/6.
 */

public class SG extends AbstractSeat {
    @Override
    public String seat() {
        return "SG";
    }
}
