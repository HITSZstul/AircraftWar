package edu.hitsz.card;

import edu.hitsz.DAO.PlayerDAO;
import edu.hitsz.DAO.PlayerDaoImpl;
import edu.hitsz.application.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class delplayer {
    private JPanel mainPanel;
    private JButton delect;
    private JButton cancel;

    public delplayer(int rank,int level) {
        delect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerDAO playerDAO = new PlayerDaoImpl();
                try {
                    playerDAO.doDelet(rank,level);
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                SimpleTable table = null;
                try {
                    table = new SimpleTable(level);
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
//                执行删除操作后更新排行榜后重新输出
                Main.cardPanel.add(table.getMainPanel());
                Main.cardLayout.last(Main.cardPanel);
            }
        });
    }

    //在输入窗口中跳转到排行榜
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
