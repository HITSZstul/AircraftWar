package edu.hitsz.application;

import java.awt.*;

public class Game_hard extends Game{
    public Game_hard(boolean music){
        super(music);
        upgrade_enemy = 400;
        Boss_limit = 700;
        level = 2;
    }
    @Override

    public void paint(Graphics g) {
        super.paint(g);
        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE3, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE3, 0, this.backGroundTop, null);
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
    public void time_update(){
        Factory.setBoss_limit(Boss_limit);
        if (timeCountAndNewCycleJudge_enemy()) {
            if(upgrade_enemy >=time_limit){
                cycleDuration_e/= (int) (1+(double)time/500000);
                System.out.println("敌机射击频率增加！敌机属性提升倍率："+(1+(double)time/100000)+"    当前敌机最大数量:"+(int)(enemyMaxNumber)+"    精英机产生概率:"+(0.2+(double)time/1000000));
                upgrade_enemy = 0;
                Factory.setRage_upgrade(1+(double)time/100000);
                Factory.setRage_of_mob(0.7-(double)time/1000000);
                Factory.setRage_of_elite(0.9-(double)time/500000);
            }
            if(more_enemy>20000){
                enemyMaxNumber+=1;
                more_enemy = 0;
            }
            // 新敌机产生
            if(enemyAircrafts.size()<enemyMaxNumber && !Factory.isBoss_vail()) {
//                    music_boss.stop(true);
                enemyAircrafts.add(Factory.EnemyCreate(cutscore));
                if(cutscore>=Boss_limit){
                    cutscore-=Boss_limit;
                }
            }
            // 飞机射出子弹
            enmey_shootAction();
        }
        if(timeCountAndNewCycleJudge()){
            hero_shootAction();
        }
    }

}
