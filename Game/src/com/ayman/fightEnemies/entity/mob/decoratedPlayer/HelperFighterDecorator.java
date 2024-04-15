package com.ayman.fightEnemies.entity.mob.decoratedPlayer;

import com.ayman.fightEnemies.entity.mob.Helper;
import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.level.effects.Effect;

public class HelperFighterDecorator extends DecoratedPlayer {
    Helper helper;
    public HelperFighterDecorator(IPlayer player) {
        super(player);
        time = Effect.EFFECT_TIME;
        System.out.println("HelperFighterDecorator1");


    }

    @Override
    public void update() {
        if (!isStillDecorated()) {
            player.update();
            return;
        }

        if(player.getLevel() != null && helper == null) {
            helper = new Helper(getInnerMostPlayer());
            player.getLevel().add(helper);
        }
        player.update();
        System.out.println("HelperFighterDecorator");

        time--;
        if (time == 0) {
            removeDecoration();
            setStillDecorated(false);
        }
    }


    @Override
    public void removeDecoration() {
        System.out.println("Removing the helper");
        helper.remove();

    }
    @Override
    public IPlayer clone() throws CloneNotSupportedException {
        var ret = new HelperFighterDecorator((IPlayer) player.clone());
        ret.player = (IPlayer) player.clone();
        ret.time = time;
        ret.helper = helper;
        return ret;
    }
}
