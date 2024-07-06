package edu.hitsz.card;

import edu.hitsz.application.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class StartMenu {
    private JPanel root;
    private JPanel window;
    private JButton simple;
    private JButton middle;
    private JButton hard;
    private JLabel choose;
    private JComboBox musicbox;
    private JLabel musiclabel;

    private boolean music = false;
    public StartMenu() {
        simple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new Game_easy(music);
                Main.cardPanel.add(game);
                game.action();
                if(music)
                {
                    game.Music();
                }
                Main.cardLayout.last(Main.cardPanel);
            }
        });
        middle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new Game_mid(music);
                Main.cardPanel.add(game);
                game.action();
                if(music)
                {
                    game.Music();
                }
                Main.cardLayout.last(Main.cardPanel);
            }
        });
        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new Game_hard(music);
                Main.cardPanel.add(game);
                game.action();
                if(music){
                    game.Music();
                }
                Main.cardLayout.last(Main.cardPanel);
            }
        });

        musicbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
//                    MusicThread music = new MusicThread("src/videos/bgm.wav",true);
                    if(e.getItem()=="open"){
//                        music.start();
//                        new MusicThread("src/videos/game_over.wav",true);
                        music = true;
                        System.out.println("open");
                    }
                    else{
                        music = false;
//                        music.interrupt();
//                        music.stop(true);
                        System.out.println("close");
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SampleCalculator");
        frame.setContentPane(new StartMenu().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getRoot() {
        return root;
    }
}
