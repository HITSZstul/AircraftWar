package edu.hitsz.factory;

import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.Strategy;
import edu.hitsz.strategy.shoot_n;

public class Mob_f implements enemy_f{
    @Override
    public MobEnemy createEnemy(boolean music,double rage_of_upgrade) {
        Strategy strategy = new shoot_n();
        return new MobEnemy((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                (int)(3*rage_of_upgrade),
                (int)(30*rage_of_upgrade),
                strategy);
    }
}
