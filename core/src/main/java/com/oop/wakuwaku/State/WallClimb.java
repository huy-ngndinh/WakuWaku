package com.oop.wakuwaku.State;

import com.badlogic.gdx.Input;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.world.GameWorld;

public class WallClimb extends PlayerState {

    public static final WallClimb INSTANCE = new WallClimb();

    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
    }

    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if (collisionDetector.isTouchingHook()) {// nếu chạm hook thì cho lơ lửng trên tường
            playerStateHandler.changeState(delta, WallHanging.INSTANCE);
        } else if (collisionDetector.isTouchingLeftWall() && input.isPressed(Input.Keys.D) && input.isPressed(Input.Keys.SPACE)) {
            playerStateHandler.changeState(delta, BeforeWallKick.INSTANCE);
        } else if (!collisionDetector.isTouchingLeftWall() && input.isPressed(Input.Keys.A) && input.isPressed(Input.Keys.SPACE)) {
            playerStateHandler.changeState(delta, BeforeWallKick.INSTANCE);
        } else if (!input.isPressed(Input.Keys.K) || !collisionDetector.isTouchingWall()) {
            playerStateHandler.changeState(delta, Falling.INSTANCE);
        } else if (!input.isPressed(Input.Keys.W)) {
            playerStateHandler.changeState(delta, WallAttach.INSTANCE);
        }
    }

    public void exit(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {

    }
}
