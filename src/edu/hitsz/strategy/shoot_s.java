package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class shoot_s implements Strategy{
    private int shootNum = 3;

    /**
     * 子弹伤害
     */

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int locationX = aircraft.getLocationX();
        int locationY = aircraft.getLocationY() -2;
        int speedX = 0;
        BaseBullet bullet;
        if(aircraft instanceof HeroAircraft){
            int speedY = aircraft.getSpeedY() -20;
            for(int i=0; i< 5; i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                bullet = new HeroBullet(locationX + (i*2 - 3 + 1)*10, locationY+20,(i*2 - 2), speedY, 30);
                res.add(bullet);}
        }
        else{
            int speedY = aircraft.getSpeedY() + 10;
            for(int i=0; i< shootNum; i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                bullet = new EnemyBullet(locationX + (i*2 - 3 + 1)*10, locationY+20,(i*2 - 2), speedY, aircraft.getPower());
                res.add(bullet);}
        }
        return res;
    }
}
