
## 工厂方法模式
### 一、定义：
工厂方法模式，又称工厂模式、多态工厂模式和虚拟构造器模式，通过定义工厂父类负责定义创建对象的公共接口，而子类则负责生成具体的对象。

总结：工厂方法使一个类的的实例化延迟到子类中。

### 二、模式组成：
| 组成（角色）                    | 关系                             | 作用                        |
| ------------------------------| --------------------------------| -------------------------- |
| 抽象产品（Product）             | 具体产品的父类	                  | 描述具体产品的公共接口 |
| 具体产品（Concrete Product）    | 抽象产品的子类；工厂类创建的目标类   |  描述生产的具体产品  |
| 抽象工厂（Creator）	            | 具体工厂的父类	                  |  描述具体工厂的公共接口 |
| 具体工厂（Concrete Creator）    | 抽象工厂的子类；被外界调用	         | 描述具体工厂；实现FactoryMethod工厂方法创建产品的实例 |

### 三、使用步骤：
●　步骤1： 创建抽象工厂类，定义具体工厂的公共接口；

●　步骤2： 创建抽象产品类 ，定义具体产品的公共接口；

●　步骤3： 创建具体产品类（继承抽象产品类） & 定义生产的具体产品；

●　步骤4： 创建具体工厂类（继承抽象工厂类），定义创建对应具体产品实例的方法；

●　步骤5： 外界通过调用具体工厂类的方法，从而创建不同具体产品类的实例

### 四、Demo解析：
场景：女蜗造人，以肤色区别人群。

```java
/**
 * 步骤一：创建抽象工厂类，定义具体工厂的公共接口
 */

public abstract class AbstractHumanFactory {
// Class<T> 两层含义  1.必须是class类型 2.必须是human的实现类
public abstract <T extends Human> T createHuman(Class<T> tClass);
}
```

```java
/**
 *
 * 步骤二：创建抽象产品类 ，定义具体产品的公共接口；
 */

public interface Human {
    void getColor();

    void talk();
}
```

```java
/**
 *
 * 步骤三：创建具体产品类（继承抽象产品类）， 定义生产的具体产品
 */

public class BlackHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("BlackHuman black");
    }

    @Override
    public void talk() {
        System.out.println("BlackHuman talk");
    }
}

public class WhiteHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("WhiteHuman  white");
    }

    @Override
    public void talk() {
        System.out.println("WhiteHuman  talk");
    }
}

```

```java
/**
 *
 * 步骤四：创建具体工厂类（继承抽象工厂类），定义创建对应具体产品实例的方法；
 */

public class HumanFactory extends AbstractHumanFactory {

    @Override
    public <T extends Human> T createHuman(Class<T> tClass) {
        Human human = null;
        try {
            human = (Human) Class.forName(tClass.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) human;
    }
}
```

```java
/**
 *
 * 步骤五：外界通过调用具体工厂类的方法，从而创建不同具体产品类的实例；
 */
    HumanFactory humanFactory =new HumanFactory();
    BlackHuman blackHuman = humanFactory.createHuman(BlackHuman.class);
    blackHuman.getColor();
    blackHuman.talk();

    WhiteHuman whiteHuman = humanFactory.createHuman(WhiteHuman.class);
    whiteHuman.getColor();
    whiteHuman.talk();
```

### 五、工厂方法模式的优缺点
#### 优点：
●　1.良好的封装性，代码结构清晰，一个对象的创建是有约束的，如果一个调用者需要一个具体的产品对象，只需这个产品名，不需要知道创建过程，降低模块间耦合。

●　2.扩展性非常优秀，在增加产品对象时候，只需增加一个具体的产品类，就可以完成。（拥抱变化）

●　3.屏蔽产品类，产品类的实现如何变化，调用者根本不用关心，只要接口保持不变，调用者就不需要改变。（符合迪米特法则）

#### 缺点：
●　1.在添加新产品时，需要编写新的具体产品类，而且还要提供与之对应的具体工厂类，系统中类的个数将成对增加，在一定程度上增加了系统的复杂度，

 有更多的类需要编译和运行，会给系统带来一些额外的开销。

●　2.由于考虑到系统的可扩展性，需要引入抽象层，在客户端代码中均使用抽象层进行定义，增加了系统的抽象性和理解难度，且在实现时可能需要用到DOM、
 反射等技术，增加了系统的实现难度。

### 六、适应场景
●　1.一个类不知道它所需要的对象的类：在工厂方法模式中，客户端不需要知道具体产品类的类名，只需要知道所对应的工厂即可，具体的产品对象由具体工厂类创建；
  客户端需要知道创建具体产品的工厂类。

●　2.一个类通过其子类来指定创建哪个对象：在工厂方法模式中，对于抽象工厂类只需要提供一个创建产品的接口，而由其子类来确定具体要创建的对象，
  利用面向对象的多态性和里氏代换原则，在程序运行时，子类对象将覆盖父类对象，从而使得系统更容易扩展。

●　3，将创建对象的任务委托给多个工厂子类中的某一个，客户端在使用时可以无须关心是哪一个工厂子类创建产品子类，需要时再动态指定，
  可将具体工厂类的类名存储在配置文件或数据库中。
