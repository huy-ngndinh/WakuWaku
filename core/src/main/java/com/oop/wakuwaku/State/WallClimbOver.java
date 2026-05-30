package com.oop.wakuwaku.State;

import com.badlogic.gdx.math.Vector2;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.world.GameWorld;

public class WallClimbOver extends PlayerState {

    public static final WallClimbOver INSTANCE = new WallClimbOver();

    private Vector2 startPos;
    private int cntFrame = 0;

    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        // this.startPos = gameWorld.getPlayer().getPosition(); // this doesn't work BS they point to the same object in runtime! so that when curpos is update, also the startPos
        this.startPos = new Vector2(gameWorld.getPlayer().getPosition());
        cntFrame = 0;
    }

    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if (Math.abs(gameWorld.getPlayer().getPosition().x - startPos.x) > 0.5f && gameWorld.getPlayer().getPosition().y - startPos.y > 0.5f) {
            playerStateHandler.changeState(delta, Idle.INSTANCE);
        }
        // System.out.println(startPos + " " + gameWorld.getPlayer().getPosition());
    }

    public void exit(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {

    }

}
