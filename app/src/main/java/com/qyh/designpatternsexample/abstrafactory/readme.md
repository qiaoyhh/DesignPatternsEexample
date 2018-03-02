## 抽象工厂模式
### 一、定义：
●　为创建一组相关或依赖的对象提供一个接口，而无需指定他们的具体类型。是工厂方法模式的升级版。

#### 与工厂方法模式的区别：
●　1.工厂方法每个工厂只能创建一个类型的产品，而抽象工厂可以创建多个类型的产品。

●　2.工厂方法产出的一个产品（实例），抽象工厂是创建新工厂。

### 二、UML类图及解析：

![](https://github.com/qiaoyhh/DesignPatternsEexample/blob/master/app/src/main/res/mipmap-xhdpi/abstartfactory_uml.png)

| 组成（角色）                    | 关系                             | 作用                        |
| ------------------------------| --------------------------------| -------------------------- |
| 抽象产品族（AbstractProduct）    | 抽象产品的父类                   | 描述抽象产品的公共接口 |
| 抽象产品（AbstractProduct）      | 具体产品的父类	                  | 描述具体产品的公共接口 |
| 具体产品（ConcreteProduct）    | 抽象产品的子类；工厂类创建的目标类   |  描述生产的具体产品  |
| 抽象工厂（AbstractFactory）	    | 具体工厂的父类	                  |  描述具体工厂的公共接口 |
| 具体工厂（ConcreteFactory）     | 抽象工厂的子类；被外界调用	         | 描述具体工厂；实现FactoryMethod工厂方法创建产品的实例 |


### 三、使用步骤：
●　步骤1： 创建抽象工厂类，定义具体工厂的公共接口；

●　步骤2： 创建抽象产品族类 ，定义抽象产品的公共接口；

●　步骤3： 创建抽象产品类 （继承抽象产品族类），定义具体产品的公共接口；

●　步骤4： 创建具体产品类（继承抽象产品类） & 定义生产的具体产品；

●　步骤5： 创建具体工厂类（继承抽象工厂类），定义创建对应具体产品实例的方法；

●　步骤6： 客户端通过实例化具体的工厂类，并调用其创建不同目标产品的方法创建不同具体产品类的实例

注：有N个产品族，在抽象工厂中就应该有N个创建方法。

### 四、Demo解析：
demo描述：女娲造人，之前造了 黑皮肤和白皮肤两种肤色的人种，发现都是同一性别的，之后发生的事情就有点混乱（自己想象，哈哈）
         ，接下来女娲想造出两种不同性别，不同肤色的人。所以女蜗就创造了一个生产黑皮肤，一个生产白皮肤人类的工厂。

**步骤1： 创建抽象工厂类，定义具体工厂的公共接口**
```java
public interface HumanFactory {
// 创建白皮肤产品族
Human createWhiteHuman();
// 创建黑皮肤产品族
Human createBlackHuman();
}
 ```

 **步骤2： 创建抽象产品族类 ，定义抽象产品的公共接口；**

```java
public interface Human {
    void getColor();

    void talk();

    void getSex();
}
```

**步骤3： 创建抽象产品类 （继承抽象产品族类），定义具体产品的公共接口**

```java
  // 负责人种的公共属性的定义。
public abstract class AbstractBlackHuman implements Human {

  @Override
  public void getColor() {
      System.out.println("AbstractBlackHuman black");
  }

  @Override
  public void talk() {
      System.out.println("AbstractBlackHuman talk");
  }

  public abstract class AbstractWhiteHuman implements Human {

      @Override
      public void getColor() {
          System.out.println("AbstractWhiteHuman white");
      }

      @Override
      public void talk() {
          System.out.println("AbstractWhiteHuman talk");
      }
  }
}
 ```

 **步骤4： 创建具体产品类（继承抽象产品类） & 定义生产的具体产品；**

```java
public class FemaleBlackHuman extends AbstractBlackHuman {

    @Override
    public void getSex() {
        System.out.println("FemaleBlackHuman 生产一个黑皮肤美女");
    }
}

public class FemaleWhiteHuman extends AbstractWhiteHuman {
    @Override
    public void getSex() {
        System.out.println("FemaleWhiteHuman 生产一个白皮肤美女");
    }
}

public class MaleBlackHuman extends AbstractBlackHuman {
    @Override
    public void getSex() {
        System.out.println("MaleBlackHuman 生产一个黑皮肤大汉");
    }
}

public class MaleWhiteHuman extends AbstractWhiteHuman {
    @Override
    public void getSex() {
        System.out.println("MaleWhiteHuman 生产一个白皮肤大汉");
    }
}
```
**步骤5： 创建具体工厂类（继承抽象工厂类），定义创建对应具体产品实例的方法；**

```java
// 生产美女的工厂
public class FemaleHumanFactory implements HumanFactory {
    @Override
    public Human createWhiteHuman() {
        return new FemaleWhiteHuman();
    }

    @Override
    public Human createBlackHuman() {
        return new FemaleBlackHuman();
    }
}

// 生产男性的工厂（区分颜色）
public class MaleHumanFactory implements HumanFactory {
    @Override
    public Human createWhiteHuman() {
        return new MaleWhiteHuman();
    }

    @Override
    public Human createBlackHuman() {
        return new MaleBlackHuman();
    }
}
```

**步骤6：客户端通过实例化具体的工厂类，并调用其创建不同目标产品的方法创建不同具体产品类的实例**

```java
 // 生产男性的生产线
    FemaleHumanFactory femaleHumanFactory = new FemaleHumanFactory();
    // 生产女性的生产线
    MaleHumanFactory maleHumanFactory = new MaleHumanFactory();
    //开始产人
    Human blackHuman = femaleHumanFactory.createBlackHuman();
    blackHuman.getColor();
    blackHuman.talk();
    blackHuman.getSex();

    Human whiteHuman = maleHumanFactory.createWhiteHuman();
    whiteHuman.getColor();
    whiteHuman.talk();
    whiteHuman.getSex();
```

### 五、优缺点
#### 优点：
●　1.封装性，降低耦合度：每个产品的实现类不是高层所关心的，只关心抽象和接口，不关心对象如何创建出来，由谁负责。只需知道工厂是谁，就能创建一个对象。

●　2.更符合开闭原则：新增一个产品类时，只需增加相应具体产品类和抽象工厂子类即可。

●　3.更符合单一原则：每个具体工厂类只负责创建相应的产品。

#### 缺点：
●　扩展产品族困难，需要更改的代码太多。如果要增加一个产品 C， 也就是说产品家族由原来的 2 个增加到 3 个，看看我们的程序有多大改动吧！
     抽象类 AbstractCreator 要增加一个方法 createProductC()， 然后两个实现类都要修改，想想看，这严重违反了开闭原则，
     而且我们一直说明抽象类和接口是一个契约。

注：虽然抽象工厂模式扩展产品族困难，但是扩展产品类别比较容易，
