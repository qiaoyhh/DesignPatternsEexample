package com.qyh.designpatternsexample.order;

/**
 * Created by qyh on 2018/3/5.
 */

// 接收者角色
public class Receiver {
    public void qqShare(){
        System.out.println("QQ分享");
    }

    public void wechatShare(){
        System.out.println("微信分享");
    }

    public void sinaShare(){
        System.out.println("新浪分享");
    }
}
