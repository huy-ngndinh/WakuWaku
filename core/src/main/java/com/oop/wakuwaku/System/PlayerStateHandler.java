package com.oop.wakuwaku.System;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.oop.wakuwaku.input.GameInput;
import com.oop.wakuwaku.world.GameWorld;
public class PlayerStateHandler {

    public enum State {
        STAND,
        FALL,
        WALK,
        DASH,
        JUMP,
        ON_WALL,
        CLIMB,
        WALL_KICK
    }

    private State currentState;
    private float cooldownDash;
    private boolean isDifferentState;

    public PlayerStateHandler() {
        currentState = State.STAND;
        cooldownDash = 0f;
        isDifferentState = false;
    }

    public boolean isDifferentState() {
        return isDifferentState;
    }

    public State getCurrentState() {
        return currentState;
    }

    private void changeState(State state) {
        currentState = state;
    }

    private void updateCooldownDash(float delta) {
        cooldownDash = Math.max(0f,cooldownDash - delta);
    }

    public boolean isInCooldownDash() {
        return cooldownDash != 0;
    }

    public void restartCooldownDash() {
        cooldownDash = Gdx.graphics.getDeltaTime() * 60 * 2;
    }

    public void updateState(float delta, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        State oldState = currentState;
        updateCooldownDash(delta);
        switch (currentState) {
            case STAND:
                if (input.isPressed(Keys.K) && collisionDetector.isTouchingWall()) {
                    // touching wall should be prioritized before other inputs
                    changeState(State.ON_WALL);
                } else if(input.isPressed(Keys.D)){
                    gameWorld.getPlayer().setDirection(1);
                    changeState(State.WALK);
                } else if(input.isPressed(Keys.A)){
                    gameWorld.getPlayer().setDirection(0);
                    changeState(State.WALK);
                } else if(input.isPressed(Keys.SPACE)){
                    changeState(State.JUMP);
                }
                break;

            case WALK:
                if (input.isPressed(Keys.K) && collisionDetector.isTouchingWall()) {
                    // touching wall should be prioritized before other inputs
                    changeState(State.ON_WALL);
                } else if (input.isPressed(Keys.A)) {
                    // A
                    gameWorld.getPlayer().setDirection(0);
                    if (input.isPressed(Keys.SPACE)) {
                        // A and SPACE (moving jump)
                        changeState(State.JUMP);
                    } else if (input.isPressed(Keys.SHIFT_LEFT) && !isInCooldownDash()) {
                        // A and DASH (moving dash)
                        changeState(State.DASH);
                    }
                } else if (input.isPressed(Keys.D)) {
                    // D
                    gameWorld.getPlayer().setDirection(1);
                    if (input.isPressed(Keys.SPACE)) {
                        // D and SPACE (moving jump)
                        changeState(State.JUMP);
                    } else if (input.isPressed(Keys.SHIFT_LEFT) && !isInCooldownDash()) {
                        // D and DASH (moving dash)
                        changeState(State.DASH);
                    }
                } else if (input.isPressed(Keys.SPACE)) {
                    // SPACE (idle jump)
                    changeState(State.JUMP);
                } else if(!collisionDetector.isTouchingGround()){
                    // FALL
                    changeState(State.FALL);
                } else if(input.isPressed(Keys.SHIFT_LEFT) && !isInCooldownDash()) {
                    // DASH (idle dash)
                    changeState(State.DASH);
                } else {
                    // Không còn phím di chuyển -> về STAND
                    changeState(State.STAND);
                }
                break;
            case JUMP:
                // xử lý JUMP...
                if (input.isPressed(Keys.K) && collisionDetector.isTouchingWall()) {
                    changeState(State.ON_WALL);
                } else {
                    changeState(State.FALL);
                }
                break;
            case FALL:
                if(collisionDetector.isTouchingGround()){
                    if (input.isPressed(Keys.A) || input.isPressed(Keys.D)) {
                        // press A or D when touching ground: continue to move
                        changeState(State.WALK);
                    } else {
                        // else: stand
                        changeState(State.STAND);
                    }
                } else if (input.isPressed(Keys.K) && collisionDetector.isTouchingWall()) {
                    changeState(State.ON_WALL);
                }
                break;
            case DASH:
                if(gameWorld.getPlayer().isInDash()){
                    // if the player is dashing, remain in state DASH
                    changeState(State.DASH);
                } else {
                    // if the player has ended dashing, set dash cooldown timer
                    gameWorld.getPlayer().getBody().setLinearVelocity(new Vector2(0,0));
                    gameWorld.getPlayer().resetDashTimer();
                    restartCooldownDash();
                    if (input.isPressed(Keys.A) || input.isPressed(Keys.D)) {
                        // press A or D when finish dashing: continue to move
                        changeState(State.WALK);
                    } else {
                        // else: stand
                        changeState(State.STAND);
                    }
                }
                break;
            case ON_WALL:
                if (!collisionDetector.isTouchingWall()) {
                    changeState(State.FALL);
                } else if (!input.isPressed(Keys.K)) {
                    changeState(State.FALL);
                } else if (input.isPressed(Keys.W)) {
                    changeState(State.CLIMB);
                } else if (input.isJustPressed(Keys.SPACE)) {
                    changeState(State.WALL_KICK);
                }
                break;
            case CLIMB:
                if (!collisionDetector.isTouchingWall()) {
                    changeState(State.FALL);
                } else if (input.isJustPressed(Keys.SPACE)) {
                    changeState(State.WALL_KICK);
                } else if (!input.isPressed(Keys.K) || !input.isPressed(Keys.W)) {
                    changeState(State.FALL);
                } else if (input.isPressed(Keys.K) && !input.isPressed(Keys.W)) {
                    changeState(State.ON_WALL);
                }
                break;
            case WALL_KICK:
                changeState(State.FALL);
                break;
        }
        State newState = currentState;
        isDifferentState = oldState != newState;
    }
}
