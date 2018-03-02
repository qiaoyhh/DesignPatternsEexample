package com.qyh.designpatternsexample.template.concrete_class;


import com.qyh.designpatternsexample.template.abstract_class.AbstractTemplate;

/**
 * Created by qyh on 2018/3/1.
 * 具体模板   种瓜
 */

public class PlantMelonTemplate extends AbstractTemplate {
    @Override
    protected void plant() {
        System.out.println("种瓜得瓜");
    }
}
