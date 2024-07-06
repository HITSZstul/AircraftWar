package edu.hitsz.factory;


import videos.MusicThread;
import edu.hitsz.aircraft.MobEnemy;

public class createEnemy {
    private volatile static createEnemy Factory;
    private static boolean music;
    private createEnemy(boolean music){
        createEnemy.music = music;
    };
    public static createEnemy getFactory(boolean music) {
        if (Factory == null) {
            synchronized (createEnemy.class) {
                if (Factory == null) {
                    Factory = new createEnemy(music);
                }
            }
        }
        return Factory;
    }
    private boolean boss_vail = false;
    public boolean isBoss_vail(){
        return boss_vail;
    }
    public void Boss_change(boolean x){
        boss_vail = x;
    }
    private int Boss_limit = 1000;
    private double rage_of_mob;
    private double rage_of_elite;
    private double rage_upgrade = 1;
    public void setBoss_limit(int boss_limit) {
        Boss_limit = boss_limit;
    }

    public void setRage_of_elite(double rage_of_elite) {
        this.rage_of_elite = rage_of_elite;
    }

    public void setRage_of_mob(double rage_of_mob) {
        this.rage_of_mob = rage_of_mob;
    }

    public void setRage_upgrade(double rage_upgrade) {
        this.rage_upgrade = rage_upgrade;
    }

    public MobEnemy EnemyCreate(int score){
        enemy_f Enemy_f = null;
        if(score>=Boss_limit)
        {
            Enemy_f = new Boss_f();
            boss_vail = true;
            return Enemy_f.createEnemy(music,rage_upgrade);
        }
        else {
            if (Math.random() <= rage_of_mob) {
                Enemy_f = new Mob_f();
                return Enemy_f.createEnemy(music,rage_upgrade);
            } else if (Math.random() <= rage_of_elite) {
                Enemy_f = new Elite_f();
                return Enemy_f.createEnemy(music,rage_upgrade);
            } else{
                Enemy_f = new ElitePuls_f();
                return Enemy_f.createEnemy(music,rage_upgrade);//create enemy
            }
        }
    }
}
