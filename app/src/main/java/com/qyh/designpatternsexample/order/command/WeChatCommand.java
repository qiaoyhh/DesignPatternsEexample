package com.qyh.designpatternsexample.order.command;

import com.qyh.designpatternsexample.order.ICommand;
import com.qyh.designpatternsexample.order.Receiver;

/**
 * Created by adminn on 2018/3/5.
 */

public class WeChatCommand implements ICommand {
    private Receiver mReceiver;

    public WeChatCommand(Receiver receiver) {
        this.mReceiver = receiver;
    }

    @Override
    public void execute() {
        mReceiver.wechatShare();
    }
}
