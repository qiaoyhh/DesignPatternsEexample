package com.qyh.designpatternsexample.template.abstract_class;

/**
 * Created by qyh on 2018/3/1.
 *
 * 抽象模板
 */

public abstract class AbstractTemplate {

    private boolean mIsWatering;

    // 模板方法
    public void templateMothod() {
        dig_holes();
        plant();
        watering();
        mulch();
    }

    //基本方法，已经实现
    private final void dig_holes() {
        // 省略一系列实现步骤
        System.out.println("挖坑 固定的套路，所以实现方法封装在父类中");
    }

    //基本方法。种植是可变的，因为父类不知道具体种植的种类，所以有子类自己去实现
    protected abstract void plant();

    //基本方法，已经实现,但是这个功能需要子类自己决定
    private void watering() {
        if (mIsWatering) {
            System.out.println("生命力脆弱，需要浇水");
        } else {
            System.out.println("生命力顽强，不用浇水");
        }
    }

    //基本方法，已经实现
    private final void mulch() {
        // 省略一系列实现步骤
        System.out.println("盖土 固定的套路，所以实现方法封装在父类中");
    }

    // 钩子函数，由子类自己决定
    public void isWatering(boolean isWatering) {
        this.mIsWatering = isWatering;
    }
}
