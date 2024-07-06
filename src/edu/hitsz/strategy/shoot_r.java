package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class shoot_r implements Strategy{
    private int shootNum = 24;
    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        BaseBullet bullet;
        if(aircraft instanceof HeroAircraft){
        for(int i=0; i< shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = new HeroBullet((int)(aircraft.getLocationX()+Math.sin(0.261*i)*15) , (int)(aircraft.getLocationY()+Math.cos(0.261*i)*15),(int)(Math.sin(0.261*i)*5) ,(int)(Math.cos(0.261*i)*5) , 30);
            res.add(bullet);}
        }
        else{
            for(int i=0; i< shootNum; i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                bullet = new EnemyBullet((int)(aircraft.getLocationX()+Math.sin(0.261*i)*15) , (int)(aircraft.getLocationY()+Math.cos(0.261*i)*15),(int)(Math.sin(0.261*i)*5) ,(int)(Math.cos(0.261*i)*5) , aircraft.getPower());
                res.add(bullet);}
        }
        return res;
    }
}
