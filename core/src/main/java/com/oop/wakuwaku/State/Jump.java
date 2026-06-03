package com.oop.wakuwaku.State;

import com.badlogic.gdx.Input;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.world.GameWorld;

public class Jump extends PlayerState {

    public static final Jump INSTANCE = new Jump();

    private boolean jumpRequest = false;

    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        jumpRequest = true;
    }

    public boolean isJumpRequest() { return jumpRequest;}

    public void turnOffJumpRequest() { jumpRequest = false; }

    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        float y_velocity = gameWorld.getPlayer().getVelocity().y;
        // Skip falling check on the first frame after jump to let impulse apply properly
        if (collisionDetector.isTouchingGround() && y_velocity <= 0) {
            playerStateHandler.changeState(delta, Idle.INSTANCE);
        } else if (y_velocity < 0) {
            playerStateHandler.changeState(delta, Falling.INSTANCE);
        } else if (input.isPressed(Input.Keys.K) && collisionDetector.isTouchingWall()) {
            playerStateHandler.changeState(delta, WallAttach.INSTANCE);
        }
    }

    public void exit(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {}
}
