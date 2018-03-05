package com.qyh.designpatternsexample.order.command;

import com.qyh.designpatternsexample.order.ICommand;
import com.qyh.designpatternsexample.order.Receiver;

/**
 * Created by qyh on 2018/3/5.
 */

public class QQCommand implements ICommand {

    private Receiver mReceiver;
    public QQCommand(Receiver receiver) {
        this.mReceiver = receiver;
    }

    @Override
    public void execute() {
        this.mReceiver.qqShare();
    }
}
