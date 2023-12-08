package com.ayman.fightEnemies.level.tile;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;

public class Tile {


        public int x, y;
        public Sprite sprite;

        public static Tile sky = new SkyTile(Sprite.sky);
        public static Tile bird = new BirdTile(Sprite.bird);
        public static Tile voidTile = new VoidTile(Sprite.voidSprite);




        public Tile(Sprite sprite) {

            this.sprite = sprite;
        }

        public void render(int x, int y, Screen screen) {


        }

        public boolean isSolid() {

            return false;
        }


}