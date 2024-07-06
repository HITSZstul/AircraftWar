package edu.hitsz.supply;

import edu.hitsz.basic.bomb_publisher;
import videos.MusicThread;
import edu.hitsz.aircraft.HeroAircraft;

public class bomb extends supply{
    public bomb(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    @Override
    public void activate(HeroAircraft hero){
        bomb_publisher Bomb_publisher = bomb_publisher.getBomb_publisher();
        Bomb_publisher.NotifyAll();
        Bomb_publisher.removeMonitor();
        System.out.println("bomb");
    }
}
