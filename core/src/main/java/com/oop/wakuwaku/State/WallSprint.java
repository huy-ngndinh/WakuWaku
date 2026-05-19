package com.oop.wakuwaku.State;

import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.input.GameInput;
import com.oop.wakuwaku.world.GameWorld;

public class WallSprint extends PlayerState {
    public static final WallSprint INSTANCE = new WallSprint();

    private int direction;
    private boolean jumpRequest = false;

    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if (collisionDetector.isTouchingLeftWall()) {
            direction = 1;
        } else {
            direction = 0;
        }
        jumpRequest = true;
    }

    public int getWallDirection() {
        return direction;
    }

    public boolean isJumpRequest() { return jumpRequest;}

    public void turnOffJumpRequest() { jumpRequest = false; }

    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if (collisionDetector.isTouchingGround()) {
            playerStateHandler.changeState(Idle.INSTANCE);
        } else if (gameWorld.getPlayer().getVelocity().y < 0) {
            playerStateHandler.changeState(Falling.INSTANCE);
            playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);
        }
    }

    public void exit() {

    }
}
