package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.*;
import edu.hitsz.strategy.Strategy;
import edu.hitsz.supply.supply;

import java.util.LinkedList;
import java.util.List;


public class Elite extends MobEnemy{
    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    public Elite(int locationX, int locationY, int speedX, int speedY, int hp, Strategy strategy) {
        super(locationX, locationY, speedX, speedY, hp, strategy);
        score = 30;
        power = 20;
    }
    @Override
    public void update_bomb() {
        System.out.println(bomb_crash);
        bomb_crash = true;
    }
    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
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
    public List<supply> fall() {
        List<supply> res = new LinkedList<>();
        double x = Math.random();
        supply_f Supply_f = null;
        if(x<0.1)
        {
            return res;
        } else if (x<0.4) {
            Supply_f = new Add_f();
            res.add(Supply_f.createSupply(locationX,locationY,speedX,5));
        }
        else if (x<0.6) {
            Supply_f = new Rfire_f();
            res.add(Supply_f.createSupply(locationX, locationY, speedX, 5));
        }
        else if (x<0.9) {
            Supply_f = new Fire_f();
            res.add(Supply_f.createSupply(locationX,locationY,speedX,speedY));
        } else  {
            Supply_f = new bomb_f();
            res.add(Supply_f.createSupply(locationX,locationY,speedX,speedY));
        }
        return res;
    }
}

