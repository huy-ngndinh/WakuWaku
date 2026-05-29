package com.oop.wakuwaku.State;

import com.badlogic.gdx.Input;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.world.GameWorld;

public class Walking extends PlayerState{

    public static final Walking INSTANCE = new Walking();

    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {}

    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if (input.isPressed(Input.Keys.K) && collisionDetector.isTouchingWall()) {
            playerStateHandler.changeState(delta, WallAttach.INSTANCE);
        } else if (input.isPressed(Input.Keys.A)) {
            gameWorld.getPlayer().setDirection(-1);
            gameWorld.getPlayer().setJumpDirection(-1);
            if (input.isPressed(Input.Keys.SPACE)) {
                playerStateHandler.changeState(delta, BeforeJump.INSTANCE);
                playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);
            } else if (!collisionDetector.isTouchingGround()){
                playerStateHandler.changeState(delta, Falling.INSTANCE);
            } else {
                playerStateHandler.changeState(delta, Walking.INSTANCE);
            }
        } else if (input.isPressed(Input.Keys.D)) {
            gameWorld.getPlayer().setDirection(1);
            gameWorld.getPlayer().setJumpDirection(1);
            if (input.isPressed(Input.Keys.SPACE)) {
                playerStateHandler.changeState(delta, BeforeJump.INSTANCE);
            } else if (!collisionDetector.isTouchingGround()){
                playerStateHandler.changeState(delta, Falling.INSTANCE);
            } else {
                playerStateHandler.changeState(delta, Walking.INSTANCE);
            }
        } else {
            gameWorld.getPlayer().setJumpDirection(0);
            playerStateHandler.changeState(delta, Idle.INSTANCE);
        }
    }

    public void exit(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {}
}
