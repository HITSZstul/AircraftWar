package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;

public interface enemy_f {
    public abstract MobEnemy createEnemy(boolean music,double rage_of_upgrade);
}
