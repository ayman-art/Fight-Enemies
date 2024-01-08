package com.ayman.fightEnemies.level.tile;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;

public class BrickTile extends Tile{
    public BrickTile(Sprite sprite) {
        super(sprite);
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public void render(int x, int y, Screen screen) {

        screen.renderTile(x << 4, y << 4, this);
    }
}
