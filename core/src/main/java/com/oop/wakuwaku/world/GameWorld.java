package com.oop.wakuwaku.world;
import com.badlogic.gdx.physics.box2d.World;

/**
 * This class manages the physics objects that is created and controlled by <code>Physics</code>.
 */
public class GameWorld {
    private Player player;
    private Map map;
    public GameWorld(World world) {
        player = new Player(world);
        this.map = new Map(world);
    }

    /**
     * Return the <code>Player</code> instance. Only one should exist and is managed by this class.
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Return the <code>Map</code> instance. Only one should exist and is managed by this class.
     * @return Map
     */
    public Map getMap() {
        return map;
    }

    public boolean update(){
        return true;
    }
}
