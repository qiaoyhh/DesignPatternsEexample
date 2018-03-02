package com.qyh.designpatternsexample.proxy.static_proxy;

/**
 * Created by qyh on 2018/3/1.
 * 所有玩家接口
 */

public interface IGamePlayer {

    void login(String name, String password);

    void killBoss();

    void upgrade();
}
