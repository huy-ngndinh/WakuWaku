package com.oop.wakuwaku.State;

import com.badlogic.gdx.Input;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.world.GameWorld;

public class WallAttach extends PlayerState {

    public static final WallAttach INSTANCE = new WallAttach();

    private int direction;

    // Direction points away from the wall
    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if (collisionDetector.isTouchingLeftWall()) {
            direction = 1;
        } else {
            direction = -1;
        }
    }

    public int getWallDirection() {
        return direction;
    }

    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if (collisionDetector.isTouchingLeftWall() && input.isPressed(Input.Keys.D) && input.isPressed(Input.Keys.SPACE)) {
            playerStateHandler.changeState(WallKick.INSTANCE);
            playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);
        } else if (!collisionDetector.isTouchingLeftWall() && input.isPressed(Input.Keys.A) && input.isPressed(Input.Keys.SPACE)) {
            playerStateHandler.changeState(WallKick.INSTANCE);
            playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);
        } else if (collisionDetector.isTouchingWall() && !input.isPressed(Input.Keys.D) && !input.isPressed(Input.Keys.A) && input.isPressed(Input.Keys.SPACE)) {
            playerStateHandler.changeState(WallSprint.INSTANCE);
            playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);
        } else if (!input.isPressed(Input.Keys.K) || !collisionDetector.isTouchingWall()) {
            playerStateHandler.changeState(Falling.INSTANCE);
            playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);
        } else if (input.isPressed(Input.Keys.W)) {
            playerStateHandler.changeState(WallClimb.INSTANCE);
            playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);
        }
    }

    public void exit() {

    }
}
