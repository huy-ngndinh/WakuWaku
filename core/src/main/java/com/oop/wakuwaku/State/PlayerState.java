package com.oop.wakuwaku.State;

import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.world.GameWorld;

/**
 * Superclass of all player state.
 */
public abstract class PlayerState {
    /**
     * Called manually when the player enter this state for the first time.
     * Required for following states: falling, wallAttach, wallClimb and wallKick state.
     */
    public abstract void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld);

    /**
     * Called continuously each frame the player is in this state
     * @param delta
     * @param playerStateHandler
     * @param input
     * @param collisionDetector
     * @param gameWorld
     */
    public abstract void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld);

    /**
     * Called before the player exit this state
     */
    public abstract void exit();
}
