package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class shoot_f implements Strategy{

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int locationX = aircraft.getLocationX();
        int locationY = aircraft.getLocationY() ;
        int speedX = 0;
        BaseBullet bullet;
        if(aircraft instanceof HeroAircraft){
            int speedY = aircraft.getSpeedY()-20;
            for(int i=0; i<1; i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                bullet = new HeroBullet(locationX , locationY+20, speedX, speedY, 30);
                res.add(bullet);}
        }
        else{
            int speedY = aircraft.getSpeedY()+10;
            for(int i=0; i<3; i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                bullet = new EnemyBullet(locationX , locationY+20, speedX, speedY, aircraft.getPower());
                res.add(bullet);}
        }
        return res;
    }
}
