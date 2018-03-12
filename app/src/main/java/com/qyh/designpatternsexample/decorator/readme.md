## 装饰模式
### 一、定义
动态地给对象添加一些额外的职责。就增加功能来说，装饰模式生成子类更为灵活。

感觉有点抽象？换个方式理解一下：装饰者模式可以灵活方便的扩展客户端的功能，是继承关系的一种替代方案。
### 二、UML类图及解析
![](https://github.com/qiaoyhh/DesignPatternsEexample/blob/master/app/src/main/res/mipmap-xhdpi/decorator_uml.png)
- 抽象组件(Component)角色：给出一个抽象接口，以规范准备接收附加责任的对象。
- 具体组件(ConcreteComponent)角色：定义一个将要接收附加责任的类(原始类)。
- 装饰(Decorator)角色：持有一个组件(Component)对象的实例，并定义一个与抽象组件接口一致的接口，功能就是装饰我们的组件对象。
- 具体装饰(ConcreteDecorator)角色：负责给组件对象“贴上”附加的责任。
#### 通用代码
**组件抽象类**
```java
public abstract class Component {
    //抽象的方法，如果有具体的业务需求可以增加多个
    public abstract void operate();
}
```
**具体组件实现类**
```java
public class ConcreteComponent extends Component {
    //具体实现
    @Override
    public void operate() {
        System.out.println("do Something");
    }
}
```
**抽象装饰者**
```java
public abstract class Decorator extends Component {
    //内部持有的组件接口对象
    private Component mComponent;
    //必要的构造方法，通过构造函数传递被修饰者
    public Decorator(Component component){
         this.mComponent = component;
    }
    
    //委托给被修饰者执行
    @Override
    public void operate() {
        mComponent.operate();
    }
}
```
**装饰具体实现类**
```java
// 某个具体装饰器实现对象，调用接口方法和具有自己的方法 并能够附加上去
public class ConcreteDecorator1 extends Decorator {
    //依赖注入,定义被修饰者
    public ConcreteDecorator1(Component component){
        super(component);
    }
    
    //根据自己的业务需求，定义需要的修饰方法
    private void method(){
        System.out.println("method 修饰");
    }
    
    //重写父类的方法
    public void operate(){
        this.method1();
        super.operate();
    }
}
```
装饰具体实现类可以有多个，根据自己业务需求扩展其功能，比较灵活，但是原始方法和装饰方法的执行顺序在具体的装饰类是固定的，可以通过方法重载实现多种执行顺序。

**客户端类**
```java
//最初的样子
Component component = new ConcreteComponent();
//修饰一下
component = new ConcreteDecorator1(component);
//修饰后运行
component.operate();
```
刚才我们说装饰者模式是继承者一种方案，相信很多人都比较蒙圈，确实比较抽象，接下来我们用一个例子说一下这是怎么回事。

### 三、举个栗子
场景：我比较喜欢玩一款2K游戏，里面分为PG,SG,SF,PF,C五个职业，刚才新建的时候外形都是一样的。假如我们是这款游戏的开发人员，
     我们肯定需要先分别创建出5个位置的球员供玩家创建，我们用程序描述一下。

#### 2k1.0版本，玩家能创建球员就行
```java
public abstract class AbstractSeat {
    // 位置
    public abstract String seat();
}
```
分别创建5个位置的球员
```java
public class Pg extends AbstractSeat {
    @Override
    public String seat() {
        return "PG";
    }
}
public class SG extends AbstractSeat {
    @Override
    public String seat() {
        return "SG";
    }
}

public class SF extends AbstractSeat {
    @Override
    public String seat() {
        return "SF";
    }
}

public class PF extends AbstractSeat {
    @Override
    public String seat() {
        return "PF";
    }
}

public class C extends AbstractSeat {
    @Override
    public String seat() {
        return "C";
    }
}
```
1.0版本上线后，瞬间增加一大批篮球爱好者的游戏玩家，正当程序开发人员美滋滋享受产品上线后短暂的空闲时间，这个时候产品MM过来对你说："
我们要增加一个功能，部分用户认为皮肤黑打篮球厉害，为了满足用户的需求，所以我们要增加球员皮肤的功能(提供黑，黄，白)"，按照我们以往
的思路，肯定会优先想的继承，但是我们有5个位置的球员，每个球员有3种皮肤可选，选用继承的方案我们需要创建 3*5个类。我们先试一下。

#### 2k 2.0版本，增加球员换肤功能
```java
public class BlackPg extends Pg {
    @Override
    public String seat() {
        System.out.println("黑皮肤位置的pg");
    }
}
public class WhitePg extends Pg {
    @Override
    public String seat() {
        System.out.println("白皮肤的pg");
    }
}

public class YellowPg extends Pg {
    @Override
    public String seat() {
        System.out.println("黄皮肤的pg");
    }
} 
                .
                .
                .
```
其他位置代码相似就不贴了，就这样创建了15个类完成这个功能，测试没问题后，程序上线。又赢得了用户一致好评。这时候，产品MM又过来跟你说：
"玩家感觉游戏里的球员动作（投篮，运球，扣篮等）都一样，感觉没有个性，用户体验也不好，我们需要增加球员的特殊动作，比如科比的投篮包，
乔丹的扣篮包，艾弗森的上篮包。。。"，这个时候，如果我们再按照上面那种继承方式，我想我们就要哭了，这得需要创建多少没用的类呀。这个时候
装饰模式就用了用武之地了，我们把刚才球员换肤那个功能用装饰模式也修改一下，代码如下：

**抽象装饰者**
```java
public  class SeatDecorator extends AbstractSeat {

    private AbstractSeat mAbstractSeat;

    public SeatDecorator(AbstractSeat abstractSeat) {
        this.mAbstractSeat = abstractSeat;
    }

    @Override
    public String seat() {
      return mAbstractSeat.seat();
    }
}
```
**装饰具体实现类**
```java
// 黄皮肤球员
public class YellowPlayer extends SeatDecorator {

    public YellowPlayer(AbstractSeat abstractSeat) {
        super(abstractSeat);
    }

    @Override
    public String seat() {
        System.out.println("创建一个 "+getChangeSkin() + super.seat());
        return getChangeSkin();
    }

    private String getChangeSkin() {
        return "yellow";
    }
}
// 黑皮肤球员
public class BlackPlayer extends SeatDecorator {
    public BlackPlayer(AbstractSeat abstractSeat) {
        super(abstractSeat);
    }

    @Override
    public String seat() {
        System.out.println("创建一个 "+getChangeSkin() + super.seat());
        return getChangeSkin();
    }

    //自定义的修饰方法
    private String getChangeSkin() {
        return "black";
    }
}
// 白皮肤球员
public class WhitePlayer extends SeatDecorator {

    public WhitePlayer(AbstractSeat abstractSeat) {
        super(abstractSeat);
    }

    @Override
    public String seat() {
        System.out.println("创建一个 "+getChangeSkin() + super.seat());
        return getChangeSkin();
    }

    private String getChangeSkin() {
        return "white";
    }
}
```
我们看到，我们的构造方法是调用的super()的构造方法，也就是调用的SeatDecorator的构造方法，我们的seat中也先调用了super.seat()
然后后面加上了我们的装饰内容，也就是说我们传进来的一个AbstractSeat对象被我们装饰了。这就相当于UML中的ConcreteDecorator。

**客户端**
```java
 // 装饰模式测试
    @Test
    public void testDecorator(){
        // 这样我们就可以随意组装不同位置不同皮肤的球员了
        BlackPlayer blackPlayer = new BlackPlayer(new Pg());
        WhitePlayer whitePlayer = new WhitePlayer(new SG());
        YellowPlayer yellowPlayer = new YellowPlayer(new SF());
        blackPlayer.seat();
        whitePlayer.seat();
        yellowPlayer.seat();
    }
```
Log如下，完美~

![](https://github.com/qiaoyhh/DesignPatternsEexample/blob/master/app/src/main/res/mipmap-xhdpi/decorator_log1.png)

接下来我们再设计动作包。
```java
// 科比投篮包
public class KobeShootPacket extends SeatDecorator {

    public KobeShootPacket(AbstractSeat abstractSeat) {
        super(abstractSeat);
    }

    @Override
    public String seat() {
        return super.seat()+addPacket();
    }

    private String addPacket(){
        return "KobeShootPacket";
    }
}

// 乔丹扣篮包
public class JordanDunkPacket extends SeatDecorator {

    public JordanDunkPacket(AbstractSeat abstractSeat) {
        super(abstractSeat);
    }

    @Override
    public String seat() {

        return super.seat()+addPacket();
    }

    private String addPacket(){
        return "JordanDunkPacket";
    }
}
// 艾佛森上篮包
public class IversonLayupPacket extends SeatDecorator {

    public IversonLayupPacket(AbstractSeat abstractSeat) {
        super(abstractSeat);
    }

    @Override
    public String seat() {
        return super.seat()+addPacket();
    }

    private String addPacket() {
        return "IversonLayupPacket";
    }
}
```
**客户端**
```java
    @Test
    public void testDecorator(){
        // 这样我们就可以随意组装不同位置不同皮肤不同动作包的的球员了
        
        // 创建一个黑皮肤用科比投篮包的SG
        BlackPlayer blackPlayer = new BlackPlayer(new KobeShootPacket(new SG()));
        // 创建一个黄皮肤用乔丹扣篮包的SF
        YellowPlayer yellowPlayer = new YellowPlayer(new JordanDunkPacket(new SF()));
        // 创建一个白皮肤用艾佛森上篮包的PG
        WhitePlayer whitePlayer = new WhitePlayer(new IversonLayupPacket(new Pg()));
        
        blackPlayer.seat();
        yellowPlayer.seat();
        whitePlayer.seat();
    }
```
Log如下：完美~

![](https://github.com/qiaoyhh/DesignPatternsEexample/blob/master/app/src/main/res/mipmap-xhdpi/decorator_log2.png)

### 四、优缺点
#### 优点
- 1、装饰模式可以动态地扩展一个实现类的功能，而且不用修改原有代码，符合开闭原则。
- 2、装饰模式是继承关系的一个替代方案，但是比继承灵活。
#### 缺点
- 1、会生成额外的类，增加系统复杂度。
- 2、由于装饰可以层层包装，交叉包装，如果包装的很深的话，调试排错会比较麻烦，增加代码阅读难度。

### 五、使用场景
- 1、需要扩展一个类的功能，或给一个类增加附加功能。
- 2、需要动态地给一个对象增加功能，这些功能可以再动态地撤销。
- 3、需要为一批的兄弟类进行改装或加装功能，当然是首选装饰模式。

### 六、JAVA或Android中提现
- 1、JAVA 中IO
- 2、Android中Context