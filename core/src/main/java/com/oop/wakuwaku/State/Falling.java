package com.oop.wakuwaku.State;

import com.badlogic.gdx.Input;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.world.GameWorld;

public class Falling extends PlayerState {

    public static final Falling INSTANCE = new Falling();
    private float fallCoeff;
    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        this.fallCoeff = 0;
    }

    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if (collisionDetector.isTouchingGround()) {
            playerStateHandler.changeState(delta, Idle.INSTANCE);
        } else if (input.isPressed(Input.Keys.K) && collisionDetector.isTouchingWall()) {
            playerStateHandler.changeState(delta, WallAttach.INSTANCE);
        }
        else if (input.isPressed(Input.Keys.K) && collisionDetector.isTouchingHook()) {
            playerStateHandler.changeState(delta, WallHanging.INSTANCE);
        }
        fallCoeff += 0.01f;
    }

    public float getFallCoeff (){
        return this.fallCoeff;
    }
    public void exit(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {}
}
