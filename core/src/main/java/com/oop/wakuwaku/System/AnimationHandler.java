package com.oop.wakuwaku.System;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oop.wakuwaku.Animation.AnimationState;
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
import com.oop.wakuwaku.world.Player;

/**
 * Master class for managing animation state.
 */
public class AnimationHandler {

    private float stateTime = 0f;
    private AnimationState currentAnimationState;

    /**
     * Update the current animation state
     * @param newAnimationState
     */
    public void updateAnimationState(PlayerState newState) {
        if (newState instanceof Idle) {
            this.currentAnimationState = IdleAnimation.INSTANCE;
        } else if(newState instanceof Walking) {
            this.currentAnimationState = WalkingAnimation.INSTANCE;
        } else if (newState instanceof Jump) {
            this.currentAnimationState = JumpAnimation.INSTANCE;
        } else if (newState instanceof Falling) {
            this.currentAnimationState = FallingAnimation.INSTANCE;
        } else if (newState instanceof WallAttach) {
            this.currentAnimationState = WallAttachAnimation.INSTANCE;
        } else if (newState instanceof WallClimb) {
            this.currentAnimationState = WallClimbAnimation.INSTANCE;
        } else if (newState instanceof WallKick) {
            this.currentAnimationState = WallKickAnimation.INSTANCE;
        } else if(newState instanceof BeforeJump){
            this.currentAnimationState = BeforeJumpAnimation.INSTANCE;
        } else if (newState instanceof BeforeWallKick) {
            this.currentAnimationState = BeforeWallKickAnimation.INSTANCE;
        } else if (newState instanceof WallHanging) {
            this.currentAnimationState = WallHangingAnimation.INSTANCE;
        } else if (newState instanceof WallClimbOver) {
            this.currentAnimationState = WallClimbOverAnimation.INSTANCE;
        }
        stateTime = 0f;
    }

    /**
     * Get the current animation frame
     * @param deltaTime
     * @param player
     * @param playerStateHandler
     * @return
     */
    public TextureRegion getCurrentAnimationFrame(float deltaTime, Player player, PlayerStateHandler playerStateHandler) {
        PlayerState currentState = playerStateHandler.getCurrentState();
        TextureRegion animationFrame = currentAnimationState.getTextureRegion(stateTime, currentState, player);
        stateTime += deltaTime;
        return animationFrame;
    }
}
