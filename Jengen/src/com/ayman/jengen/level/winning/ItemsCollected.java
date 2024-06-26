package com.ayman.jengen.level.winning;

import com.ayman.jengen.level.Level;

/**
 * This class is used in the GameController to check if the player has collected all the items(coins).
 */
public class ItemsCollected implements WinningState{
    @Override
    public boolean checkWinningState(Level level) {
        return level.getNumberOfCoins() == 0;
    }
}
