package com.qyh.designpatternsexample.proxy.static_proxy;

/**
 * Created by qyh on 2018/3/1.
 * 游戏代练者，代替游戏玩家
 */

public class GamePlayProxy implements IGamePlayer{
    private IGamePlayer mIGamePlayer;

    public GamePlayProxy(IGamePlayer iGamePlayer ) {
        this.mIGamePlayer = iGamePlayer;
    }

    @Override
    public void login(String name, String password) {
       mIGamePlayer.login(name,password);
    }

    @Override
    public void killBoss() {
        mIGamePlayer.killBoss();
    }

    // 代练升级
    @Override
    public void upgrade() {
        mIGamePlayer.upgrade();
    }
}
