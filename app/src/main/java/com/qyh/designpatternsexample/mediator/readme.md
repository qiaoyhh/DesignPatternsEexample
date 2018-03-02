## 中介者模式
### 一、定义
中介者模式包装了一系列对象相互的、作用的方式，使得这些对象不必相互明显作用。从而使它们可以松耦合。

### 二、UML类图

![](https://github.com/qiaoyhh/DesignPatternsEexample/blob/master/app/src/main/res/mipmap-xhdpi/mediator_uml.png)

- Mediator: 抽象的中介者角色，定义了同事对象到中介者的接口，一般以抽象类的方式实现。
- ConcreteMediator：具体的中介者角色，继承与抽象中介者，实现父类定义的方法，它从具体的同事对象接收消息，同时向具体的同事对象发出命令。
- Colleague：抽象同事类角色，定义了中介者对象的接口，只知道中介而不知道其他同事对象。
- ConcreteColleagueA，B：具体的同事类角色，继承与抽象同事类，每个具体同事类都知道本身在小范围内的行为，而不知道他在大范围中的行为。

### 模板代码
**通用抽象中介者**
```java
public abstract class Mediator {
    //定义同事类
    protected ConcreteColleague1 c1;
    protected ConcreteColleague2 c2;
    
    //通过setter方法把同事类注入进来
    public void setC1(ConcreteColleague1 c1) {
        this.c1 = c1;
    }
    
    public void setC2(ConcreteColleague2 c2) {
     this.c2 = c2;
    }
    //中介者模式的业务逻辑
    public abstract void doSomething1();
    public abstract void doSomething2();
}
```
**通用具体中介者**
```java
public class ConcreteMediator extends Mediator {
    @Override
    public void doSomething1() {
        //调用同事类的方法，只要是public方法都可以调用
        super.c1.selfMethod();
    }
    public void doSomething2() {
        super.c1.selfMethod();
    }
}
```
**抽象同事**
```java
public abstract class Colleague {
    protected Mediator mediator;

    public Colleague(Mediator _mediator){
        this.mediator = _mediator;
    }
}
```
**具体同事(另一个省略)**
```java
public class ConcreteColleague1 extends Colleague {
    //通过构造函数传递中介者
    public ConcreteColleague1(Mediator _mediator){
        super(_mediator);
    }
    public void selfMethod(){
    }  
}
```
为什么同事类要使用构造函数注入中介者，而中介者使用set方式注入同事类呢？这是因为同事类必须有中介者，而中介者却可以只有部分同事类。

### 案例
- 场景：我们在平时经常会收到猎头的电话，尤其是在咱们更新完简历后。猎头跟公司和我们求职者之间就搭建了一个中介者关系。
       例如：Jack Ma是一家公司的Boss,平时日理万鸡的工作甚是繁忙，正好他的公司人手短缺需要一批员工，因为他平时没时间
       一个一个简历的筛选、面试等。他就把自己的招聘要求托付给猎头公司，跟猎头说，根据招聘要求去猎人才，你面试差不多后介
       绍给我，下面我们就用程序大致实现一下。
 
**抽象中介者**
```java
// 定义抽象中介者
public abstract class Mediator {

    protected Jobhunter mJobhunter;
    protected Boss mBoss;

    // 让对象之间进行通讯
    public abstract void contact(String msg, AbstractPerson abstractPerson);

    // 设置求职者
    public void setJobhunter(Jobhunter jobhunter) {
      this.mJobhunter = jobhunter;
    }

    // 设置老板
    public void setBoss(Boss boss){
        this.mBoss = boss;
    }
}
```
**具体中介者**
```java
//猎头：具体的中介者，抽象的实现，用来协调各个同事之间的调用
public class HeadhuntingMediator extends Mediator{

    @Override
   public void contact(String msg, AbstractPerson person) {
        // 如果是老板，则求职者收到消息
        if(person.equals(mBoss)){
            mJobhunter.receiveMessage(msg);
        }else{
        // 反之，老板收到消息
            mBoss.receiveMessage(msg);
        }
    }
}
```
**抽象同事类**
```java
public abstract class AbstractPerson {
    protected String mName;
    protected Mediator mMediator;

    public AbstractPerson(String name,Mediator mediator) {
        this.mName = name;
        this.mMediator = mediator;
    }
    
    abstract void receiveMessage(String msg);
}

```
**具体同事类**
```java
// 同事类1： 求职者
public class Jobhunter extends AbstractPerson {
    public Jobhunter(String name, Mediator mediator) {
        super(name, mediator);
    }

    public void contact(String msg) {
        mMediator.contact(msg, this);
    }

    @Override
    void receiveMessage(String msg) {
        System.out.println("求职者" + mName + "收到消息： "+msg);
    }
}

//同事类2： 老板
public class Boss extends AbstractPerson {
    public Boss(String name, Mediator mediator) {
        super(name, mediator);
    }

    public void contact(String msg) {
        mMediator.contact(msg, this);
    }

    @Override
    void receiveMessage(String msg) {
        System.out.println("老板" + mName + "收到消息： " + msg);
    }
}
```

**客户端**
```java
// 猎头 老板 求职者
    HeadhuntingMediator mediator = new HeadhuntingMediator();
    Boss boss = new Boss("Jack Ma",mediator);
    Jobhunter jobhunter = new Jobhunter("Pony",mediator);

    mediator.setBoss(boss);
    mediator.setJobhunter(jobhunter);

    jobhunter.contact("Boss您好，我有三年阿里，两年腾讯，四年华为，五年百度的 保洁工作经验");
    boss.contact("吆西，我们就需要的这样的人才，明天来上班！");
```
Log显示：

老板Jack Ma收到消息： Boss您好，我有三年阿里，两年腾讯，四年华为，五年百度的 保洁工作经验

求职者Pony收到消息： 吆西，我们就需要的这样的人才，明天来上班！

### 四、优缺点
#### 优点
- 减少类间的依赖，把原有的一对多的依赖变成了一对一的依赖，同事类只依赖中介者，减少了依赖，当然同时也降低了类间的耦合。
#### 缺点
- 中介者模式的缺点就是中介者会膨胀得很大，而且逻辑复杂，原本N个对象直接的相互依赖关系转换为中介者和同事类的依赖关系，同事类越多，中介者的逻辑就越复杂。

### 五、适用场景
- 　在面向对象的变成语言里，一个类必然会与其他类产生依赖关系，如果这种依赖关系如网状般错综复杂，那么必然会影响我们的代码逻辑以及执行效率，
   适当地使用中介者模式可以对这种依赖关系进行解耦使逻辑结构清晰，但是，如果几个类之间的关系并不复杂，耦合也很少，使用中介者模式反而会使得
   原本不复杂的逻辑结构变得复杂，所以，我们在决定使用中介者模式之前需要多多考虑，权衡利弊。 