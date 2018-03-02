## 代理模式
### 一、定义
- 给目标对象提供一个代理对象，并由代理对象控制对目标对象的引用；
- 注：代理对象起到中介作用，链接客户端和目标对象。
### 二、代理分类
- 静态代理：代理者的代码有程序员自己或通过一些自动化工具生成固定的代码再对其进行编译，也就是说在我们代码运行前代理类的class编译文件就已经存在。
- 动态代理：与静态代理相反，通过发射机制动态地生成代理者的对象，也就是说我们code阶段压根就不需要知道代理谁，代理谁我们在执行阶段决定。

### 二、静态代理组成
- Subject:抽象类或者接口，声明真实对象和代理者行为的公共类
- RealSubject：真实对象，被代理的对象，最终引用
- Proxy：代理对象，包含对真实对象的引用，代表真实对象

### 三、静态代理案例
场景：网瘾少年玩游戏，打怪升级

**所有玩家接口，定义玩家的行为(Subject)**
```java
public interface IGamePlayer {
    // 登录
    void login(String name, String password);
    // 打怪
    void killBoss();
    // 升级
    void upgrade();
}
```

**模拟玩家游戏场景(RealSubject)**

```java
public class GamePlayer implements IGamePlayer{

    private String mName;
    public GamePlayer(String name) {
        this.mName = name;
    }

    @Override
    public void login(String name, String password) {
        System.out.println("用户"+name+"登录成功");
    }

    @Override
    public void killBoss() {
        System.out.println("用户"+mName+"开启超神模式，十连杀");
    }

    @Override
    public void upgrade() {
        System.out.println("恭喜用户"+mName+"成功升到110级");
    }
}
```
**客户端**

```java
    GamePlayer gamePlayer = new GamePlayer("jordan");
    gamePlayer.login("jordan","jjj");
    gamePlayer.killBoss();
    gamePlayer.upgrade();
 ```
●　客户端运行结果

<p> 用户jordan登录成功</p>
<p>用户jordan开启超神模式，十连杀</p>
<p>恭喜用户jordan成功升到110级</p>

 #### 接下来我们改变一下需求，jordan这个玩家由于长时间沉迷于游戏中不能自拔，久而久之终于光荣地住进了医院，但是他的游戏正处于打怪升级的关键时候，怎么办？很简单 找个代练，接下来，我们模拟一下这个场景，前面几个类不变，增加一个代理类。

**游戏代理者，同样需要登录游戏，打怪，升级(Proxy)**

```java
public class GamePlayProxy implements IGamePlayer{
  private IGamePlayer mIGamePlayer;

  public GamePlayProxy(IGamePlayer iGamePlayer ) {
      this.mIGamePlayer = iGamePlayer;
  }
  // 代练升级
  @Override
  public void login(String name, String password) {
     mIGamePlayer.login(name,password);
  }

  // 代练打怪
  @Override
  public void killBoss() {
      mIGamePlayer.killBoss();
  }

  // 代练升级
  @Override
  public void upgrade() {
      mIGamePlayer.upgrade();
  }
}
```

**客户端**

```java
GamePlayer gamePlayer = new GamePlayer("jordan");
// 游戏代练者，需要以游戏玩家的身份玩游戏
GamePlayProxy gamePlayProxy =new GamePlayProxy(gamePlayer);

gamePlayProxy.login("jordan","jjj");
gamePlayProxy.killBoss();
gamePlayProxy.upgrade();
```

### 四、动态态代理案例
现在有个非常流行的程序叫做面向切面编程(AOP),其核心就是采用了动态代理的方式。怎么用？Java为我们提供了一个便捷的动态代理接口 InvocationHandler，实现该接口需要重写其调用方法invoke
```java
public class DynamicProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
```
在这里，我们主要通过invoke()来调用具体的被代理方法，也就是真实的方法。
接下来我们看一下动态代理的实际用法，接着上面游戏代练的例子。新增一个GamePlayIH类并实现InvocationHandler接口，然后动态代理是根据被代理的接口生成所有的方法

**动态代理类**
```java
public class GamePlayIH implements InvocationHandler {
    private Object mObject;

    public GamePlayIH(Object object) {
        this.mObject = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(this.mObject, args);
        return result;
    }
}
```
**客户端**
```java
IGamePlayer gamePlayer = new GamePlayer("jordan");
InvocationHandler handler = new GamePlayIH(gamePlayer);
ClassLoader classLoader = gamePlayer.getClass().getClassLoader();
// 动态的产生一个代理者
IGamePlayer proxy = (IGamePlayer) Proxy.newProxyInstance(classLoader, new Class[]{IGamePlayer.class}, handler);
// 代替玩家开始犀利的操作
proxy.login("jordan","jjj");
proxy.killBoss();
proxy.upgrade();
```
程序完美运行,Log显示跟上面一样，接下来让我们看看程序是怎么实现的，我们知其然还要知所以然。
```java
ClassLoader classLoader = gamePlayer.getClass().getClassLoader();
// 动态的产生一个代理者
IGamePlayer proxy = (IGamePlayer) Proxy.newProxyInstance(classLoader, new Class[]{IGamePlayer.class}, handler);
```
我们看一下这个方法，该方法时重新生成了一个对象，为什么要重新生成？因为我们需要用代理，new Class[]{IGamePlayer.class}是说查找到该类的所有接口，然后实现接口
的所有方法。当然了，方法都是空的，由谁负责接管呢？handler这个对象，于是我们知道了一个类的动态代理是这样一个类由InvocationHandler的实现类实现所有的方法，
由其invoke()接管所有方法的实现。

##### 注：要实现动态代理的首要条件：被代理类必须实现一个接口
### 五、优缺点
#### 优点：
- 1.协调调用者和被调用者，降低了系统的耦合度
- 2.代理对象作为客户端和目标对象之间的中介，起到了保护目标对象的作用
#### 缺点：
- 1.由于在客户端和真实主题之间增加了代理对象，因此会造成请求的处理速度变慢；
- 2.实现代理模式需要额外的工作（有些代理模式的实现非常复杂），从而增加了系统实现的复杂度。