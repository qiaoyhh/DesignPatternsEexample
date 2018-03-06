package com.qyh.designpatternsexample.iterator.example;

/**
 * Created by qyh on 2018/3/6.
 */

public abstract class AbstractLeader {

    private AbstractLeader mNextHandler;

    // 需要处理的请求
    public final void handlerRequest(IEmployee employee) {
        if (employee.getHolidayDay() <= limitDay()) {
            handle(employee.getHolidayDay());
            return;
        } else {
            if (mNextHandler != null) {
                mNextHandler.handlerRequest(employee);
            } else {
                System.out.println("公司规定请假不得超过7天，没人处理！");
            }
        }
    }

    // 自身能处理最多天数
    public abstract int limitDay();

    // 处理请求，具体的请假天数
    public abstract void handle(double day);

    // 设置下一位处理者
    public void setNextHandler(AbstractLeader abstractLeader) {
        this.mNextHandler = abstractLeader;
    }
}
