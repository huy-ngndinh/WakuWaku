package com.oop.wakuwaku.State;

import com.badlogic.gdx.Input;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.world.GameWorld;

public class Idle extends PlayerState {

    public static final Idle INSTANCE = new Idle();

    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {}

    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if (!collisionDetector.isTouchingGround()) {
            playerStateHandler.changeState(delta, Falling.INSTANCE);
        } else if (input.isPressed(Input.Keys.SPACE)) {
            playerStateHandler.changeState(delta, BeforeJump.INSTANCE);
        } else if (input.isPressed(Input.Keys.A)) {
            gameWorld.getPlayer().setDirection(-1);
            gameWorld.getPlayer().setJumpDirection(-1);
            playerStateHandler.changeState(delta, Walking.INSTANCE);
        } else if (input.isPressed(Input.Keys.D)) {
            gameWorld.getPlayer().setDirection(1);
            gameWorld.getPlayer().setJumpDirection(1);
            playerStateHandler.changeState(delta, Walking.INSTANCE);
        } else if (input.isPressed(Input.Keys.K) && collisionDetector.isTouchingWall()) {
            playerStateHandler.changeState(delta, WallAttach.INSTANCE);
        }
        else if(input.isPressed(Input.Keys.J) && collisionDetector.isTouchingHook()) {
            playerStateHandler.changeState(delta, WallClimbOver.INSTANCE);
        }
    }

    public void exit(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {}
}
