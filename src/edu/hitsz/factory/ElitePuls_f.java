package edu.hitsz.factory;

import edu.hitsz.aircraft.ElitePlus;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.Strategy;
import edu.hitsz.strategy.shoot_s;

public class ElitePuls_f implements enemy_f{
    @Override
    public MobEnemy createEnemy(boolean music,double rage_of_upgrade) {
        Strategy strategy = new shoot_s();
        return new ElitePlus(0,
                (int) ( Main.WINDOW_HEIGHT * 0.05)+10,
                (int)(5*rage_of_upgrade),
                0,
                (int)(90*rage_of_upgrade),
                strategy);
    }
}
