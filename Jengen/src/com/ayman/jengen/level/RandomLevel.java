package com.ayman.jengen.level;

import com.ayman.jengen.GameController;
import com.ayman.jengen.Graphics.Sprite;
import com.ayman.jengen.entity.IEntity;
import com.ayman.jengen.entity.mob.Chaser;
import com.ayman.jengen.entity.mob.Dummy;
import com.ayman.jengen.entity.mob.IMob;
import com.ayman.jengen.level.effects.CoinEffect;
import com.ayman.jengen.level.effects.HealthEffect;
import com.ayman.jengen.level.effects.WinningEffect;
import com.ayman.jengen.level.effects.decorationEffects.BreakTilesEffect;
import com.ayman.jengen.level.effects.decorationEffects.HelperFighterEffect;
import com.ayman.jengen.level.effects.decorationEffects.InvisibilityEffect;
import com.ayman.jengen.level.effects.decorationEffects.SpeedEffect;
import com.ayman.jengen.level.tile.Tile;
import com.ayman.jengen.level.winning.ItemsCollected;
import com.ayman.jengen.level.winning.TargetReached;
import com.ayman.jengen.util.AdjacentCheckGenerator;
import com.ayman.jengen.util.DSU;
import com.ayman.jengen.util.Vector2i;

import java.util.*;

public class RandomLevel extends Level {

    private static final Random random = new Random();
    private volatile boolean done = false;

    static public int WIDTH = 64;
    static public int HEIGHT = 64;
    public DSU dsu = new DSU(width * height); // for debugging purposes
    /**
     * The number of times the level generation will try to generate a valid tile.
     */
    private static final int collisionFactor = 1;

    public RandomLevel(int width, int height) {
        super(width, height, winningState);
    }

    public RandomLevel() {
        super(WIDTH, HEIGHT, winningState);
    }

