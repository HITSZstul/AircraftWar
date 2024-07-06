package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.Strategy;
import edu.hitsz.strategy.shoot_f;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    private int strategychange = 0;

    public int getStrategychange(){
        return strategychange;
    }
    public void setStrategy(int strategychange) {
        this.strategychange = strategychange;
    }
    /**
     * 子弹伤害
     */

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private Strategy strategy;

    public void setStrategy() {
        this.strategy = new shoot_f();
    }

    public void changeStrategy(Strategy strategy)
    {
        this.strategy = strategy;
    }
    public Strategy getStrategy(){
        return strategy;
    }

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private volatile static HeroAircraft Hero;
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }
    public static HeroAircraft getHero(){
        if(Hero == null){
            synchronized (HeroAircraft.class){
                if(Hero == null){
                    Strategy strategy = new shoot_f();
                    Hero = new HeroAircraft(Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, 1000
                    );
                }
            }
        }
        return Hero;
    }
    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        res.addAll(strategy.shoot(this));
        return res;
    }
    @Override
    public void update_bomb() {

    }
}
