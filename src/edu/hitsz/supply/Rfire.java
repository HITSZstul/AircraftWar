package edu.hitsz.supply;

import videos.MusicThread;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.Strategy;
import edu.hitsz.strategy.shoot_f;
import edu.hitsz.strategy.shoot_r;

public class Rfire extends supply{
    public Rfire(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    @Override
    public void activate(HeroAircraft hero){
        Strategy strategy = new shoot_r();
        hero.changeStrategy(strategy);
        System.out.println("RFire!");
        Runnable r = () -> {
            try {
                int temp = hero.getStrategychange();
                hero.setStrategy(1+hero.getStrategychange());
                Thread.sleep(5000);
                if(hero.getStrategychange()==temp+1) {
                    final Strategy strategy1 = new shoot_f();
                    hero.changeStrategy(strategy1);
                    System.out.println("no rFire!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        // 启动线程
        new Thread(r, "线程 1").start();

    }

}
