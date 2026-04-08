package com.oop.wakuwaku.world;
import com.badlogic.gdx.physics.box2d.World;
public class GameWorld {
    private Player player;
    private Map map;
    public GameWorld(World world) {
        player = new Player(world);
        this.map = new Map(world);
    }
    public Player getPlayer() {
        return player;
    }
    public Map getMap() {
        return map;
    }

    public boolean update(){
        return true;
    }   
}
