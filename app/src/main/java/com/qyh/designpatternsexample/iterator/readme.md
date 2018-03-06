## 责任链模式
### 一、定义：
  一个请求沿着一条“链”传递，直到该“链”上的某个处理者处理它为止。
### 二、UML类图及解析
![](https://github.com/qiaoyhh/DesignPatternsEexample/blob/master/app/src/main/res/mipmap-xhdpi/iterator_base_uml.png)

- Handler：抽象处理者角色，声明一个处理请求的方法，并保持对下一个处理节点Handler对象的引用。
- ConcreteHandler:具体的处理者，对请求进行处理，如果不处理就讲请求转发给下一个节点上的处理对象。

注：责任链的核心是"链"，链是多个具体的处理者ConcreteHandler组成。

**抽象处理者**
```java
public abstract class AbstractHandler {
    protected Handler nextHaler;
    // 每个处理者都必须实现处理任务
    public abstract void handleRequest(String msg);
    
    //设置下一位处理者
    protected void setNextHaler(AbstractHandler handler){
        this.nextHaler = handler;
    }
}
```
**具体处理者**
```java
public class ConcreteHandler1 extends AbstractHandler {
    // 定义自己处理的请求逻辑
    @Override
    public void handleRequest(String condition) {
        if ("ConcreteHandler1".equals(condition)){
            // 完成请求
            System.out.println("ConcreteHandler1 handleRequest");
            return;
        }else {
            nextHaler.handleRequest(msg);
        }
    }
}

public class ConcreteHandler2 extends AbstractHandler {
    @Override
    public void handleRequest(String condition) {
        if ("ConcreteHandler2".equals(condition)) {
            System.out.println("ConcreteHandler2 handleRequest ");
            return;
        } else {
            nextHaler.handleRequest(msg);
        }
    }
}
```

**客户类**
```java
// 创建具体的处理者
ConcreteHandler1 concreteHandler1 = new ConcreteHandler1();
ConcreteHandler2 concreteHandler2 = new ConcreteHandler2();
// 设置下一个节点
concreteHandler1.setNextHaler(concreteHandler2);
// 处理请求，一般从一级处理者开始
concreteHandler1.handleRequest("ConcreteHandler1");
```

以上是最基本的通用责任链代码，对于请求形式是固定的,通过一个字符串判断该节点上的对象是否能够处理请求。然而在大多情况下，
责任链中的请求和对应的处理规则是相同的，在这种情况下我们可以将请求进行封装，同时对处理规则也封装成一个对象，类图如下：
![](https://github.com/qiaoyhh/DesignPatternsEexample/blob/master/app/src/main/res/mipmap-xhdpi/iterator_comp_uml.png)

这种形式的通用代码就不贴了，接下来我们用一个实际例子来实现这种方式的实现。
通过刚才我们对责任链的描述，我们也发现了责任链特别适合于流程审批之类的场景，或者责任转发(View的事件分开)。责任链模式在这里很好地将请求的
发起者和处理解耦。
### 三、举个栗子
场景：公司员工请假这种情况简直不要太常见了，比如一个公司请假规定：三天以下  组长审批；五天以下，CTO审批；7天以下，CEO审批！大于7天，不审批！
     接下来我们就模拟这个请假流程，具体地了解责任链模式的用法。
     
**抽象处理者**
```java
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
```
在这个抽象领导类中只做了两件事，一是定义了两个抽象接口方法来确定一个领导者应用的行为和属性；二是声明了一个处理请求的方法来确定当前领导
是否有权限来处理当前的这个请求。
**具体处理者**
```java
// 组长
public class GroupLeader extends AbstractLeader {
    @Override
    public int limitDay() {
        return 3;
    }

    @Override
    public void handle(double day) {
        // 省略逻辑
        System.out.println("组长审批假期天数 "+day);
    }
}


public class CTO extends AbstractLeader {
    @Override
    public int limitDay() {
        return 5;
    }

    @Override
    public void handle(double day) {
        System.out.println("CTO审批假期天数 "+day);
    }
}

public class CEO extends AbstractLeader {
    @Override
    public int limitDay() {
        return 7;
    }

    @Override
    public void handle(double day) {
        System.out.println("CEO审批假期天数 "+day);
    }
}
```
这三个类都很简单，构造方法是必须实现的，父类框定子类必须有一个显式构造函数，子类不实现编译不通过。通过构造方法我们设置了各
个类能处理的请求类型(天数)，那如果请求类型为4的该如何处理呢？在Handler中我们已经判断了，如何没有相应的处理者（也就是没有
下一环节），则视为不同意。对于这种情况我们称为不纯的责任链！
**请求者**
```java
public interface IEmployee {
    // 获得请假天数
    double getHolidayDay();

    // 获得个人请假说明
    String getRequest();
}

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
```
具体的员工类，定义了请假天数！
**客户端**
```java
// 责任链模式测试
@Test
public void testIterator() {
    // 创建所有领导对象
    GroupLeader groupLeader = new GroupLeader();
    CTO cto = new CTO();
    CEO ceo = new CEO();

    //随机生成几个请假员工
    Random random = new Random();
    ArrayList<IEmployee> employeeArrayList = new ArrayList<>();
    for (int i = 0; i <5 ; i++) {
        employeeArrayList.add(new Employee(random.nextInt(10),"回家相亲"));
    }

    // 设置下一位处理对象
    groupLeader.setNextHandler(cto);
    cto.setNextHandler(ceo);
    
    for(IEmployee employee:employeeArrayList) {
        // 发起请求
        groupLeader.handlerRequest(employee);
    }
}
```
Log如下，完美~

![](https://github.com/qiaoyhh/DesignPatternsEexample/blob/master/app/src/main/res/mipmap-xhdpi/iterator_log.png)
#### 说明
- 看完这个例子我们可能会想，我们可不可以直接越过组长请假呢？答案是可以的，这也是责任链模式的灵活之处，请求的发起可以从责任链的任何一个节点开始。
- 对于责任链中的一个处理者对象，其有两个行为，一是处理请求，二是将请求转发给下一个节点，不允许某个处理者对象在处理了请求后又将请求转达给上一个
节点的情况。
- 对于一条责任链来说，一个请求最终只有两种情况，一是被某个对象处理，二是所以处理者均未处理，日常开发中后者居多。

### 四、优缺点
#### 优点：
1.解耦，扩展容易，请求的发起者和处理者分开，请求者不用知道是谁处理的，处理者也不需要知道请求者的全貌。
#### 缺点：
- 1.性能问题，每个请求都是从链头遍历到链尾，特别是在链比较长的时候，比较耗性能。
- 2.调试不很方便，特别是链条比较长，环节比较多的时候，由于采用了类似递归的方式，调试的时候逻辑可能比较复杂。
