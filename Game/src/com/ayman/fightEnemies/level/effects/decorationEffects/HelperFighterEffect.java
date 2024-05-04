package com.ayman.fightEnemies.level.effects.decorationEffects;

import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.HelperFighterDecorator;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.level.effects.Effect;
import com.ayman.fightEnemies.util.Vector2i;

/**
 * This class is used to create the HelperFighterEffect which is an effect that helps the player fight the enemies.

 */
public class HelperFighterEffect extends Effect {
    public HelperFighterEffect(Vector2i position) {
        super(position, helperFighterAnimatedSprite);
    }

    public HelperFighterEffect(int x, int y) {
        this(new Vector2i(x, y));
    }
    @Override
    public void applyEffect(Level level, Player player) {
        if(!isRemoved()) {
            int index = level.getPlayerIndex(player);
            HelperFighterDecorator decoratedPlayer = new HelperFighterDecorator(level.getPlayer(index));
            level.setPlayer(index, decoratedPlayer);
        }
    }
}
