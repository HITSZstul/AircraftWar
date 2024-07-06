package edu.hitsz.supply;

import videos.MusicThread;
import edu.hitsz.aircraft.HeroAircraft;

public class Add extends supply{
    public Add(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    @Override
    public void activate(HeroAircraft hero){
        hero.decreaseHp(-100);
    }
}
