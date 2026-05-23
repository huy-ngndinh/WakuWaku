package com.oop.wakuwaku.State;

import com.badlogic.gdx.Input;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.world.GameWorld;
public class WallKick extends PlayerState {

    public static final WallKick INSTANCE = new WallKick();

    private boolean jumpRequest = false;

    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        jumpRequest = true;
        if (collisionDetector.isTouchingLeftWall()) {
            gameWorld.getPlayer().setDirection(1);
        } else {
            gameWorld.getPlayer().setDirection(-1);
        }
    }

    public boolean isJumpRequest() { return jumpRequest;}

    public void turnOffJumpRequest() { jumpRequest = false; }

    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if (collisionDetector.isTouchingGround()) {
            playerStateHandler.changeState(delta, Idle.INSTANCE);
        } else if (gameWorld.getPlayer().getVelocity().y < 0) {
            playerStateHandler.changeState(delta, Falling.INSTANCE);
        }else if(collisionDetector.isTouchingWall() && input.isPressed(Input.Keys.K)) {
            playerStateHandler.changeState(delta, WallAttach.INSTANCE);
        }
    }

    public void exit(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {

    }
}
