package com.ayman.jengen.gameCreation;

import com.ayman.jengen.gameCreation.contexts.*;
import com.ayman.jengen.gameCreation.contexts.levelcontexts.ILevelContext;

/**
 * Game is a class that holds the context of the game required by the engine to prduce the game.

 */
public class GameContext {

    private final GUIContext guiContext;
    private final ILevelContext levelContext;
    private final PlayerContext playerContext;
    private final ScreenContext screenContext;
    private final ProjectileContext projectileContext;
    private final AIContext aiContext;
    private final WinnigStateContext winnigStateContext;

    private GameContext(Builder builder) {
        this.guiContext = builder.GUIContext;
        this.levelContext = builder.levelContext;
        this.playerContext = builder.playerContext;
        this.screenContext = builder.screenContext;
        this.projectileContext = builder.projectileContext;
        this.aiContext = builder.aiContext;
        this.winnigStateContext = builder.winnigStateContext;
    }

    public PlayerContext getPlayerContext() {
        return playerContext;
    }

    public GUIContext getGuiContext() {
        return guiContext;
    }

    public ProjectileContext getProjectileContext() {
        return projectileContext;
    }
    public AIContext getAiContext() {
        return aiContext;
    }
    public ILevelContext getLevelContext() {
        return levelContext;
    }
    public ScreenContext getScreenContext() {
        return screenContext;
    }

    public WinnigStateContext getWinnigStateContext() {
        return winnigStateContext;
    }


    public static class Builder {
        private GUIContext GUIContext;
        private ILevelContext levelContext;
        private PlayerContext playerContext;
        private ScreenContext screenContext;
        private ProjectileContext projectileContext;
        private AIContext aiContext;
        private WinnigStateContext winnigStateContext;

        public Builder setGUIContext(GUIContext GUIContext) {
            this.GUIContext = GUIContext;
            return this;
        }


        public Builder setLevelContext(ILevelContext levelContext) {
            this.levelContext = levelContext;
            return this;
        }

        public Builder setPlayerContext(PlayerContext playerContext) {
            this.playerContext = playerContext;
            return this;
        }

        public Builder setScreenContext(ScreenContext screenContext) {
            this.screenContext = screenContext;
            return this;
        }

        public Builder setProjectileContext(ProjectileContext projectileContext) {
            this.projectileContext = projectileContext;
            return this;
        }
        public Builder setAIContext(AIContext aiContext) {
            this.aiContext = aiContext;
            return this;
        }
        public Builder setWinningStateContext(WinnigStateContext winnigStateContext) {
            this.winnigStateContext = winnigStateContext;
            return this;
        }


        public GameContext build() {
            return new GameContext(this);
        }
    }
}