    /**
     * This method is used to generate the level.
     * It uses 2 threads to generate the level.
     * The first thread to finish will put its level in the tiles array and stop the other thread.
     * The level is generated by putting the border, obstacles, mobs, and effects.
     */
    @Override
    protected void generateLevel() {
        {
            Thread thread1 = new Thread(this::generateLevelThreadMethod);
            Thread thread2 = new Thread(thread1);

            thread1.start();
            thread2.start();

            try {
                thread1.join();
                thread2.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(int i = 0; i < width * height; i++) {
                if(tiles[i] == 0) {
                    tiles[i] = Tile.skyColor;
                }
            }
        }


    }


    /**
     * This method is used to check if the level is valid.
     * @param freeTiles the free tiles of the level
     * @return true if the level is valid, false otherwise
     */
    private boolean isValidLevel(Set<Vector2i> freeTiles) {

        Vector2i start = new Vector2i(1,1);
        if(!freeTiles.contains(start)) return false;
        Set<Vector2i> visited = new TreeSet<>( (v1, v2) -> {
            if(v1.getX() == v2.getX()) return v1.getY() - v2.getY();
            return v1.getX() - v2.getX();
        });

        Queue<Vector2i> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while(!queue.isEmpty()) {
            Vector2i current = queue.poll();
            int x = current.getX();
            int y = current.getY();




            List<Vector2i> neighbors = new ArrayList<>();
            if(x - 1 >= 0) neighbors.add(new Vector2i(x - 1, y));
            if(x + 1 < width) neighbors.add(new Vector2i(x + 1, y));
            if(y - 1 >= 0) neighbors.add(new Vector2i(x, y - 1));
            if(y + 1 < height) neighbors.add(new Vector2i(x, y + 1));

            for(Vector2i neighbor : neighbors) {
                if(freeTiles.contains(neighbor) && !visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return visited.size() == freeTiles.size() - 1;
    }


    /**
     * This method is used to generate the level in a separate thread.
     */
    protected void generateLevelThreadMethod() {

        int[] tiles = new int[width * height];
        DSU dsu = new DSU(width * height);

        putBorder(tiles, dsu);
        putObstacles(tiles, dsu);
        registerLevel(tiles);


    }
    private void putBorder(int[] tiles, DSU dsu) {
        for(int x = 0; x < width; x++) {
            int y1 = 0, y2 = height - 1;
            tiles[x + y1 * width] = Tile.brickColor;
            tiles[x + y2 * width] = Tile.brickColor;

            dsu.union(x + y1 * width, 0);
            dsu.union(x + y2 * width, 0);

        }

        for(int y = 0; y < height; y++) {
            int x1 = 0, x2 = width - 1;
            tiles[x1 + y * width] = Tile.brickColor;
            tiles[x2 + y * width] = Tile.brickColor;

            dsu.union(x1 + y * width, 0);
            dsu.union(x2 + y * width, 0);
        }
    }
    private void putObstacles(int[] tiles, DSU dsu) {

        handleLevelsBesideBorders(tiles, dsu);
        handleInnerLevels(tiles, dsu);
    }
    private void handleLevelsBesideBorders(int[] tiles, DSU dsu) {
        for(int i = 1; i < width + height>>2; i++) {
            int x, y;

            if(random.nextInt(2) == 0) {
                y = 1;
            } else {
                y = height - 2;
            }

            x = 3 + random.nextInt(width - 3 -3);
            if(x == 3 && (y == 3) || x == width - 2 && y == height - 2) {
                continue;
            }
            tiles[x + width * y] = Tile.rockColor;
            for(int xx = x - 1; xx <= x + 1; xx++) {
                for(int yy = y - 1; yy <= y + 1; yy++) {
                    if(getTile(xx, yy, tiles).isSolid()) {
                        dsu.union(xx + yy * width, x + y * width);
                    }
                }
            }

            if(random.nextInt(2) == 0) {
                x = 1;
            } else {
                x = width - 2;
            }
            y = 3 + random.nextInt(height-3 -3);
            // what is the difference between the
            if(x == 3 && (y == 3) || x == width - 2 && y == height - 2) {
                continue;
            }
            tiles[x + width * y] = Tile.rockColor;
            for(int xx = x - 1; xx <= x + 1; xx++) {
                for(int yy = y - 1; yy <= y + 1; yy++) {
                    if(getTile(xx, yy, tiles).isSolid()) {
                        dsu.union(xx + yy * width, x + y * width);
                    }
                }
            }

        }
    }

    private void handleInnerLevels(int[] tiles, DSU dsu) {

        int _i;
        int maxAttempts = GameController.getDifficulty() * (width-2) * (height-2) / 100;
        int collisions = 0;
        for(_i = 0; _i < maxAttempts && collisions < maxAttempts * collisionFactor && !done  ; _i++) {
            int x = 1 + random.nextInt(width - 2);
            int y = 1 + random.nextInt(height - 2);

            if(getTile(x, y, tiles).isSolid()) {
                _i--;
                collisions++;
                continue;
            }

            boolean ok = true;
            for(Vector2i[] vector2i : AdjacentCheckGenerator.vectors) {
                Vector2i current = new Vector2i(x, y);  current = current.add(vector2i[0]);
                Vector2i adjacent = new Vector2i(x, y); adjacent = adjacent.add(vector2i[1]);
                int p = current.getX() + current.getY() * width;
                int q = adjacent.getX() + adjacent.getY() * width;
                if(p < 0 || p >= width * height || q < 0 || q >= width * height) {
//                    System.out.println("Current: " + current + ", Adjacent: " + adjacent);
//                    System.out.println("p: " + p + ", q: " + q);
//                    System.out.println("X: " + x + ", Y: " + y);
                }
                if(dsu.connected(p, q)) {
                    ok = false;
                    break;
                }
            }

            if(ok) {
                if (getTile(x, y, tiles).isSolid())
                    throw new AssertionError();

                tiles[x + y * width] = Tile.rockColor;
                for(int i = -1; i <= 1; i++) {
                    for(int j = -1; j <= 1; j++) {
                        int p = (x + i) + (y + j) * width;
                        if(getTile(x+i, (y+j), tiles).isSolid()) {
                            dsu.union(p, x + y * width);
                        }
                    }
                }

            } else {
                _i--;
                collisions++;
            }
        }
    }



    private void registerLevel(int[] tiles) {

        //To ensure that only one thread can put its level.
        synchronized (this) {
            if(!done) {
                done = true;
                this.tiles = tiles;
                putEffects(Math.min(getEmptySlots(),getEmptySlots() / GameController.getDifficulty() ));
                putMobs(Math.min(getEmptySlots(),GameController.getDifficulty()));
            }
        }
    }

    private void putMobs(int n) {
        int x = 1 + random.nextInt(width - 2),
        y = 1 + random.nextInt(height - 2);
        while(occupiedSlot(x, y)) {
            x = 1 + random.nextInt(width - 2);
            y = 1 + random.nextInt(height - 2);
        }
        // Base case
        if(n <= 1) {
//            mobs.add(new Player(x, y, null, null));
            return;
        }

        add(Objects.requireNonNull(getRandomMob(x, y)));
        putMobs(n - 1);
    }
    private void putEffects(int n) {

        int x, y;
        do {
            x = 1 + random.nextInt(width - 2);
            y = 1 + random.nextInt(height - 2);
        } while (occupiedSlot(x, y));
        if (n == 2 && Level.winningState instanceof ItemsCollected) {
                add(new CoinEffect(x, y));
                putMobs(1);
                return;
        } else if(n == 1) {
            if(Level.winningState instanceof TargetReached)
                add(new WinningEffect(new Vector2i(x, y)));
            else if(Level.winningState instanceof ItemsCollected)
                add(new CoinEffect(x, y));
            return;
        }


        int num = random.nextInt(6);
        switch (num) {
            case 0 -> add(new CoinEffect(x, y));
            case 1 -> add(new HealthEffect(x, y));
            case 2 -> add(new BreakTilesEffect(x, y));
            case 3 -> add(new SpeedEffect(x, y));
            case 4 -> add(new HelperFighterEffect(x, y));
            case 5 -> add(new InvisibilityEffect(x, y));
            default -> throw new IllegalStateException("Unexpected value: " + num);
        }

        putEffects(n - 1);
    }
    private IMob getRandomMob(int x, int y) {
        int num = random.nextInt(2);
        switch(num) {
            case 0 -> {
                return new Dummy(x, y);
            }
            case 1 -> {
                return new Chaser(x, y);
            }
        }
        return null;
    }


    /**
     * This method is used to check if a slot is occupied.
     * @param x the x coordinate of the slot
     * @param y the y coordinate of the slot
     * @return true if the slot is occupied by a solid tile or a mob, false otherwise
     */
    public boolean occupiedSlot(int x, int y) {
        if(getTile(x, y).isSolid())
            return true;
        for(IEntity entity : mobs) {
            if(entity instanceof IMob mob) {
                if(mob.getX()/ Sprite.TILE_SIZE == x && mob.getY()/Sprite.TILE_SIZE == y)
                    return true;
            }
        }
        return false;
    }

    /**
     * This method is used to get the number of empty slots in the level.
     * @return the number of empty slots in the level
     */
    public int getEmptySlots() {
        int count = 0;
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(!getTile(x, y).isSolid()) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public Level getNextLevel() {
        return new RandomLevel(width, height);
    }

}