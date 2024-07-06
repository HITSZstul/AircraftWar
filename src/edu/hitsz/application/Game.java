package edu.hitsz.application;

import edu.hitsz.basic.bomb_publisher;
import edu.hitsz.supply.bomb;
import videos.MusicThread;
import edu.hitsz.DAO.Player;
import edu.hitsz.DAO.PlayerDAO;
import edu.hitsz.DAO.PlayerDaoImpl;
import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.card.load_user;
import edu.hitsz.factory.*;
import edu.hitsz.supply.supply;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class Game extends JPanel {
    protected bomb_publisher Bomb_publisher = bomb_publisher.getBomb_publisher();
    protected int backGroundTop = 0;
    /**
     * Scheduled 线程池，用于任务调度
     */
    protected final ScheduledExecutorService executorService;
    /**
     * 时间间隔(ms)，控制刷新频率
     */
    protected int timeInterval = 40;
    protected final HeroAircraft heroAircraft;
    protected final List<MobEnemy> enemyAircrafts;
    protected final List<BaseBullet> heroBullets;
    protected final List<BaseBullet> enemyBullets;
    protected final List<supply> supplys;
    /**
     * 屏幕中出现的敌机最大数量
     */
    protected int enemyMaxNumber = 5;
    /**
     * 当前得分
     */
    protected static int score = 0;
    protected static int cutscore=0;

    /**
     * 当前时刻
     */
    protected int time = 0;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    protected int cycleDuration = 600;
    protected int cycleTime = 0;
    protected int cycleTime_e = 0;
    protected int cycleDuration_e = 600;
    protected int upgrate_enemy = 0;
    protected int time_limit = 500;
    protected int more_enemy = 10000;
    protected int Boss_limit = 1000;
    protected int level = 0;
    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;
    public boolean getOverFlag(){
        return gameOverFlag;
    }
    protected boolean music;
    public Game(boolean music) {
        heroAircraft = HeroAircraft.getHero();
        heroAircraft.setStrategy();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        supplys = new LinkedList<>();
        this.music = music;
        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    protected createEnemy Factory;
    public void action() {
        Factory = createEnemy.getFactory(music);
        Factory.setRage_upgrade(1);
        Factory.setRage_of_elite(0.9);
        Factory.setRage_of_mob(0.7);
        Factory.setBoss_limit(Boss_limit);
        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {
            time += timeInterval;
            upgrate_enemy +=timeInterval;
            more_enemy +=timeInterval;
            // 周期性执行（控制频率）
            time_update();
//          随时间增加难度
            bombAction();
            // 子弹移动
            bulletsMoveAction();
            // 飞机移动
            aircraftsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                new MusicThread("src/videos/game_over.wav",false);
                load_user load = new load_user(score,level);
                Main.cardPanel.add(load.getMainPanel());
                Main.cardLayout.last(Main.cardPanel);
//                music_bgm.stopping(true);
                //跳转到输入username窗口
                // 游戏结束
                music_bgm.stopping(true);
                executorService.shutdown();
                gameOverFlag = true;
                System.out.println("Game Over!");
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    protected void time_update() {

    }

    protected boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    protected boolean timeCountAndNewCycleJudge_enemy() {
        cycleTime_e += timeInterval;
        if (cycleTime_e >= cycleDuration_e && cycleTime_e - timeInterval < cycleTime_e) {
            // 跨越到新的周期
            cycleTime_e %= cycleDuration_e;
            return true;
        } else {
            return false;
        }
    }

    protected void enmey_shootAction() {
        // TODO 敌机射击
        for(MobEnemy Enemy2:enemyAircrafts){
            enemyBullets.addAll(Enemy2.shoot());
        }

    }

    protected void hero_shootAction(){
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    protected void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    protected void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
        for(supply Supply:supplys){
            Supply.forward();
        }
    }

    protected void bombAction(){
        for(MobEnemy enemyAircraft:enemyAircrafts){
            if(enemyAircraft.get_bomb_crash()){
                score+=enemyAircraft.getScore();
                cutscore+=enemyAircraft.getScore();
                enemyAircraft.vanish();
            }
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    protected void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for(BaseBullet bullets: enemyBullets){
            if(bullets.notValid()){
                continue;
            } else if (heroAircraft.crash(bullets)) {
                heroAircraft.decreaseHp(bullets.getPower());
                if(music){new MusicThread("src/videos/bullet.wav",false).start();}
                bullets.vanish();
            }
        }


        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (MobEnemy enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
//                     敌机撞击到英雄机子弹
//                     敌机损失一定生命值
                    if(music)
                    {
                        new MusicThread("src/videos/bullet_hit.wav",false).start();
                    }
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        score+=enemyAircraft.getScore();
                        cutscore+=enemyAircraft.getScore();
                        List<supply> a = enemyAircraft.fall();
                        if(a!=null) {
                            supplys.addAll(a);
                        }                        //elite crash
                        // TODO 获得分数，产生道具补给
                    }
                }
                    // 英雄机 与 敌机 相撞，均损毁
                    if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                        enemyAircraft.vanish();
                        heroAircraft.decreaseHp(Integer.MAX_VALUE);
                    }

            }
        }

        // Todo: 我方获得道具，道具生效
        for(supply Supply:supplys){
            if(heroAircraft.crash(Supply)) {
                if(music){
                    if(Supply instanceof bomb){
                        new MusicThread("src/videos/bomb_explosion.wav",false).start();
                    }
                    else{
                        new MusicThread("src/videos/get_supply.wav",false).start();
                    }
                }
                if(Supply instanceof bomb){
                    for(MobEnemy enemyAircraft:enemyAircrafts){
                        Bomb_publisher.addmonitor(enemyAircraft);
                    }
                    for(BaseBullet enemybullet:enemyBullets){
                        Bomb_publisher.addmonitor(enemybullet);
                    }
                }
                Supply.activate(HeroAircraft.getHero());
                Supply.vanish();
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    protected void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        supplys.removeIf(AbstractFlyingObject::notValid);
    }
    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    protected void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }
        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }
    void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }
    private final MusicThread music_bgm = new MusicThread("src/videos/bgm.wav",true);
    public void Music(){
        music_bgm.start();
    }

}
