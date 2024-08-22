package edu.hitsz.aircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.factory.*;
import edu.hitsz.strategy.Strategy;
import edu.hitsz.strategy.shoot_r;
import edu.hitsz.supply.supply;
import videos.MusicThread;

import java.util.LinkedList;
import java.util.List;

public class Boss extends MobEnemy {

    private boolean music;
    private MusicThread music_boss;
    public Boss(int locationX, int locationY, int speedX, int speedY, int hp, Strategy strategy,boolean music) {
        super(locationX, locationY, speedX, speedY, hp, strategy);
        power = 30;
        score = 100;
        this.music = music;
        if(music){
            music_boss =  new MusicThread("src/videos/bgm_boss.wav",true);
            music_boss.stopping(true);
            Runnable task = ()->{
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };
            System.out.println(music_boss.isStop());
            music_boss.stopping(false);
            music_boss.start();
        }
    }
    private void Music(){
        music_boss.start();
    }
    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
        if(hp >= maxHp){
            hp = maxHp;
        }
    }
    private Strategy strategy = new shoot_r();
    @Override
    public void forward()
    {
        super.forward();
        if(this.locationY>=100)
        {
            speedY=0;
        }
    }
    public void update_bomb() {
    }
    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        res.addAll(strategy.shoot(this));//将引用传递给strategy，进行子弹发射
        return res;
    }
    public List<supply> fall() {
        List<supply> res = new LinkedList<>();
        createEnemy Factory = createEnemy.getFactory(music);
        Factory.Boss_change(false);
        supply_f Supply_f = null;
        for(int i=0;i<3;i++) {
            double x = Math.random();
            if(x<0.1)
            {
                Supply_f = new Add_f();
                res.add(Supply_f.createSupply(locationX+(i-1)*5, locationY+(i-1)*5, speedX*(i-1), 5));
            }
            else if (x<0.5) {
                Supply_f = new Rfire_f();
                res.add(Supply_f.createSupply(locationX+(i-1)*5, locationY+(i-1)*5, speedX*(i-1), 5));
            }
            else if (x < 0.7) {
                Supply_f = new Fire_f();
                res.add(Supply_f.createSupply(locationX+(i-1)*5, locationY+(i-1)*5, speedX*(i-1), 5));
            } else {
                Supply_f = new bomb_f();
                res.add(Supply_f.createSupply(locationX+(i-1)*5, locationY+(i-1)*5, speedX*(i-1), 5));
            }
        }
        if(music){
            music_boss.stopping(true);
            Runnable task=()->{
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };
            music_boss.stopping(false);
            new MusicThread("src/videos/bgm.wav",true).start();
            System.out.println(music_boss.isStop());
            music_boss.interrupt();
        }
        return res;
    }
}
