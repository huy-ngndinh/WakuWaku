package com.oop.wakuwaku.System;

import com.oop.wakuwaku.Animation.*;
import com.oop.wakuwaku.State.*;
import com.oop.wakuwaku.input.GameInput;
import com.oop.wakuwaku.world.GameWorld;

/**
 * Master class for managing player state.
 */
public class PlayerStateHandler {

    private final AnimationHandler animationHandler;
    private PlayerState currentState;

    public PlayerStateHandler(AnimationHandler animationHandler) {
        this.animationHandler = animationHandler;
        currentState = new Idle();
    }

    /**
     * Update the current state.
     * @param delta
     * @param input
     * @param collisionDetector
     * @param gameWorld
     */
    public void updateState(float delta, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        currentState.update(delta, this, input, collisionDetector, gameWorld);
    }

    /**
     * Change current state. Called by the states themselves.
     * @param newState
     */
    public void changeState(PlayerState newState) {
        if (currentState.equals(newState)) return;
        this.currentState = newState;
        if (newState instanceof Idle) {
            animationHandler.updateAnimationState(IdleAnimation.INSTANCE);
        } else if(newState instanceof Walking) {
            animationHandler.updateAnimationState(WalkingAnimation.INSTANCE);
        } else if (newState instanceof Jump) {
            animationHandler.updateAnimationState(JumpAnimation.INSTANCE);
        } else if (newState instanceof Falling) {
            animationHandler.updateAnimationState(FallingAnimation.INSTANCE);
        } else if (newState instanceof WallAttach) {
            animationHandler.updateAnimationState(WallAttachAnimation.INSTANCE);
        } else if (newState instanceof WallClimb) {
            animationHandler.updateAnimationState(WallClimbAnimation.INSTANCE);
        } else if (newState instanceof WallKick) {
            animationHandler.updateAnimationState(WallKickAnimation.INSTANCE);
        } else if (newState instanceof WallSprint) {
            animationHandler.updateAnimationState(WallSprintAnimation.INSTANCE);
        }
    }

    public PlayerState getCurrentState() {
        return currentState;
    }
}
