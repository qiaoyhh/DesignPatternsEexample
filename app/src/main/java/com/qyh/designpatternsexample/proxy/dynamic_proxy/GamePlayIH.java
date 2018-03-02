package com.qyh.designpatternsexample.proxy.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by qyh on 2018/3/1.
 * 动态代理
 */

public class GamePlayIH implements InvocationHandler {
    private Object mObject;

    public GamePlayIH(Object object) {
        this.mObject = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(this.mObject, args);
        return result;
    }
}
