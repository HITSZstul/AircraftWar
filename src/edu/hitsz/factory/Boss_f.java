package edu.hitsz.factory;

import edu.hitsz.aircraft.Boss;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.Strategy;
import edu.hitsz.strategy.shoot_r;

public class Boss_f implements enemy_f{
    @Override
    public MobEnemy createEnemy(boolean music,double rage_of_upgrade) {
        Strategy strategy = new shoot_r();
        System.out.println(5*rage_of_upgrade);
        return new Boss((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                (int)(5*rage_of_upgrade),
                (int)(1*rage_of_upgrade),
                (int)(500*rage_of_upgrade),
                strategy,music);
    }
}
