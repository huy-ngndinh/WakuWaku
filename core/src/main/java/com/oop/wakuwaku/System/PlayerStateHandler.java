package com.oop.wakuwaku.System;

import com.badlogic.gdx.Game;
import com.oop.wakuwaku.Animation.*;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.State.*;
import com.oop.wakuwaku.world.GameWorld;

/**
 * Master class for managing player state.
 */
public class PlayerStateHandler {


    private final GameInput gameInput;
    private final CollisionDetector collisionDetector;
    private final GameWorld gameWorld;
    private final AnimationHandler animationHandler;
    private PlayerState currentState;

    public PlayerStateHandler(GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld, AnimationHandler animationHandler) {
        this.gameInput = input;
        this.collisionDetector = collisionDetector;
        this.gameWorld = gameWorld;
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
    public void updateState(float delta) {
        currentState.update(delta, this, gameInput, collisionDetector, gameWorld);
    }

    /**
     * Change current state. Called by the states themselves.
     * @param newState
     */
    public void changeState(float delta, PlayerState newState) {
        if (currentState.equals(newState)) return;
        currentState.exit(delta, this, gameInput, collisionDetector, gameWorld);
        this.currentState = newState;
        currentState.enter(delta,this, gameInput, collisionDetector, gameWorld);
        animationHandler.updateAnimationState(currentState);
    }

    public PlayerState getCurrentState() {
        return currentState;
    }
}
