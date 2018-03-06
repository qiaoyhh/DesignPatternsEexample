package com.qyh.designpatternsexample.iterator.example;

/**
 * Created by adminn on 2018/3/6.
 */

public class Employee implements IEmployee {

    private double mHolidayDay;
    private String mRequest;

    public Employee(double day, String request) {
        this.mHolidayDay = day;
        this.mRequest = request;
        System.out.println("员工JACK Ma 请假天数:"+day+",请假理由："+request);
    }

    @Override
    public double getHolidayDay() {
        return mHolidayDay;
    }

    @Override
    public String getRequest() {
        return mRequest;
    }
}
