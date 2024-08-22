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

    private int level;
    private PlayerDaoImpl demo;
    ArrayList<Player> players;

    String[] tableTitle = {"排名","玩家名","分数","游戏时间"};
    String[][] playertable =  null;

    public SimpleTable(int level) throws IOException, ClassNotFoundException {
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

    public JPanel getMainPanel() {
        return MainPanel;
    }
}
