package com.qyh.designpatternsexample.proxy.static_proxy;

/**
 * Created by qyh on 2018/3/1.
 * 玩家自己玩游戏，打怪 升级
 */

public class GamePlayer implements IGamePlayer{

    private String mName;
    public GamePlayer(String name) {
        this.mName = name;
    }

    @Override
    public void login(String name, String password) {
        System.out.println("用户"+name+"登录成功");
    }

    @Override
    public void killBoss() {
        System.out.println("用户"+mName+"开启超神模式，十连杀");
    }

    @Override
    public void upgrade() {
        System.out.println("恭喜用户"+mName+"成功升到110级");
    }
}
