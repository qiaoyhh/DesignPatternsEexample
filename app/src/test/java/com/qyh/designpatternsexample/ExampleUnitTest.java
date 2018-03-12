package com.qyh.designpatternsexample;

import com.qyh.designpatternsexample.abstrafactory.abstract_product.Human;
import com.qyh.designpatternsexample.abstrafactory.concrete_factory.FemaleHumanFactory;
import com.qyh.designpatternsexample.abstrafactory.concrete_factory.MaleHumanFactory;
import com.qyh.designpatternsexample.decorator.concretedecorator.Pg;
import com.qyh.designpatternsexample.decorator.concretedecorator.SF;
import com.qyh.designpatternsexample.decorator.concretedecorator.SG;
import com.qyh.designpatternsexample.decorator.example.packet.IversonLayupPacket;
import com.qyh.designpatternsexample.decorator.example.packet.JordanDunkPacket;
import com.qyh.designpatternsexample.decorator.example.packet.KobeShootPacket;
import com.qyh.designpatternsexample.decorator.example.skinplayer.BlackPlayer;
import com.qyh.designpatternsexample.decorator.example.skinplayer.WhitePlayer;
import com.qyh.designpatternsexample.decorator.example.skinplayer.YellowPlayer;
import com.qyh.designpatternsexample.factory.concrete_product.BlackHuman;
import com.qyh.designpatternsexample.factory.concrete_product.WhiteHuman;
import com.qyh.designpatternsexample.factory.contrete_factory.HumanFactory;
import com.qyh.designpatternsexample.iterator.example.CEO;
import com.qyh.designpatternsexample.iterator.example.CTO;
import com.qyh.designpatternsexample.iterator.example.Employee;
import com.qyh.designpatternsexample.iterator.example.GroupLeader;
import com.qyh.designpatternsexample.iterator.example.IEmployee;
import com.qyh.designpatternsexample.mediator.Boss;
import com.qyh.designpatternsexample.mediator.HeadhuntingMediator;
import com.qyh.designpatternsexample.mediator.Jobhunter;
import com.qyh.designpatternsexample.observer.ConcreteObserver;
import com.qyh.designpatternsexample.observer.ConcreteSubject;
import com.qyh.designpatternsexample.observer.IObserver;
import com.qyh.designpatternsexample.observer.Subject;
import com.qyh.designpatternsexample.order.Receiver;
import com.qyh.designpatternsexample.order.command.QQCommand;
import com.qyh.designpatternsexample.order.command.SinaCommand;
import com.qyh.designpatternsexample.order.command.WeChatCommand;
import com.qyh.designpatternsexample.order.invoker.Invoker;
import com.qyh.designpatternsexample.proxy.dynamic_proxy.GamePlayIH;
import com.qyh.designpatternsexample.proxy.static_proxy.GamePlayProxy;
import com.qyh.designpatternsexample.proxy.static_proxy.GamePlayer;
import com.qyh.designpatternsexample.proxy.static_proxy.IGamePlayer;
import com.qyh.designpatternsexample.strategy.CommonStrategy;
import com.qyh.designpatternsexample.strategy.DiscountNum;
import com.qyh.designpatternsexample.strategy.GoldStrategy;
import com.qyh.designpatternsexample.strategy.IDiscountStrategy;
import com.qyh.designpatternsexample.strategy.JewelStrategy;
import com.qyh.designpatternsexample.template.concrete_class.PlantMelonTemplate;
import com.qyh.designpatternsexample.template.concrete_class.PlantPeasTemplate;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    //工厂模式测试
    @Test
    public void testFactory() {
        //步骤5：外界通过调用具体工厂类的方法，从而创建不同具体产品类的实例
        HumanFactory humanFactory = new HumanFactory();
        BlackHuman blackHuman = humanFactory.createHuman(BlackHuman.class);
        blackHuman.getColor();
        blackHuman.talk();

        WhiteHuman whiteHuman = humanFactory.createHuman(WhiteHuman.class);
        whiteHuman.getColor();
        whiteHuman.talk();
    }

    // 抽象工厂测试
    @Test
    public void testAbstractFactory() {
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

        System.out.println("执行完成");
    }

    // 模板方法模式测试
    @Test
    public void testTemplate() {
        PlantMelonTemplate melonTemplate = new PlantMelonTemplate();
        melonTemplate.isWatering(true);
        melonTemplate.templateMothod();

        PlantPeasTemplate peasTemplate = new PlantPeasTemplate();
        peasTemplate.isWatering(false);
        peasTemplate.templateMothod();
    }

    // 静态代理测试
    @Test
    public void testStaticProxy() {
        //玩家自己操作
        GamePlayer gamePlayer = new GamePlayer("jordan");
        gamePlayer.login("jordan", "jjj");
        gamePlayer.killBoss();
        gamePlayer.upgrade();

        // 找个代练
        GamePlayProxy gamePlayProxy = new GamePlayProxy(gamePlayer);
        gamePlayProxy.login("jordan", "jjj");
        gamePlayProxy.killBoss();
        gamePlayProxy.upgrade();
    }

    // 动态代理测试
    @Test
    public void testDynamicProxy() {
        IGamePlayer gamePlayer = new GamePlayer("jordan");
        InvocationHandler handler = new GamePlayIH(gamePlayer);
        ClassLoader classLoader = gamePlayer.getClass().getClassLoader();
        IGamePlayer proxy = (IGamePlayer) Proxy.newProxyInstance(classLoader, new Class[]{IGamePlayer.class}, handler);
        proxy.login("jordan", "jjj");
        proxy.killBoss();
        proxy.upgrade();
    }

    // 中介者模式测试
    @Test
    public void testMediator() {
        // 猎头 老板 求职者
        HeadhuntingMediator mediator = new HeadhuntingMediator();
        Boss boss = new Boss("Jack Ma", mediator);
        Jobhunter jobhunter = new Jobhunter("Pony", mediator);

        mediator.setBoss(boss);
        mediator.setJobhunter(jobhunter);

        jobhunter.contact("Boss您好，我有三年阿里，两年腾讯，四年华为，五年百度的 保洁工作经验");
        boss.contact("吆西，我们就需要的这样的人才，明天来上班！");
    }

    // 命令模式测试
    @Test
    public void testOrder() {
        // 创建接收者
        Receiver receiver = new Receiver();
        //创建具体的命令对象，设定它的接收者
        QQCommand qqCommand = new QQCommand(receiver);
        SinaCommand sinaCommand = new SinaCommand(receiver);
        WeChatCommand weChatCommand = new WeChatCommand(receiver);

        //创建请求者，把命令对象设置进去
        Invoker invoker = new Invoker();
        invoker.addCommand(qqCommand);
        invoker.addCommand(sinaCommand);
        invoker.addCommand(weChatCommand);

        // 执行方法
        invoker.allAction();
        invoker.removeCommand(weChatCommand);
    }

    // 责任链模式测试
    @Test
    public void testIterator() {
        // 创建所有领导对象
        GroupLeader groupLeader = new GroupLeader();
        CTO cto = new CTO();
        CEO ceo = new CEO();

        //随机生成几个请假员工
        Random random = new Random();
        ArrayList<IEmployee> employeeArrayList = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            employeeArrayList.add(new Employee(random.nextInt(10),"回家相亲"));
        }

        // 设置下一位处理对象
        groupLeader.setNextHandler(cto);
        cto.setNextHandler(ceo);
        for(IEmployee employee:employeeArrayList) {
            // 发起请求
            groupLeader.handlerRequest(employee);
        }
    }

    // 装饰模式测试
    @Test
    public void testDecorator(){
        // 这样我们就可以随意组装不同位置不同皮肤的球员了
//        BlackPlayer blackPlayer = new BlackPlayer(new Pg());
//        WhitePlayer whitePlayer = new WhitePlayer(new SG());
//        YellowPlayer yellowPlayer = new YellowPlayer(new SF());
//        blackPlayer.seat();
//        whitePlayer.seat();
//        yellowPlayer.seat();

        BlackPlayer blackPlayer = new BlackPlayer(new KobeShootPacket(new SG()));
        YellowPlayer yellowPlayer = new YellowPlayer(new JordanDunkPacket(new SF()));
        WhitePlayer whitePlayer = new WhitePlayer(new IversonLayupPacket(new Pg()));

        blackPlayer.seat();
        yellowPlayer.seat();
        whitePlayer.seat();

    }

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

    // 观察者模式测试
    @Test
    public void testObserver(){
        // 创建被观察者
        ConcreteSubject concreteSubject = new ConcreteSubject();
        // 定义一个观察者
        IObserver observer1 = new ConcreteObserver();
        // 把观察者注入到被观察者中
        concreteSubject.addObserver(observer1);
        // 观察者发生改变
        concreteSubject.changeState();
    }
}