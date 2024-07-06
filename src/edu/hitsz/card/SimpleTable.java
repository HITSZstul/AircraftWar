package edu.hitsz.card;

import edu.hitsz.DAO.Player;
import edu.hitsz.DAO.PlayerDaoImpl;
import edu.hitsz.application.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SimpleTable {
    private JPanel MainPanel;
    private JPanel TopPanel;
    private JScrollPane tableScrollPane;
    private JTable scoretable;
    private JLabel headerLabel;
    private JButton delect;
    public SimpleTable(int level) throws IOException, ClassNotFoundException {
        PlayerDaoImpl demo = new PlayerDaoImpl();
        int rank = 0;
        ArrayList<Player> players = new ArrayList<>();
        players = demo.getAllPlayer(level);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 定义日期格式
        String[] tableTitle = {"排名","玩家名","分数","游戏时间"};
        String[][] playertable = new String[players.size()][4];
        for(int i = 0;i<players.size();i++)
        {
            playertable[i][0] = String.valueOf(players.get(i).getRank()+1);
            playertable[i][1] = players.get(i).getUsername();
            playertable[i][2] = String.valueOf(players.get(i).getScore());
            playertable[i][3] = sdf.format(players.get(i).getDate());
        }
        DefaultTableModel model = new DefaultTableModel(playertable,tableTitle){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        scoretable.setModel(model);
        tableScrollPane.setViewportView(scoretable);
        delect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoretable.getSelectedRow();
                System.out.println(row);
                if (row != -1) {
                    model.removeRow(row);
                }
//                点击删除按键后调出删除窗口
                delplayer delete = new delplayer(row,level);
                Main.cardPanel.add(delete.getMainPanel());
                Main.cardLayout.last(Main.cardPanel);
            }
        });
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JFrame frame = new JFrame("SimpleTable");
        frame.setContentPane(new SimpleTable(1).MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }
}
