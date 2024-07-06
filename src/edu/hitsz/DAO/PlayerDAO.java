package edu.hitsz.DAO;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface PlayerDAO {
    public Player findPlayer(int rank) throws IOException, ClassNotFoundException;

    public ArrayList<Player> getAllPlayer(int level) throws IOException, ClassNotFoundException;

    //    将数据插入文件
    void doAdd(Player player,int level) throws IOException, ClassNotFoundException;

    public void doDelet(int rank,int level) throws IOException, ClassNotFoundException;
}
