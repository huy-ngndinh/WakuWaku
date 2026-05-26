package com.oop.wakuwaku.System;

import com.oop.wakuwaku.Animation.BeforeJumpAnimation;
import com.oop.wakuwaku.Animation.BeforeWallKickAnimation;
import com.oop.wakuwaku.Animation.FallingAnimation;
import com.oop.wakuwaku.Animation.IdleAnimation;
import com.oop.wakuwaku.Animation.JumpAnimation;
import com.oop.wakuwaku.Animation.WalkingAnimation;
import com.oop.wakuwaku.Animation.WallAttachAnimation;
import com.oop.wakuwaku.Animation.WallClimbAnimation;
import com.oop.wakuwaku.Animation.WallClimbOverAnimation;
import com.oop.wakuwaku.Animation.WallHangingAnimation;
import com.oop.wakuwaku.Animation.WallKickAnimation;
import com.oop.wakuwaku.Animation.WallSprintAnimation;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.State.BeforeJump;
import com.oop.wakuwaku.State.BeforeWallKick;
import com.oop.wakuwaku.State.Falling;
import com.oop.wakuwaku.State.Idle;
import com.oop.wakuwaku.State.Jump;
import com.oop.wakuwaku.State.PlayerState;
import com.oop.wakuwaku.State.Walking;
import com.oop.wakuwaku.State.WallAttach;
import com.oop.wakuwaku.State.WallClimb;
import com.oop.wakuwaku.State.WallClimbOver;
import com.oop.wakuwaku.State.WallHanging;
import com.oop.wakuwaku.State.WallKick;
import com.oop.wakuwaku.State.WallSprint;
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
        } else if (newState instanceof WallHanging) {
            animationHandler.updateAnimationState(WallHangingAnimation.INSTANCE);
        } else if (newState instanceof WallClimb) {
            animationHandler.updateAnimationState(WallClimbAnimation.INSTANCE);
        } else if (newState instanceof WallKick) {
            animationHandler.updateAnimationState(WallKickAnimation.INSTANCE);
        } else if (newState instanceof WallSprint) {
            animationHandler.updateAnimationState(WallSprintAnimation.INSTANCE);
        } else if(newState instanceof BeforeJump){
            animationHandler.updateAnimationState(BeforeJumpAnimation.INSTANCE);
        } else if (newState instanceof BeforeWallKick) {
            animationHandler.updateAnimationState(BeforeWallKickAnimation.INSTANCE);
        } else if (newState instanceof WallClimbOver) {
            animationHandler.updateAnimationState(WallClimbOverAnimation.INSTANCE);
        }
    }

    public PlayerState getCurrentState() {
        return currentState;
    }
}
