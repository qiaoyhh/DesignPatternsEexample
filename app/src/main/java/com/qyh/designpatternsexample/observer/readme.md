## 观察者模式
### 一、定义：
定义对象之间一种一对多的依赖关系，使得每当一个对象改变状态，所有依赖于它的对象都会收到通知并被自动更新。
### 二、UML类图及解析
![](https://github.com/qiaoyhh/DesignPatternsEexample/blob/master/app/src/main/res/mipmap-xhdpi/observer_uml.png)

- Subject : 抽象被观察者（Observeable),把所有对观察者对象的引用保存在一个聚集（比如ArrayList对象）里，每个主题都可以有任何数量的观察者。提供一个接口，可以增加和删除观察者对象
- ConcreteSubject： 具体的被观察者，将有关状态存入具体的观察者对象，在具体的被观察者内部状态发生变化时，给所有注册的观察者发送通知。
- Observer ： 抽象观察者，定义了一个更新接口，使得在得到被观察者的通知时更新自己。
- ConcreteObserver ： 具体的观察者，实现了抽象观察者锁定义的接口，用来在收到通知时更新自己。

**抽象观察者**
```java
public interface IObserver {
    void update();
}
```
**具体观察者**
```java
public class ConcreteSubject extends Subject {

    public void method1(){
       super.notifyObservers();
    }
}
```
**抽象被观察者**
```java
public abstract class Subject {
    ArrayList<IObserver> observers = new ArrayList<>();

    //增加观察者
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    //删除一个观察者

    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    // 自身发生改变，通知所有观察者
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update();
        }
    }
}
```
**具体被观察者**
```java
public class ConcreteSubject extends Subject {

    public void changeState(){
       super.notifyObservers();
    }
}
```
**客户端**
```java
// 观察者模式测试
@Test
public void testObserver(){
    // 创建被观察者
    ConcreteSubject concreteSubject = new ConcreteSubject();
    // 定义一个观察者
    IObserver observer1 = new ConcreteObserver();
    // 把观察者注入到被观察者中
    concreteSubject.addObserver(observer1);
    //被观察者发生改变
    concreteSubject.changeState();
}
```
当调用concreteSubject.changeState()时，说明当前被观察者状态发生改变，之后将通知所有观察者，观察者接收到通知后，将可以进行其他操作，进行响应。

### 三、Android源码中实现
以前常用到的控件ListView，其中最重要的一个点就是Adapter，在我们往ListView添加数据后，我们都会调用一个方法:
notifyDataSetChanged(), 这个方法就是用到了我们所说的观察者模式。
跟进这个方法notifyDataSetChanged方法，这个方法定义在BaseAdapter中，代码如下:
```java
public abstract class BaseAdapter implements ListAdapter, SpinnerAdapter {
    // 数据集观察者
    private final DataSetObservable mDataSetObservable = new DataSetObservable();
    // 注册观察者
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }
    // 注销观察者
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    /**
     * Notifies the attached observers that the underlying data has been changed
     * and any View reflecting the data set should refresh itself.
     * 当数据集用变化时通知所有观察者
     */
    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }
} 
```
可以发现，当数据发生变化时候，notifyDataSetChanged中会调用mDataSetObservable.notifyChanged()方法。
```java
public class DataSetObservable extends Observable<DataSetObserver> {
    /**
     * Invokes onChanged on each observer. Called when the data set being observed has
     * changed, and which when read contains the new state of the data.
     */
    public void notifyChanged() {
        synchronized(mObservers) {
            // 调用所有观察者的onChanged方式
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onChanged();
            }
        }
    }
}
```
mDataSetObservable.notifyChanged()中遍历所有观察者，并且调用它们的onChanged方法。

那么这些观察者是从哪里来的呢？首先ListView通过setAdapter方法来设置Adapter
```java
 @Override
    public void setAdapter(ListAdapter adapter) {
        // 如果已经有了一个adapter实例,那么先注销该Adapter对应的观察者
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        // 代码省略
        super.setAdapter(adapter);

        if (mAdapter != null) {
            mAreAllItemsSelectable = mAdapter.areAllItemsEnabled();
            mOldItemCount = mItemCount;
            // 获取数据的数量
            mItemCount = mAdapter.getCount();
            checkFocus();
            //创建一个数据集观察者
            mDataSetObserver = new AdapterDataSetObserver();
            // 将这个观察者注册到Adapter中，实际上是注册到DataSetObservable中
            mAdapter.registerDataSetObserver(mDataSetObserver);

            // 代码省略
        } else {
            // 代码省略
        }

        requestLayout();
    }
```
在设置Adapter时会构建一个AdapterDataSetObserver，最后将这个观察者注册到adapter中，这样我们的被观察者、观察者都有了。

总结：观察者模式主要的作用就是对象解耦，在Android源码中用到观察者模式的地方还有很多，比如：BroadcastReceiver、TextWatcher、包括RXjava、EventBus等等。像我们平时写的
回调也属于观察者的一种形式。


### 四、优缺点
#### 优点：
- 观察者和被观察者之间是抽象耦合，增加删除观察者或者被观察者都比较方便。
#### 缺点：
- 在设计模式之禅这本书中是这样总结观察者缺点的(Android源码设计模式也是这样写的，几乎一模一样，手动笑脸)：Java中的消息默认是顺序执行，如果一个观察者卡顿，会造成整个系统效率变低，解决方式是：异步。
不过这种说法是说一对多的情况，一对一情况是不是就可以忽略了？？？？
