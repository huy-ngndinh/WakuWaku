package com.oop.wakuwaku.State;

import com.badlogic.gdx.Input;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.world.GameWorld;

public class WallAttach extends PlayerState {

    public static final WallAttach INSTANCE = new WallAttach();

    // Direction points away from the wall
    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
    }

    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if (collisionDetector.isTouchingLeftWall() && input.isPressed(Input.Keys.D) && input.isPressed(Input.Keys.SPACE)) {
            playerStateHandler.changeState(delta, BeforeWallKick.INSTANCE);
        } else if (!collisionDetector.isTouchingLeftWall() && input.isPressed(Input.Keys.A) && input.isPressed(Input.Keys.SPACE)) {
            playerStateHandler.changeState(delta, BeforeWallKick.INSTANCE);
        } else if (!input.isPressed(Input.Keys.K) || !collisionDetector.isTouchingWall()) {
            playerStateHandler.changeState(delta, Falling.INSTANCE);
        } else if (input.isPressed(Input.Keys.W)) {
            playerStateHandler.changeState(delta, WallClimb.INSTANCE);
        }
    }

    public void exit(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {

    }
}
