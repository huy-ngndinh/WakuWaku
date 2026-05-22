package com.oop.wakuwaku.State;

import com.badlogic.gdx.Input;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.world.GameWorld;

public class Idle extends PlayerState {

    public static final Idle INSTANCE = new Idle();

    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {}

    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if (!collisionDetector.isTouchingGround()) {
            playerStateHandler.changeState(Falling.INSTANCE);
            playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);
        } else if (input.isPressed(Input.Keys.A)) {
            gameWorld.getPlayer().setDirection(1);
            playerStateHandler.changeState(Walking.INSTANCE);
        } else if (input.isPressed(Input.Keys.D)) {
            gameWorld.getPlayer().setDirection(0);
            playerStateHandler.changeState(Walking.INSTANCE);
        } else if (input.isPressed(Input.Keys.SPACE)) {
            playerStateHandler.changeState(Jump.INSTANCE);
            playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);
        } else if (input.isPressed(Input.Keys.K) && collisionDetector.isTouchingWall()) {
            playerStateHandler.changeState(WallAttach.INSTANCE);
            playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);
        }
    }

    public void exit() {}
}
