package com.ayman.fightEnemies.entity.mob.decoratedPlayer;

import com.ayman.fightEnemies.entity.mob.IMob;
import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.level.effects.Effect;

public class FastPlayer extends DecoratedPlayer {

    static private final int speedFactor = 4;



    public FastPlayer(IPlayer player) {
        super(player);
        this.time = Effect.EFFECT_TIME;
        player.setSpeed(speedFactor * player.getSpeed());
    }

    @Override
    public void update() {
        if(!isStillDecorated()) {
            player.update();
            return;
        }

        player.update();

        time--;
        if(time == 0) {
            removeDecoration();
            setStillDecorated(false);
        }

    }

    @Override
    public void removeDecoration() {
        player.setSpeed(player.getSpeed() / speedFactor);
    }


    @Override
    public IMob clone() throws CloneNotSupportedException {
        var ret = new FastPlayer((IPlayer) player.clone());
        ret.player = (IPlayer) player.clone();
        ret.time = time;
        return ret;
    }



}