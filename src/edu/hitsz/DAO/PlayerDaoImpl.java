package edu.hitsz.DAO;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayerDaoImpl implements PlayerDAO{
    ArrayList<Player> Players = new ArrayList<>();
//    private List<Player> players;
//    从文件中查找索引
    @Override
    public Player findPlayer(int rank) throws IOException, ClassNotFoundException {
        File file = new File("rank.txt");
        io demo = new io();
        if(file.length()>0){
//            将文件读取
            Players = demo.fileToObject(file);
            if(rank<Players.size()){
                return Players.get(rank-1);
            }
        }
        return null;
    }
//从文件中读取所有数据

    @Override
    public ArrayList<Player> getAllPlayer(int level) throws IOException, ClassNotFoundException {
        File file;
        if(level == 1){
            file = new File("rank_easy.txt");
        }
        else if (level == 2) {
            file = new File("rank_mid.txt");
        }
        else{
            file = new File("rank_hard.txt");
        }
        io demo = new io();
        if(file.length()>0){
//            将文件读取
            Players = demo.fileToObject(file);
        }
        return Players;
    }

//    将数据插入文件
    @Override
    public void doAdd(Player player,int level) throws IOException, ClassNotFoundException {
        File file;
        if(level == 1){
            file = new File("rank_easy.txt");
        }
        else if (level == 2) {
            file = new File("rank_mid.txt");
        }
        else{
            file = new File("rank_hard.txt");
        }
        io demo = new io();
        if(file.length()>0){
//            将文件读取
            Players = demo.fileToObject(file);
        }
        else{
            Players.add(player);
        }
        int i ;
        int flag = 0;
        for(i = 0;i<Players.size();i++){
            if(Players.get(i).getScore()<player.getScore()){
                flag = 1;
                int j ;
                for(j = i; j < Players.size();j++){
                    Players.get(j).setRank(j+1);
                }
                Players.add(i,player);
                player.setRank(i);
                break;
            }
        }
        if(flag==0&&file.length()>0){
            Players.add(Players.size(),player);
            player.setRank(Players.size()-1);
        }
        demo.objectToFile(Players,file);
    }
//将数据从文件中删除
    @Override
    public void doDelet(int rank,int level) throws IOException, ClassNotFoundException {
        File file;
        if(level == 1){
            file = new File("rank_easy.txt");
        }
        else if (level == 2) {
            file = new File("rank_mid.txt");
        }
        else{
            file = new File("rank_hard.txt");
        }
        io demo = new io();
        if(file.length()>0){
//            将文件读取
            Players = demo.fileToObject(file);
            if(rank<Players.size()){
                Players.remove(rank);
                for(int i = rank;i<Players.size();i++){
                    Players.get(i).setRank(Players.get(i).getRank()-1);
                }
                demo.objectToFile(Players,file);
            }
        }
    }
}
