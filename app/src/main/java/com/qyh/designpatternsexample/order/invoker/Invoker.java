package com.qyh.designpatternsexample.order.invoker;

import com.qyh.designpatternsexample.order.ICommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qyh on 2018/3/5.
 */

public class Invoker {
    public ICommand mIcommand;
    private List<ICommand> mCommandList = new ArrayList<>();

    public Invoker() {
    }

    public void addCommand(ICommand command) {
        mCommandList.add(command);
    }

    // 撤销某项操作
    public void removeCommand(ICommand command){
        if(mCommandList.contains(command)){
            mCommandList.remove(command);
            System.out.println("撤销了"+command.getClass().getName().toString());
        }
    }

    public void action() {
        mIcommand.execute();
    }

    public void allAction() {
        for (ICommand iCommand : mCommandList) {
            iCommand.execute();
        }
    }
}
