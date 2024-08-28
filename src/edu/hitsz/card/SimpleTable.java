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
import java.util.Date;

public class SimpleTable {
    private JPanel MainPanel;
    private JPanel TopPanel;
    private JScrollPane tableScrollPane;
    private JTable scoretable;
    private JLabel levelLabel;
    private JButton delect;

    private int level;
    private PlayerDaoImpl demo;
    ArrayList<Player> players;

    String[] tableTitle = {"排名","玩家名","分数","游戏时间"};
    String[][] playertable =  null;

    public SimpleTable(int level) throws IOException, ClassNotFoundException {
        if(level==0){
            levelLabel.setText("难度：EASY");
        }else if (level==1){
            levelLabel.setText("难度：MEDIUM");
        }else if (level==2){
            levelLabel.setText("难度：HARD");
        }
        demo = new PlayerDaoImpl();
        this.level = level;
        get_table();
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
                    int result = JOptionPane.showConfirmDialog(delect, "是否确定中删除？");
                    if (JOptionPane.YES_OPTION == result ) {
                        try {
                            demo.doDelet(row,level);
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "没有选中删除选项", "warning",JOptionPane.WARNING_MESSAGE);
                    //model.removeRow(row);
                }
                try {
                    get_table();
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
//                点击删除按键后调出删除窗口
//                delplayer delete = new delplayer(row,level);
//                Main.cardPanel.add(delete.getMainPanel());
//                Main.cardLayout.last(Main.cardPanel);
            }
        });
    }

    public void get_table() throws IOException, ClassNotFoundException {
        players = demo.getAllPlayer(level);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 定义日期格式
        playertable = new String[players.size()][4];
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
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JFrame frame = new JFrame("SimpleTable");
        frame.setContentPane(new SimpleTable(1).MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void input(int score, int level) throws IOException, ClassNotFoundException {
        // 弹出一个带有输入框的对话框
        String input = null;
        input = JOptionPane.showInputDialog(null, "游戏结束，您的分数为"+score+"\n"+"请输入名字记录得分：");
        System.out.println(input);
        if (input == null || input.isEmpty()) {
            JOptionPane.showMessageDialog(null, "未成功保存数据", "warning",JOptionPane.WARNING_MESSAGE);
        }else{
            //获取当前时间
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
            Player player = new Player(0,score,input,date);
            System.out.println(player.getUsername());
//            User user = new User();
//            user.setUserName(input);
//            user.setScore(score);
//            user.setOverTime(formatter.format(date));
            //记录数据
            demo.doAdd(player,level);
            refresh();
        }
    }

    public void refresh() throws IOException, ClassNotFoundException {
        players = demo.getAllPlayer(level);
        get_table();
    }
    public JPanel getMainPanel() {
        return MainPanel;
    }
}
