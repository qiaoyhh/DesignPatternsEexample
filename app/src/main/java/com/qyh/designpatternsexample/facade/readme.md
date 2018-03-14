## 外观模式
### 一、定义
外观模式也称为门面模式，其定义为：要求子系统对外部通信必须通过一个统一的对象，也就是提供一个访问子系统的接口
，除了这个接口不允许有任何访问子系统的行为发生。
### 二、UML类图及解析
![](https://github.com/qiaoyhh/DesignPatternsEexample/blob/master/app/src/main/res/mipmap-xhdpi/facade_uml.png)

- Facade: 系统对外的统一接口，系统内部系统地工作。此角色知晓子系统所有功能和职责，但是此类没有实际的业务逻辑，只是委托类。
- class 1,2,3: 子系统，可以有多个，子类系统不知道门面的存在。

**子系统角色中的类**
```java
public class ClassA {
    public void testA(){
       
    }
}
public class ClassB {
    public void testB(){
       
    }
}
public class ClassC {
    public void testC(){
       
    }
}
```
**外观角色类**
```java
public class Facade {
    //示意方法，满足客户端需要的功能
    public void test(){
        ClassA a = new ClassA();
        a.testA();
        ClassB b = new ClassB();
        b.testB();
        ClassC c = new ClassC();
        c.testC();
    }
}
```
**客户端类**
```java
public void test(){
    Facade facade = new Facade();
    facade.test();
}
```
Facade类其实相当于A、B、C模块的外观界面，有了这个Facade类，那么客户端就不需要亲自调用子系统中的A、B、C模块了，也不需要
知道系统内部的实现细节，甚至都不需要知道A、B、C模块的存在，客户端只需要跟Facade类交互就好了，从而更好地实现了客户端和子
系统中A、B、C模块的解耦，让客户端更容易地使用系统。
模板用法还是比较简单的，也挺小清新的，我们实际开发中能到哪里呢？我们项目都会操作数据的类，比如Db,Sp,Http等，如果我们不用
外观设计模式大概是这样的：
![](https://github.com/qiaoyhh/DesignPatternsEexample/blob/master/app/src/main/res/mipmap-xhdpi/noface_ex.png)

当我们调用他们每个类中的方法时候，需要先拿到这个类的对象，外界访问直接深入到子系统内部，相互之间是一种强耦合的关系，如果某个系统
改变了，所以跟这个类有关系的调用者都需要作出相应的改变这样的系统设计是不能接受的，外观设计模式就很好的解决了这个问题，如图：

![](https://github.com/qiaoyhh/DesignPatternsEexample/blob/master/app/src/main/res/mipmap-xhdpi/face_ex.png)

有了DataManager这个外观者，我们不需要知道Db,Sp,Http模块的实例了，减少了类与类之间的耦合关系,灵活性也比较高了。代码比较简单，跟
模板代码比较类似就不贴了。
### Android源码中实现
首先给大家推荐一个查看源码的网站，因为framework层的代码咱们在开发工具看不了，打开这个网站直接搜索类名就OK了。-
- [源码查看](http://androidxref.com/)

在Android开发中，Context是经常使用的一个类，它封装了很多重要的操作，如：startActivity()、sendBroadcast()、bindService()等，
几乎是开发者对应用操作的统一入口。Context是一个抽象类，它只是定义了抽象接口，真正的实现在ContextImpl类中。它就是今天我们要分析的外观类。

在应用启动时，首先会fork一个子进程，并且调用ActivityThread.main方法启动该进程。ActivityThread又会构建Application对象，
然后和Activity、ContextImpl关联起来，然后再调用Activity的onCreate、onStart、onResume函数使Activity运行起来。
我们看看下面的相关代码:
```java
private void handleLaunchActivity(ActivityClientRecord r, Intent customIntent, String reason) {
        // 1.创建Activity，并调用onCreate()
        Activity a = performLaunchActivity(r, customIntent);

        if (a != null) {
          r.createdConfig = new Configuration(mConfiguration);
           reportSizeConfigurations(r);
           Bundle oldState = r.state;
           // 2.调用Activity的onResume方法，使Activity变得可见。
           handleResumeActivity(r.token, false, r.isForward,
                  !r.activity.mFinished && !r.startsNotResumed, r.lastProcessedSeq, reason);
          }
}
```
```java
 private Activity performLaunchActivity(ActivityClientRecord r, Intent customIntent) {
        // 代码省略
        Activity activity = null;
        try {
            java.lang.ClassLoader cl = r.packageInfo.getClassLoader();
            // 1.创建Activity
           activity = mInstrumentation.newActivity(
                    cl, component.getClassName(), r.intent);
            StrictMode.incrementExpectedActivityCount(activity.getClass());
            r.intent.setExtrasClassLoader(cl);
            r.intent.prepareToEnterProcess();
            if (r.state != null) {
                r.state.setClassLoader(cl);
           }
        } catch (Exception e) {
          
        }
        try {
            // 2.创建 Application
            Application app = r.packageInfo.makeApplication(false, mInstrumentation);
             if (activity != null) {
               // 3.创建ContextImpl,并通过ContextImpl获得Activity
               Context appContext = createBaseContextForActivity(r, activity);
               CharSequence title = r.activityInfo.loadLabel(appContext.getPackageManager());
               Configuration config = new Configuration(mCompatConfiguration);
              // 4.Activity与Context和appcation关联起来
              activity.attach(appContext, this, getInstrumentation(), r.token,
                      r.ident, app, r.intent, r.activityInfo, title, r.parent,
                     r.embeddedID, r.lastNonConfigurationInstances, config,
                      r.referrer, r.voiceInteractor, window);

                activity.mStartedActivity = false;
                // 5.获得Activity的主题并设置
                int theme = r.activityInfo.getThemeResource();
                if (theme != 0) {
                    activity.setTheme(theme);
                }
                // 6.回调Activity onCreate()
             if (r.isPersistable()) {
               mInstrumentation.callActivityOnCreate(activity, r.state, r.persistentState);
            } else {
               mInstrumentation.callActivityOnCreate(activity, r.state);
            }
            mActivities.put(r.token, r);

        } catch (SuperNotCalledException e) {
            throw e;

      } catch (Exception e) {
       }
       return activity;
    }
}
```
在handleLaunchActivity函数中调用performLaunchActivity函数来完成Application、ContextImpl和Activity的创建工作，
并且调用Activity的attach()将这3者关联起来。

```java
// Activity源码中 attach()
final void attach(Context context, ActivityThread aThread,
            Instrumentation instr, IBinder token, int ident,
            Application application, Intent intent, ActivityInfo info,
            CharSequence title, Activity parent, String id,
            NonConfigurationInstances lastNonConfigurationInstances,
            Configuration config, String referrer, IVoiceInteractor voiceInteractor,
            Window window) {
            
          /**调用父类 ContextThemeWrapper 的attachBaseContext()完成关联。
           * 这个时候Activity就持有了ContextImpl的引用
           */
        attachBaseContext(context); 
    }
    
    public class ContextThemeWrapper extends ContextWrapper {
     @Override
        protected void attachBaseContext(Context newBase) {
            super.attachBaseContext(newBase);
        }
    }
```
Activity本身是Context的子类，因此，Activity具有了Context定义的所有方法，但是Activity并不实现具体的功能,它只是继承了Context
的接口，并且将相关的操作转发给了ContextImpl对象，这个ContextImpl存储在Activity的上层父类ContextWrapper中，变量名为mBase。
```java
public class ContextWrapper extends Context {
    Context mBase;

    public ContextWrapper(Context base) {
        mBase = base;
    }
}
```
总结：Activity启动之后，Android给我们提供了操作系统服务的统一入口，也就是Activity本身。这些工作并不是Activity自己实现的，
而是将操作委托给Activity父类ContextThemeWrapper的mBase对象，这个对象的实现类就是ContextImpl ( 也就是performLaunchActivity
方法中构建的ContextImpl )。
我们打开刚才那个网站，搜索ContextImpl的源码，重要代码如下：
```java
class ContextImpl extends Context {
	// 代码省略
        @Override
    public void sendBroadcast(Intent intent) {
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            ActivityManagerNative.getDefault().broadcastIntent(
                mMainThread.getApplicationThread(), intent, resolvedType, null,
                Activity.RESULT_OK, null, null, null, false, false);
        } catch (RemoteException e) {
        }
    }

        @Override
    public void startActivity(Intent intent) {
        if ((intent.getFlags()&Intent.FLAG_ACTIVITY_NEW_TASK) == 0) {
            throw new AndroidRuntimeException(
                    "Calling startActivity() from outside of an Activity "
                    + " context requires the FLAG_ACTIVITY_NEW_TASK flag."
                    + " Is this really what you want?");
        }
        mMainThread.getInstrumentation().execStartActivity(
            getOuterContext(), mMainThread.getApplicationThread(), null, null, intent, -1);
    }
    
        @Override
    public ComponentName startService(Intent service) {
        try {
            ComponentName cn = ActivityManagerNative.getDefault().startService(
                mMainThread.getApplicationThread(), service,
                service.resolveTypeIfNeeded(getContentResolver()));
            if (cn != null && cn.getPackageName().equals("!")) {
                throw new SecurityException(
                        "Not allowed to start service " + service
                        + " without permission " + cn.getClassName());
            }
            return cn;
        } catch (RemoteException e) {
            return null;
        }
    }
    
        @Override
    public String getPackageName() {
        if (mPackageInfo != null) {
            return mPackageInfo.getPackageName();
        }
        throw new RuntimeException("Not supported in system context");
    }
}
```
ContextImpl内部有很多不用子系统的操作，比如我们要启动一个Activity的时候，我们调用的是startActivity方法，这个功能的内部
实现实际上是Instrumentation完成的。ContextImpl封装了这个功能，使得用户根本不需要知晓Instrumentation相关的信息，
直接使用startActivity即可完成相应的工作。其他的子系统功能也是类似的实现，比如启动Service和发送广播内部使用的是
ActivityManagerNative等。
总结：外观模式封装了子系统的操作，并且暴露接口让用户使用，避免了用户需要与多个子系统进行交互，降低了系统的耦合度、复杂度。
如果没有外观模式的封装，那么用户就必须知道各个子系统的相关细节，子系统之间的交互必然造成纠缠不清的关系，影响系统的稳定性。

### 四、优缺点
#### 优点：
- 减少系统的相互依赖，降低客户端和子系统的耦合；
- 提高安全性，客户端想访问子系统的方法只能通过门面类；
#### 缺点：
外观类没有遵循开闭原则，增加新的子系统可能需要修改外观类或客户端的源代码；