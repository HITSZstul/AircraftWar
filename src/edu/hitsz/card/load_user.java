package edu.hitsz.card;

import edu.hitsz.DAO.Player;
import edu.hitsz.DAO.PlayerDAO;
import edu.hitsz.DAO.PlayerDaoImpl;
import edu.hitsz.application.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class load_user {
    private JPanel mainPanel;
    private JTextField username;
    private JButton notin;
    private JButton input;

    public load_user(int score,int level) {

        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = username.getText();
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                Player player = new Player(0,score,name,date);
                PlayerDAO playerDAO = new PlayerDaoImpl();
                //排行榜更新与写入
                try {
                    playerDAO.doAdd(player,level);
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                SimpleTable table = null;
                try {
                    table = new SimpleTable(level);
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                Main.cardPanel.add(table.getMainPanel());
                Main.cardLayout.last(Main.cardPanel);
            }
        });
        notin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleTable table = null;
                try {
                    table = new SimpleTable(level);
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                Main.cardPanel.add(table.getMainPanel());
                Main.cardLayout.last(Main.cardPanel);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DelTable");
        frame.setContentPane(new load_user(0,0).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
