package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.Strategy;
import edu.hitsz.supply.supply;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractAircraft {

    protected int score = 20;

    protected boolean bomb_crash=false;
    @Override
    public void update_bomb() {
        bomb_crash = true;
    }
    public boolean get_bomb_crash() {
        return bomb_crash;
    }

    public int getScore(){
        return score;
    }
    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp, Strategy strategy) {
        super(locationX, locationY, speedX, speedY, hp);
        this.strategy = strategy;
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
    public List<BaseBullet> shoot() {
        return strategy.shoot(this);
    }
    public List<supply> fall() {
        return new LinkedList<>();
    }
}
