package com.qyh.designpatternsexample.iterator.base;

import java.util.logging.Handler;

/**
 * Created by adminn on 2018/3/6.
 */

public abstract class AbstractHandler {

    private Handler mHandler;

    // 每个处理者都必须实现处理任务
    public abstract void handleRequest(String msg);

    //每个处理者都有一个处理级别
    protected abstract int getHandlerLevel();

    //设置下一位处理者
    private void setNextHandler(Handler handler) {
        this.mHandler = handler;
    }
}
