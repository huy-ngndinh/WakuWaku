package com.oop.wakuwaku.State;

import com.badlogic.gdx.Input;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.world.GameWorld;

public class Falling extends PlayerState {

    public static final Falling INSTANCE = new Falling();

    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
    }

    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if (collisionDetector.isTouchingGround()) {
            playerStateHandler.changeState(delta, Idle.INSTANCE);
        } else if (input.isPressed(Input.Keys.K) && collisionDetector.isTouchingWall()) {
            playerStateHandler.changeState(delta, WallAttach.INSTANCE);
        }
    }

    public void exit(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {}
}
