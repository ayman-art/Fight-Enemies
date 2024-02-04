package com.ayman.fightEnemies.network;

import com.ayman.fightEnemies.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;


public class Login extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JTextField txtName;
    private final JTextField txtAddress;
    private final JTextField txtPort;

    public Login() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        setResizable(false);
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 380);
        setLocationRelativeTo(null);
        //panel to hold all the components
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(null);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(127, 34, 45, 16);
        contentPanel.add(lblName);

        txtName = new JTextField();
        txtName.setBounds(67, 50, 165, 28);
        contentPanel.add(txtName);
        txtName.setColumns(10);


        JLabel lblIpAddress = new JLabel("IP Address of the Game Server you want to connect to:");
        lblIpAddress.setBounds(20, 96, 300, 16);
        contentPanel.add(lblIpAddress);

        txtAddress = new JTextField();
        txtAddress.setBounds(67, 116, 165, 28);
        contentPanel.add(txtAddress);
        txtAddress.setColumns(10);

        JLabel lblAddressDesc = new JLabel("(eg. 192.168.1.2)");
        lblAddressDesc.setBounds(100, 142, 112, 16);

        contentPanel.add(lblAddressDesc);
        JLabel lblPort = new JLabel("Server Port:");
        lblPort.setBounds(113, 171, 70, 16);
        contentPanel.add(lblPort);

        txtPort = new JTextField();
        txtPort.setColumns(10);
        txtPort.setBounds(67, 191, 165, 28);
        contentPanel.add(txtPort);



        JLabel lblPortDesc = new JLabel("(eg. 8192)");
        lblPortDesc.setBounds(116, 218, 68, 16);
        contentPanel.add(lblPortDesc);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText();
                String address = txtAddress.getText();
                int port = Integer.parseInt(txtPort.getText());
                login(name, address, port);
            }
        });
        btnLogin.setBounds(91, 311, 117, 29);
        contentPanel.add(btnLogin);
    }

    private void login(String name, String address, int port) {
        dispose();
        Game game = new Game(txtName.getText());
        game.jFrame.setResizable(false);
        game.jFrame.setTitle("FightEnemies - " + name);
        game.jFrame.add(game);
        game.jFrame.pack();
        game.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.jFrame.setLocationRelativeTo(null);
        game.jFrame.setVisible(true);

        game.start();

        game.requestFocus(); //request focus for the game

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}