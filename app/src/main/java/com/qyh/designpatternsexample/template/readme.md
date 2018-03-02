## 模板方法模式
### 一、定义
    模板方法模式是类的行为模式。定义一个模板结构，将具体内容延迟到子类去实现。
### 二、作用
    在不改变模板结构的前提下在子类中重新定义模板中的内容。

### 三、UML类图及结构解析

![](https://github.com/qiaoyhh/DesignPatternsEexample/blob/master/app/src/main/res/mipmap-xhdpi/template_uml.png)

●　Abstemplate：抽象类，定义了一套算法框架

●　ConcreteImplA：具体实现类A

●　ConcreteImplB：具体实现类B
#### 抽象模板(Abstract Template)角色有如下责任：

●　1.定义了一个或多个抽象操作，以便让子类实现。这些抽象操作叫做基本操作，它们是一个顶级逻辑的组成步骤。

●　2.定义并实现了一个模板方法。这个模板方法一般是一个具体方法，它给出了一个顶级逻辑的骨架，而逻辑的组成步骤在相应的抽象操作中，
  推迟到子类实现。顶级逻辑也有可能调用一些具体方法。

#### 具体模板(Concrete Template)角色又如下责任：
●　1.实现父类所定义的一个或多个抽象方法，它们是一个顶级逻辑的组成步骤。

●　2.每一个抽象模板角色都可以有任意多个具体模板角色与之对应，而每一个具体模板角色都可以给出这些抽象方法（也就是顶级逻辑的组成步骤）的不同实现，
  从而使得顶级逻辑的实现各不相同。

#### 模板方法模式中的方法以分为两大类：模板方法和基本方法。
●　模板方法
  一个模板方法是定义在抽象类中的，把基本操作方法组合在一起形成一个总算法或一个总行为的方法。

  一个抽象类可以有任意多个模板方法，而不限于一个。每一个模板方法都可以调用任意多个具体方法。
#### 基本方法又可以分为三种：抽象方法(Abstract Method)、具体方法(Concrete Method)和钩子方法(Hook Method)。
●　1.抽象方法：一个抽象方法由抽象类声明，由具体子类实现。在Java语言里抽象方法以abstract关键字标示。

●　2.具体方法：一个具体方法由抽象类声明并实现，而子类并不实现或置换。

●　3.钩子方法：一个钩子方法由抽象类声明并实现，而子类会加以扩展。通常抽象类给出的实现是一个空实现，作为方法的默认实现。
 ```java
/**
  * 抽象模板角色类，abstractMethod()、hookMethod()等基本方法是顶级逻辑的组成步骤，这个顶级逻辑由templateMethod()方法代表。
  */
 public abstract class AbstractTemplate {
     /**
      * 模板方法
      */
     public void templateMethod(){
         //调用基本方法
         abstractMethod();
         hookMethod();
         concreteMethod();
     }
     /**
      * 基本方法的声明（由子类实现）
      */
     protected abstract void abstractMethod();
     /**
      * 基本方法(空方法)
      */
     protected void hookMethod(){}
     /**
      * 基本方法（已经实现）
      */
     private final void concreteMethod(){
         //业务相关的代码
     }
 }
  ```

 ```java
 /*
  *具体模板角色类，实现了父类所声明的基本方法，abstractMethod()方法所代表的就是强制子类实现的剩余逻辑，
  *而hookMethod()方法是可选择实现的逻辑，不是必须实现的。
  */
 public class ConcreteTemplate extends AbstractTemplate{
     //基本方法的实现
     @Override
     public void abstractMethod() {
         //业务相关的代码
     }
     //重写父类的方法
     @Override
     public void hookMethod() {
         //业务相关的代码
     }
 }
 ```

### 四、DEMO解析
    场景：采用种瓜得瓜种豆得豆，过程：挖坑 --> 栽种 --> 浇水(本例中作为可选行为，目的是为了测试钩子函数) -->埋土
    解析：不管是种瓜还是种豆，挖坑和埋土步骤都是一样的，所以这两个功能的实现需要父类自己一套统一的模板，而种什么类别
         父类不知道，需不需要浇水也不知道，所以这两个需要子类自己去实现。


**抽象模板角色类**

```java
public abstract class AbstractTemplate {

    private boolean mIsWatering;

    // 模板方法
    public void templateMothod() {
        dig_holes();
        plant();
        watering();
        mulch();
    }

    //基本方法，已经实现
    private final void dig_holes() {
        // 省略一系列实现步骤
        System.out.println("挖坑 固定的套路，所以实现方法封装在父类中");
    }

    //基本方法。种植是可变的，因为父类不知道具体种植的种类，所以有子类自己去实现
    protected abstract void plant();

    //基本方法，已经实现,但是这个功能需要子类自己决定
    private void watering() {
        if (mIsWatering) {
            System.out.println("生命力脆弱，需要浇水");
        } else {
            System.out.println("生命力顽强，不用浇水");
        }
    }

    //基本方法，已经实现
    private final void mulch() {
        // 省略一系列实现步骤
        System.out.println("盖土 固定的套路，所以实现方法封装在父类中");
    }

    // 钩子函数，由子类自己决定
    public void isWatering(boolean isWatering) {
        this.mIsWatering = isWatering;
    }
}
 ```

**具体模板**

  ```java
  /**
   * 具体模板   种豆得豆
   */

  public class PlantPeasTemplate extends AbstractTemplate {

      @Override
      protected void plant() {
          System.out.println("种豆得豆");
      }
  }

  public class PlantMelonTemplate extends AbstractTemplate {
      @Override
      protected void plant() {
          System.out.println("种瓜得瓜");
      }
  }
  ```

**客户端类**

```java
 PlantMelonTemplate melonTemplate = new PlantMelonTemplate();
   melonTemplate.isWatering(true);
   melonTemplate.templateMothod();

   PlantPeasTemplate peasTemplate = new PlantPeasTemplate();
   peasTemplate.isWatering(false);
   peasTemplate.templateMothod();
  ```

### 五、优缺点
#### 优点:
●　1.封装不变的部门，扩张可变部分
●　2.提取公共代码，便于维护
●　3.行为由父类控制，子类可以通过扩张的方式增加相应功能，符合开闭原则。
#### 缺点
●　需要为每一个基本方法的不同实现提供一个子类，如果父类中可变的基本方法太多，将会导致类的个数增加，系统更加庞大，设计也更加抽象，
此时，可结合桥接模式来进行设计。

### 六、适用场景
●　1.多个子类有公共的方法，并且逻辑基本相同的情况。（BaseActivity,BaseFragment）

●　 2.重要、复杂的算法，可以把核心的算法实现设计为模板方法，细节由子类去完成。
