package com.oop.wakuwaku.System;

import com.badlogic.gdx.Input;
import com.oop.wakuwaku.input.GameInput;
import com.oop.wakuwaku.world.GameWorld;
import com.oop.wakuwaku.world.Player;

public class PlayerStateHandler {

    public enum State {
        IDLE,
        WALK,
        SPRINT,
        JUMP,
        WALL,
        WALL_CLIMB,
        WALL_KICK
    }

    private State currentState;

    public PlayerStateHandler() {
        currentState = State.IDLE;
    }

    public State getCurrentState() {
        return currentState;
    }

    private void changeState(State state) {
        currentState = state;
    }

    public void updateState(float delta, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        switch (currentState) {
            case IDLE:
                if (input.isPressed(Input.Keys.A)) {
                    gameWorld.getPlayer().setDirection(0);
                    changeState(State.WALK);
                }
                if (input.isPressed(Input.Keys.D)) {
                    gameWorld.getPlayer().setDirection(1);
                    changeState(State.WALK);
                }
                break;
            case WALK:
                if (input.isPressed(Input.Keys.SHIFT_LEFT)) changeState(State.SPRINT);
                else if (input.isPressed(Input.Keys.SPACE)) changeState(State.JUMP);
                else changeState(State.IDLE);
                break;
            case SPRINT:
                if (input.isPressed(Input.Keys.SPACE)) changeState(State.JUMP);
                else changeState(State.IDLE);
                break;
            case JUMP:
                if (collisionDetector.isTouchingGround()) changeState(State.IDLE);
                else if ((collisionDetector.isTouchingLeftWall() || collisionDetector.isTouchingRightWall()) && input.isPressed(Input.Keys.K)) changeState(State.WALL);
                break;
            case WALL:
                if (!input.isPressed(Input.Keys.K)) changeState(State.JUMP);
                else if (!input.isPressed(Input.Keys.W)) changeState(State.WALL);
                else if (input.isPressed(Input.Keys.W)) changeState(State.WALL_CLIMB);
                else if (input.isPressed(Input.Keys.SPACE)) changeState(State.WALL_KICK);
                break;
            case WALL_CLIMB:
                if (!input.isPressed(Input.Keys.K)) changeState(State.JUMP);
                else if (input.isPressed(Input.Keys.SPACE)) changeState(State.WALL_KICK);
                break;
            case WALL_KICK:
                changeState(State.JUMP);
        }
    }
}
