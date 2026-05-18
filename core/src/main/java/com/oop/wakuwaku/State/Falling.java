package com.oop.wakuwaku.State;

import com.badlogic.gdx.Input;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.input.GameInput;
import com.oop.wakuwaku.world.GameWorld;

public class Falling extends PlayerState {

    public static final Falling INSTANCE = new Falling();
    private int direction;

    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if (gameWorld.getPlayer().getDirection() == 1) {
            this.direction = 1;
        } else if (gameWorld.getPlayer().getDirection() == 0) {
            this.direction = -1;
        } else {
            this.direction = 0;
        }
    }

    private void setFallingDirection(GameInput input) {
        if (input.isPressed(Input.Keys.A)) {
            direction = 1;
        } else if (input.isPressed(Input.Keys.D)) {
            direction = -1;
        } else {
            direction = 0;
        }
    }

    public int getFallingDirection() {
        return direction;
    }

    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        setFallingDirection(input);
        if (collisionDetector.isTouchingGround()) {
            playerStateHandler.changeState(Idle.INSTANCE);
        } else if (input.isPressed(Input.Keys.K) && collisionDetector.isTouchingWall()) {
            playerStateHandler.changeState(WallAttach.INSTANCE);
            playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);
        }
    }

    public void exit() {}
}
