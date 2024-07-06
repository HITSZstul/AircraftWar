package edu.hitsz.DAO;

import java.io.Serializable;
import java.util.Date;

//一个玩家数据应该放置有分数，排名，游戏时间
public class Player implements Serializable {
    private int rank;
    private int score;
    private Date date;
    private String username;
    public Player(int rank, int score, String username,Date date ){
        this.rank = rank;
        this.score = score;
        this.username = username;
        this.date = date ;
    }
    public void setRank(int rank){
        this.rank = rank;
    }
    public int getRank(){
        return this.rank;
    }
    public int getScore(){
        return this.score;
    }
    public String getUsername(){
        return this.username;
    }
    public Date getDate(){
        return this.date;
    }

}
