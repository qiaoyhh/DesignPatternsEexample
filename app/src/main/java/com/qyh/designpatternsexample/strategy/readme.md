## 策略模式
### 一、定义
定义一组算法，将每个算法封装起来，并使它们可以互换。
### 二、UML类图及解析
![](https://github.com/qiaoyhh/DesignPatternsEexample/blob/master/app/src/main/res/mipmap-xhdpi/strategy_uml.png)

- 环境(Context)角色：持有一个Strategy的引用，用来操作策略的上下文环境。屏蔽高层模块对策略、算法的直接访问。
- 抽象策略(Strategy)角色：这是一个抽象角色，通常由一个接口或抽象类实现。此角色给出所有的具体策略类所需的接口。
- 具体策略(ConcreteStrategy)角色：实现抽象策略中的操作，含有具体的算法或行为。

策略模式是对算法的包装，是把调用算法的责任（行为）和算法本身（行为实现）分割开来，委派给不同的对象管理从而降低耦合问题。
通常如果一个问题有多个解决方案时候，最简单的方式就是利用if-else或者swithc-case方式根据不同的情况选择不同的解决方案，
但是这种方案有时候会有耦合性太高或者代码臃肿的问题。而应对这种情况策略模式就能很好的解决这个问题。

**抽象策略模式**
```java
public interface Strategy {
    //策略模式的运算法则
    public void doSomething();
}
```
**具体策略角色**
```java
public class ConcreteStrategy1 implements Strategy {
    public void doSomething() {
       System.out.println("具体策略1的运算法则");
    }
}
public class ConcreteStrategy2 implements Strategy {
    public void doSomething() {
       System.out.println("具体策略2的运算法则");
    }
}
```
策略模式的重点就是封装角色，它是借用了代理模式的思路。

**封装角色**
```java
public class Context {
    //抽象策略
    private Strategy strategy = null;
    //构造函数设置具体策略
    public Context(Strategy _strategy){
         this.strategy = _strategy;
    }
    //封装后的策略方法
    public void doAnythinig(){
        this.strategy.doSomething();
    }
}
```
**客户端**
```java
//声明一个具体的策略
Strategy strategy = new ConcreteStrategy1();
//声明上下文对象
Context context = new Context(strategy);
//执行封装后的方法
context.doAnythinig();
```
### 三、举个栗子
场景：一年一度的双十一到了，jack ma宣布：

    算法一： 普通用户      不打折
    
    算法二： 黄金会员      10%折扣 
    
    算法三： 钻石会员      20%折扣
假如我们是维护这个产品的开发人员，算法工程师已经给我们写好了算法，就等着我们去调用了。按照我们以往的思路，先判断用户的级别
(if-else/ swith-case)，然后再根据对应的折扣算出最后应付的钱，假如我们这个算法很复杂，如果用判断用户级别的方法会过给我
们代码造成很大的臃肿，所以我换个方式去实现，尝试用一下策略模式做一下：

**抽象策略模式**
```java
public interface IDiscountStrategy {
    // 传入总价，计算出打折后的价钱
    double calcDiscount(double money);
}
```
**具体策略角色**
```java
// 普通用户 不打折
public class CommonStrategy implements IDiscountStrategy{
    @Override
    public double calcDiscount(double money) {
        // 省略一系列复杂算法
        return money;
    }
}

// 黄金会员，10%折扣
public class GoldStrategy implements IDiscountStrategy {
    @Override
    public double calcDiscount(double money) {
        return money - money * 0.1;
    }
}

// 钻石会员，20%折扣
public class JewelStrategy implements IDiscountStrategy {
    @Override
    public double calcDiscount(double money) {
        return money - money * 0.2;
    }
}
```
**封装角色**
```java
// 计算打折后价钱类（对应环境类）
public class DiscountNum {
    private IDiscountStrategy mIDiscountStrategy;

    //构造参数，传入具体的策略对象
    public DiscountNum(IDiscountStrategy iDiscountStrategy) {
        this.mIDiscountStrategy = iDiscountStrategy;
    }

    // 计算打折后价钱
    public double calculateTax(double money) {
        return this.mIDiscountStrategy.calcDiscount(money);
    }
}
```
**客户端**
```java
 // 策略模式测试
    @Test
    public void testStrategy(){
        // 创建需要使用的策略对象
        IDiscountStrategy commonStrategy = new CommonStrategy();
        IDiscountStrategy goldStrategy = new GoldStrategy();
        IDiscountStrategy jewelStrategy = new JewelStrategy();
        // 创建环境
        DiscountNum commonNum = new DiscountNum(commonStrategy);
        DiscountNum goldNum = new DiscountNum(goldStrategy);
        DiscountNum jewelNum = new DiscountNum(jewelStrategy);
        // 计算价钱
        double commonDiscount = commonNum.calculateTax(5000);
        double goldDiscount = goldNum.calculateTax(5000);
        double jewelDiscount = jewelNum.calculateTax(5000);

        System.out.println("普通用户需要交钱数 "+commonDiscount);
        System.out.println("黄金会员需要交钱数 "+goldDiscount);
        System.out.println("钻石会员需要交钱数 "+jewelDiscount);
    }
```
Log打印如下,完美~

![](https://github.com/qiaoyhh/DesignPatternsEexample/blob/master/app/src/main/res/mipmap-xhdpi/strategy_log.png)

个人认为策略模式还是比较简单的，它只是采用了面向对象和多态的机制，替代了复杂的if-else。

### 四、优缺点
#### 优点：
- 1、算法自由切换，只要实现抽象策略，它就成为策略家族的一个成员，通过封装角色对其进行封装，保证对外提供可自由切换的策略。
- 2、避免多重条件判断，减少出错率
- 3、扩展性好，增加一个策略只需实现抽象策略接口就可以了。
#### 缺点：
- 1、每一个策略就一个类，导致类的数量增加
- 2、所有的策略都需要对外暴露，因为上层模块只有知道了哪些策略，才能决定调用哪个策略，与迪米特法则相背。

### 五、使用场景
- 1、多个类只有在算法或行为上有区别时。
- 2、算法自由切换的场景
- 3、需要屏蔽算法规则的场景，只需知道算法名字，传相关参数就可以。