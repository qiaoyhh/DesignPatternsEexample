## Builder模式
### 一、定义
将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示

### 二、UML类图及解析
![](https://github.com/qiaoyhh/DesignPatternsEexample/blob/master/app/src/main/res/mipmap-xhdpi/builder_uml.png)
- Builder（抽象建造者）：它为创建一个产品Product对象的各个部件指定抽象接口，在该接口中一般声明两类方法，一类方法是buildPartX()，它们用于创建复杂对象的各个部件；另一类方法是getResult()，它们用于返回复杂对象。Builder既可以是抽象类，也可以是接口。
- ConcreteBuilder（具体建造者）：它实现了Builder接口，实现各个部件的具体构造和装配方法，定义并明确它所创建的复杂对象，也可以提供一个方法返回创建好的复杂产品对象。
- Product（产品角色）：它是被构建的复杂对象，包含多个组成部件，具体建造者创建该产品的内部表示并定义它的装配过程。
- Director（指挥者）：指挥者又称为导演类，它负责安排复杂对象的建造次序，指挥者与抽象建造者之间存在关联关系，可以在其construct()建造方法中调用建造者对象的部件构造与装配方法，完成复杂对象的建造。客户端一般只需要与指挥者进行交互，在客户端确定具体建造者的类型，并实例化具体建造者对象（也可以通过配置文件和反射机制），然后通过指挥者类的构造函数或者Setter方法将该对象传入指挥者类中。
Builder模式的变种(Android Builder模式)

### Builder模式的目的在于减少对象创建过程中引用的多 个重载构造方法、可选参数和setters过度使用导致的不必要的复 杂性。下面以一个简单对象Person对象创建过程为例子说明，它 具有如下所示的属性，且都是不可变的final的。

```java
public class Person {
    private final String name;
    private final int age;
    private final double height;
    private final double weight;

我们给Person增加一个静态内部类Builder类，并修改Person类的构造函数

public class Person {
    private final String name;
    private final int age;
    private final double height;
    private final double weight;
    private Person(Builder builder){
        this.name=builder.name;
        this.age=builder.age;
        this.height=builder.height;
        this.weight=builder.weight;
    }


    static class Builder{
        private String name;
        private int age;
        private double height;
        private double weight;
        public Builder name(String name){
            this.name=name;
            return this;
        }
        public Builder age(int age){
            this.age=age;
            return this;
        }
        public Builder height(double height){
            this.height=height;
            return this;
        }
        public Builder weight(double weight){
            this.weight=weight;
            return this;
        }
        public Person build(){
            return new Person(this);
        }
    }
}
```

从上面的代码中我们可以看到，我们在Builder类里定义了一份与Person类一模一样的变量，通过一系列的成员函数进行设置属性值，但是返回值都是this，也就是都是Builder对象，最后提供了一个build函数用于创建Person对象，返回的是Person对象，对应的构造函数在Person类中进行定义，也就是构造函数的入参是Builder对象，然后依次对自己的成员变量进行赋值，对应的值都是Builder对象中的值。此外Builder类中的成员函数返回Builder对象自身的另一个作用就是让它支持链式调用，使代码可读性大大增强。

于是我们就可以这样创建Person类。

Person.Builder builder=new Person.Builder();
Person person=builder
  .name("qyh")
  .age(26)
  .height(180)
  .weight(80)
  .build();

### 最后总结一下Android Builder模式的用法：

- 定义一个静态内部类Builder，内部的成员变量和外部类一样
- Builder类通过一系列的方法用于成员变量的赋值，并返回当前对象本身（this）
- Builder类提供一个build方法或者create方法用于创建对应的外部类，该方法内部调用了外部类的一个私有构造函数，该构造函数的参数就是内部类Builder
- 外部类提供一个私有构造函数供内部类调用，在该构造函数中完成成员变量的赋值，取值为Builder对象中对应的值
### 优点
- 在建造者模式中，客户端不必知道产品内部组成的细节，将产品本身与产品的创建过程解耦，使得相同的创建过程可以创建不同的产品对象。
- 每一个具体建造者都相对独立，而与其他的具体建造者无关，因此可以很方便地替换具体建造者或增加新的具体建造者，用户使用不同的具体建造者即可得到不同的产品对象。由于指挥者类针对抽象建造者编程，增加新的具体建造者无须修改原有类库的代码，系统扩展方便，符合“开闭原则”
- 可以更加精细地控制产品的创建过程。将复杂产品的创建步骤分解在不同的方法中，使得创建过程更加清晰，也更方便使用程序来控制创建过程。
### 缺点
- 建造者模式所创建的产品一般具有较多的共同点，其组成部分相似，如果产品之间的差异性很大，例如很多组成部分都不相同，不适合使用建造者模式，因此其使用范围受到一定的限制。
- 如果产品的内部变化复杂，可能会导致需要定义很多具体建造者类来实现这种变化，导致系统变得很庞大，增加系统的理解难度和运行成本。
### 适用场景
- 需要生成的产品对象有复杂的内部结构，这些产品对象通常包含多个成员属性。
- 需要生成的产品对象的属性相互依赖，需要指定其生成顺序。
- 对象的创建过程独立于创建该对象的类。在建造者模式中通过引入了指挥者类，将创建过程封装在指挥者类中，而不在建造者类和客户类中。
- 隔离复杂对象的创建和使用，并使得相同的创建过程可以创建不同的产品。

