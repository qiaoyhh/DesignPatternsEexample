package com.qyh.designpatternsexample.order.command;

import com.qyh.designpatternsexample.order.ICommand;
import com.qyh.designpatternsexample.order.Receiver;

/**
 * Created by qyh on 2018/3/5.
 */

public class SinaCommand implements ICommand {
    private Receiver mReceiver;

    public SinaCommand(Receiver receiver) {
        this.mReceiver = receiver;
    }

    @Override
    public void execute() {
        mReceiver.sinaShare();
    }
}
