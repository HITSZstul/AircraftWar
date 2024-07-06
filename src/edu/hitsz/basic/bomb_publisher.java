package edu.hitsz.basic;

import java.util.ArrayList;
import java.util.List;


public class bomb_publisher {
    private List<bomb_monitor> Monitors = new ArrayList<>();
    private volatile static bomb_publisher Bomb_publisher;
    private void Bomb_monitor() {
    }
    public static bomb_publisher getBomb_publisher(){
        if(Bomb_publisher == null){
            synchronized (bomb_publisher.class){
                if(Bomb_publisher == null){
                    Bomb_publisher = new bomb_publisher();
                }
            }
        }
        return Bomb_publisher;
    }

    public void addmonitor(bomb_monitor monitor){
        Monitors.add(monitor);
    }
    public void removeMonitor(){
        Monitors.clear();
    }
    public void NotifyAll(){
        for(bomb_monitor monitor:Monitors){
            monitor.update_bomb();
        }
    }
}
