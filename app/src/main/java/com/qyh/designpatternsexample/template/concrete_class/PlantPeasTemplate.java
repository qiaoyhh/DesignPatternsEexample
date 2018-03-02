package com.qyh.designpatternsexample.template.concrete_class;


import com.qyh.designpatternsexample.template.abstract_class.AbstractTemplate;

/**
 * Created by adminn on 2018/3/1.
 * 具体模板   种豆得豆
 */

public class PlantPeasTemplate extends AbstractTemplate {

    @Override
    protected void plant() {
        System.out.println("种豆得豆");
    }
}
