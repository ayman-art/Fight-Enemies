package com.ayman.fightEnemies.gui.states;

import com.ayman.fightEnemies.gui.AppFrame;

import javax.swing.*;
import java.awt.*;

public class AboutState extends GuiState{

    JLabel aboutLabel;

    JButton backButton;

    public static String aboutText = "This is a game about fighting enemies";



    public AboutState() {
        aboutLabel = new JLabel(aboutText);
        aboutLabel.setBounds(100, 100, 300, 100);
        aboutLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
        aboutLabel.setForeground(Color.GREEN);

        backButton = new JButton("Back");
        backButton.setBounds(100, 350, 400, 100);
        backButton.setBackground(Color.PINK);
        backButton.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 15));
    }
    @Override
    public void update(AppFrame frame) {
        frame.getContentPane().removeAll();
        frame.getContentPane().repaint();

        frame.add(aboutLabel);
        frame.add(backButton);

        backButton.addActionListener(e -> {
            frame.setGuiState(new MainMenuState());
        });

    }
}
