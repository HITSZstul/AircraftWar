package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.factory.*;
import edu.hitsz.strategy.Strategy;
import edu.hitsz.strategy.shoot_s;
import edu.hitsz.supply.supply;

import java.util.LinkedList;
import java.util.List;

public class ElitePlus extends MobEnemy{
    public ElitePlus(int locationX, int locationY, int speedX, int speedY, int hp, Strategy strategy) {
        super(locationX, locationY, speedX, speedY, hp, strategy);
        score = 50;
        power = 30;
    }
    @Override
    public void update_bomb() {
        if (hp <= 30) {
            bomb_crash = true;
        }
        else{
            hp = hp -30;
        }
    }
    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        res.addAll(strategy.shoot(this));
        return res;
    }
    @Override
    public List<supply> fall() {
        double x = Math.random();
        supply_f Supply_f = null;
        List<supply> res = new LinkedList<>();
        if(x<0.1)
        {
            return res;
        } else if (x<0.3) {
            Supply_f = new Add_f();
            res.add(Supply_f.createSupply(locationX,locationY,speedX,5));
        }
        else if (x<0.5) {
            Supply_f = new Rfire_f();
            res.add(Supply_f.createSupply(locationX, locationY, speedX, 5));
        }
        else if (x<0.7) {
            Supply_f = new Fire_f();
            res.add(Supply_f.createSupply(locationX,locationY,speedX,5));
        } else  {
            Supply_f = new bomb_f();
            res.add(Supply_f.createSupply(locationX,locationY,speedX,5));
        }
        return res;
    }
}
