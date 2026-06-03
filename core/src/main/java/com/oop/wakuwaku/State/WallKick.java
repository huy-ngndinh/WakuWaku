package com.oop.wakuwaku.State;

import com.badlogic.gdx.Input;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.world.GameWorld;
public class WallKick extends PlayerState {

    public static final WallKick INSTANCE = new WallKick();

    private boolean jumpRequest = false;
    // if the player holds K while performing a wall kick, the player state will change to wallAttach after 1 frame
    // add a timer to prevent immediate wallAttach, see below
    private int wallReattachCooldown;

    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        jumpRequest = true;
        if (collisionDetector.isTouchingLeftWall()) {
            gameWorld.getPlayer().setDirection(1);
        } else {
            gameWorld.getPlayer().setDirection(-1);
        }
        // only detects wallAttach after 3 frames
        wallReattachCooldown = 3;
    }

    public boolean isJumpRequest() { return jumpRequest;}

    public void turnOffJumpRequest() { jumpRequest = false; }

    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        wallReattachCooldown = Math.max(0, wallReattachCooldown - 1);
        if (collisionDetector.isTouchingGround()) {
            playerStateHandler.changeState(delta, Idle.INSTANCE);
        } else if (gameWorld.getPlayer().getVelocity().y < 0) {
            playerStateHandler.changeState(delta, Falling.INSTANCE);
        }else if(collisionDetector.isTouchingWall() && input.isPressed(Input.Keys.K) && wallReattachCooldown == 0) {
            playerStateHandler.changeState(delta, WallAttach.INSTANCE);
        }
    }

    public void exit(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {

    }
}
