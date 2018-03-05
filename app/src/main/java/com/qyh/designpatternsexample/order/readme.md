## 命令模式
### 一、定义
命令模式是行为型设计模式之一，将一个请求封装成一个对象，从而让用户使用不同的请求把客户端参数化；对请求队列或者记录请求日志，以及支持可撤销的操作。
### 二、UML图及解析
![](https://github.com/qiaoyhh/DesignPatternsEexample/blob/master/app/src/main/res/mipmap-xhdpi/order_uml.png)

- Receiver : 命令接收者，负责具体执行一个请求。
- Command：命令角色，定义具体命令类的接口。
- ConcreteCommand : 具体的命令角色。，实现了Command接口，在excute()方法中调用接收者Receiver的相关方法，弱化了命令接收者和具体行为之间的耦合。
- Invoker：请求者角色，调用命令对象执行具体的请求

**抽象命令接收者**
```java
 public abstract class Receiver {
     //抽象接收者，定义每个接收者都必须完成的业务
     public abstract void doSomething();
 }
```
**具体命令接收者**
```java
 public class ConcreteReciver1 extends Receiver{
     //每个接收者都必须处理一定的业务逻辑
     public void doSomething(){
     }
 }
 public class ConcreteReciver2 extends Receiver{
     //每个接收者都必须处理一定的业务逻辑
     public void doSomething(){
     }
 }
```
接收者可以是N个，具体看实际业务需求。

**抽象命令角色**
```java
  public abstract class Command {
      //每个命令类都必须有一个执行命令的方法
      public abstract void execute();
  }
```
**具体命令角色**
```java
   public class ConcreteCommand1 extends Command {
       //对哪个Receiver类进行命令处理
       private Receiver mReceiver;
       //构造函数传递接收者
       public ConcreteCommand1(Receiver receiver){
           this.mReceiver = receiver;
       }
       //必须实现一个命令
       public void execute() {
       //业务处理
           this.mReceiver.doSomething();
       }
   }
```
注：具体命令角色也可以有N个。

**调用者角色**
```java
public class Invoker {
    private Command mCommand;
    //接受命令
    public void setCommand(Command command){
        this.mCommand = command;
    }
    //执行命令
    public void action(){
        this.mCommand.execute();
    }
}
```
**客户端**
```java
public void test(){
    //首先声明调用者Invoker
    Invoker invoker = new Invoker();
    //定义接收者
    Receiver receiver = new ConcreteReciver1();
    //定义一个发送给接收者的命令
    Command command = new ConcreteCommand1(receiver);
    //把命令交给调用者去执行
    invoker.setCommand(command);
    invoker.action();
}
```

### 举个栗子
场景：平时开发中经常会有分享这种功能，比如QQ分享，微信分享，新浪分享等等。
**接收者**
```java
// 接收者角色
public class Receiver {
    public void qqShare(){
        //省略若干操作。
        System.out.println("QQ分享");
    }

    public void wechatShare(){
        System.out.println("微信分享");
    }

    public void sinaShare(){
        System.out.println("新浪分享");
    }
}
```
```java
public interface ICommand {
    void execute();
}
```
**具体命令角色**
```java
public class QQCommand implements ICommand {

    private Receiver mReceiver;
    public QQCommand(Receiver receiver) {
        this.mReceiver = receiver;
    }

    @Override
    public void execute() {
        this.mReceiver.qqShare();
    }
}

public class SinaCommand implements ICommand {
    private Receiver mReceiver;

    public SinaCommand(Receiver receiver) {
        this.mReceiver = receiver;
    }

    @Override
    public void execute() {
        mReceiver.sinaShare();
    }
}

public class WeChatCommand implements ICommand {
    private Receiver mReceiver;

    public WeChatCommand(Receiver receiver) {
        this.mReceiver = receiver;
    }

    @Override
    public void execute() {
        mReceiver.wechatShare();
    }
}
```
**调用者角色**
```java
public class Invoker {
    public ICommand mIcommand;
    private List<ICommand> mCommandList = new ArrayList<>();

    public Invoker() {
    }

    public void addCommand(ICommand command) {
        mCommandList.add(command);
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
```
**客户端**
```java
 public void testOrder(){
        // 创建接收者
        Receiver receiver = new Receiver();
        //创建具体的命令对象，设定它的接收者
        QQCommand qqCommand = new QQCommand(receiver);
        SinaCommand sinaCommand = new SinaCommand(receiver);
        WeChatCommand weChatCommand = new WeChatCommand(receiver);

        //创建请求者，把命令对象设置进去
        Invoker invoker = new Invoker();
        invoker.addCommand(qqCommand);
        invoker.addCommand(sinaCommand);
        invoker.addCommand(weChatCommand);

        // 执行方法
        invoker.allAction();
    }
```
Log显示

QQ分享

新浪分享

微信分享

或许大家看了这个代码之后心存疑惑，明明就是一个简单的调用逻辑，为什么要做的如此复杂呢？调用如此复杂，是因为开发起来方便，每次我们增加或者修改分享
的功能只需修改Receiver和XXXCommand就可以了。
除此之外，命令模式还有一个好处就是对请求恢复和撤销方便，我们尝试一下撤销命令的操作，修改上面例子的Invoker类。
```java
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
```
在测试类中调用removeCommand(),正确打印出Log,具体log打印就不发出了，有兴趣的可以下载源码测试一下。
### 四、优缺点
#### 优点
- 1.类间解耦：调用者和命令接受者没有任何关系，调用者实现功能只需调用Command抽象类的execute方法就可以，不需要了解到底是哪个接收者执行。
- 2.可扩展性：Command的子类非常容易扩展。
#### 缺点
- 使用命令模式可能会导致某些系统有过多的具体命令类。因为针对每一个命令都需要设计一个具体命令类，因此某些系统可能需要大量具体命令类。
                
