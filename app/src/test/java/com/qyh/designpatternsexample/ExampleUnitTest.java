package com.qyh.designpatternsexample;
import com.qyh.designpatternsexample.abstrafactory.abstract_product.Human;
import com.qyh.designpatternsexample.abstrafactory.concrete_factory.FemaleHumanFactory;
import com.qyh.designpatternsexample.abstrafactory.concrete_factory.MaleHumanFactory;
import com.qyh.designpatternsexample.factory.concrete_product.BlackHuman;
import com.qyh.designpatternsexample.factory.concrete_product.WhiteHuman;
import com.qyh.designpatternsexample.factory.contrete_factory.HumanFactory;
import com.qyh.designpatternsexample.proxy.dynamic_proxy.GamePlayIH;
import com.qyh.designpatternsexample.proxy.static_proxy.GamePlayProxy;
import com.qyh.designpatternsexample.proxy.static_proxy.GamePlayer;
import com.qyh.designpatternsexample.proxy.static_proxy.IGamePlayer;
import com.qyh.designpatternsexample.template.concrete_class.PlantMelonTemplate;
import com.qyh.designpatternsexample.template.concrete_class.PlantPeasTemplate;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

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
    public void testFactory(){
        //步骤5：外界通过调用具体工厂类的方法，从而创建不同具体产品类的实例
        HumanFactory humanFactory =new HumanFactory();
        BlackHuman blackHuman = humanFactory.createHuman(BlackHuman.class);
        blackHuman.getColor();
        blackHuman.talk();

        WhiteHuman whiteHuman = humanFactory.createHuman(WhiteHuman.class);
        whiteHuman.getColor();
        whiteHuman.talk();
    }

    // 抽象工厂测试
    @Test
    public void testAbstractFactory(){
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
    public void testTemplate(){
        PlantMelonTemplate melonTemplate = new PlantMelonTemplate();
        melonTemplate.isWatering(true);
        melonTemplate.templateMothod();

        PlantPeasTemplate peasTemplate = new PlantPeasTemplate();
        peasTemplate.isWatering(false);
        peasTemplate.templateMothod();
    }

    // 静态代理测试
    @Test
    public void testStaticProxy(){
        //玩家自己操作
        GamePlayer gamePlayer = new GamePlayer("jordan");
        gamePlayer.login("jordan","jjj");
        gamePlayer.killBoss();
        gamePlayer.upgrade();

        // 找个代练
        GamePlayProxy gamePlayProxy =new GamePlayProxy(gamePlayer);
        gamePlayProxy.login("jordan","jjj");
        gamePlayProxy.killBoss();
        gamePlayProxy.upgrade();
    }

    // 动态代理测试
    @Test
    public void testDynamicProxy(){
        IGamePlayer gamePlayer = new GamePlayer("jordan");
        InvocationHandler handler = new GamePlayIH(gamePlayer);
        ClassLoader classLoader = gamePlayer.getClass().getClassLoader();
        IGamePlayer proxy = (IGamePlayer) Proxy.newProxyInstance(classLoader, new Class[]{IGamePlayer.class}, handler);
        proxy.login("jordan","jjj");
        proxy.killBoss();
        proxy.upgrade();
    }
}