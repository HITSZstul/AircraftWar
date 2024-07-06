package edu.hitsz.DAO;

import java.io.*;
import java.util.ArrayList;

public class io {
    public ArrayList<Player> fileToObject(File file)throws IOException, ClassNotFoundException {
        //构建文件输入流
        FileInputStream fis = new FileInputStream(file);
        //构建对象输入流（读文件）
        ObjectInputStream ois = new ObjectInputStream(fis);
        //创建存储对象的集合
        //read 获取对象
        ArrayList<Player> Res = (ArrayList<Player>)ois.readObject();
        //释放资源
        ois.close();
        return Res;
    }
    public void objectToFile(ArrayList<Player> Players, File file) throws IOException {
        //构建文件输出流
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        //将集合中的对象序列化写入文件
        oos.writeObject(Players);
        //刷新缓冲区
        oos.flush();
        //关闭输出流并释放资源
        oos.close();
    }
}
