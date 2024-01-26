package com.ayman.fightEnemies.Graphics;

public class AnimatedSprite extends Sprite {


    private int frame = 0; //the current frame of the animation
    private Sprite sprite;
    private int rate = 5; //the rate at which the frames change
    private int time = 0; //the time since the last frame change
    private int length = -1; //the length of the animation(in frames)

    public AnimatedSprite(SpriteSheet sheet, int width, int height, int length) {
        super(sheet, width, height);
        this.length = length;
        if(length > sheet.getSprites().length) System.err.println("Error! the length of the animation is greater than the number of sprites in the sheet!");

    }

    public void update() {
        time++;
        if(time % rate == 0) {
            if(frame >= length - 1) frame = 0;
            else frame++;
            sprite = sheet.getSprites()[frame];
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setFrameRate(int frames) {
        rate = frames;
    }
}