package com.oop.wakuwaku.System;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oop.wakuwaku.Animation.AnimationState;
import com.oop.wakuwaku.State.*;
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
    public void updateAnimationState(AnimationState newAnimationState) {
        this.currentAnimationState = newAnimationState;
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
