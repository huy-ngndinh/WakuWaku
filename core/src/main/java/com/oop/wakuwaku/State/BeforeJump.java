package com.oop.wakuwaku.State;

import com.badlogic.gdx.Input;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.world.GameWorld;
public class BeforeJump extends PlayerState{

    public static final BeforeJump INSTANCE = new BeforeJump();

    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {

    }

    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if (!input.isHoldingSpace()) {
            playerStateHandler.changeState(Jump.INSTANCE);
            playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);
        } else if (input.isPressed(Input.Keys.A)) {
            gameWorld.getPlayer().setJumpDirection(-1);
        } else if (input.isPressed(Input.Keys.D)) {
            gameWorld.getPlayer().setJumpDirection(1);
        } else {
            gameWorld.getPlayer().setJumpDirection(0);
        }
    }

    public void exit() {

    }
}
