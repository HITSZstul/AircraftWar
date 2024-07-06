package edu.hitsz.application;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.enemy_f;
import edu.hitsz.factory.supply_f;
import edu.hitsz.supply.supply;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Game_easy extends Game{
    public Game_easy(boolean music){
        super(music);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE1, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE1, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }
        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g,supplys);
        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);
    }
    @Override
    public void time_update(){

        if (timeCountAndNewCycleJudge()) {
            // 新敌机产生
            if(enemyAircrafts.size()<enemyMaxNumber&& !Factory.isBoss_vail()) {
                enemyAircrafts.add(Factory.EnemyCreate(0));
                // 飞机射出子弹
            }
            enmey_shootAction();
            hero_shootAction();
        }
    }
}
