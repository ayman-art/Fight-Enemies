package com.ayman.fightEnemies;


import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.Graphics.SpriteSheet;
import com.ayman.fightEnemies.Input.Keyboard;
import com.ayman.fightEnemies.Input.Mouse;
import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.level.RandomLevel;
import com.ayman.fightEnemies.level.SpawnLevel;
import com.ayman.fightEnemies.level.TileCoordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.List;
import java.util.Random;

public class Game extends Canvas implements Runnable{




    public static final int width = 300;
    public static final int height = width / 12 * 8;
    public static final int scaleFactor = 3;


    private boolean running = false;


    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();



    private Screen screen;
    public Keyboard keyboard;



    private Thread thread;
    public JFrame jFrame;

    public Level level;

    private List<Player> players;



    final String playerName;
    public Game(String playerName) {
        this.playerName = playerName;

        Dimension size = new Dimension(width * scaleFactor, height * scaleFactor);
        setPreferredSize(size);


        screen = new Screen(width, height);
        keyboard = new Keyboard();
        addKeyListener(keyboard);

        Mouse mouse = new Mouse();
        addMouseListener(mouse); // for mouse pressed and released
        addMouseMotionListener(mouse); // for mouse moved and dragged

        jFrame = new JFrame();

//        level = new RandomLevel(64, 64);
        level = Level.spawn;

        TileCoordinate playerSpawn = new TileCoordinate(3, 9);
        Player player = new Player(playerName ,playerSpawn.x(), playerSpawn.y(), keyboard);
        level.add(player);

        Game game = this;
        game.jFrame.setResizable(false);
        game.jFrame.setTitle("FightEnemies");
        game.jFrame.add(game);
        game.jFrame.pack();
        game.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.jFrame.setLocationRelativeTo(null);
        game.jFrame.setVisible(true);


        game.requestFocus(); //request focus for the game

        game.start();

        setFocusable(true);
    }





    public synchronized void start() {

        running = true;
        thread = new Thread(this, "Game");
        thread.start();
    }

    public synchronized void stop() throws InterruptedException {

        running = false;

        //wait until the thread dies first
        thread.join();
    }
    @Override
    public void run() {

        running = true;

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0; //for converting to one of sixty part of second
        double delta = 0 / ns;
        int frames = 0;
        int updates = 0;
        while(running) {
            long now = System.nanoTime();

            delta += (now - lastTime) / ns; // number of "Updates" we need to do
            lastTime = now;

            while(delta >= 1) {
                update();
                updates++;
                delta--;
            }

            //render limit without limit
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {     // Update the title every second
                timer += 1000;
                jFrame.setTitle("FightEnemies | " + updates + " ups, " + frames + " fps - " + playerName);

                //reset the updates and frames
                updates = 0;
                frames = 0;
            }
        }
        try {
            stop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {

        level.update();

        keyboard.update();


    }

    public void render() {

        Player player = level.getPlayer();

        BufferStrategy bufferStrategy = getBufferStrategy();
        if(bufferStrategy == null) {
            createBufferStrategy(3); //triple buffering for faster rendering
            return;
        }

        screen.clear();
        // Center the screen on the player
        int xScroll = player.x - screen.width / 2;
        int yScroll = player.y - screen.height / 2;
        level.render(xScroll, yScroll, screen);
//        screen.renderSpriteSheet(player.x, player.y, SpriteSheet.tiles, false);

        for(int i = 0; i < pixels.length; i++) {
            this.pixels[i] = screen.pixels[i]; //copy the pixels data from screen to the pixels array of the image object
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        graphics.setColor(Color.black);
        graphics.setFont(new Font("Verdana", Font.PLAIN, 50));
        graphics.drawString("X: " + player.x + ", Y: " + player.y, 450, 450);
        graphics.drawString(Mouse.getButton() + "", 80, 80);


        graphics.fillRect(Mouse.getX() , Mouse.getY(), 8, 8);
        graphics.dispose();
        bufferStrategy.show();




    }

    public static void main(String[] args) {
        Game game = new Game("SASA");
    }

    public Level getLevel() {
        return level;
    }
}


