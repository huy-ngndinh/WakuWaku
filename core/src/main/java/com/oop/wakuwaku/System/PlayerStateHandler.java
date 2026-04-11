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
    public PlayerStateHandler() {
        currentState = State.STAND;
    }

    public State getCurrentState() {
        return currentState;
    }

    private void changeState(State state) {
        currentState = state;
    }

    private float cooldownDash = 0f;
    public void updateState(float delta, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
    cooldownDash = Math.max(0f,cooldownDash - delta);
    switch (currentState) {
        case STAND:
            if(input.isPressed(Keys.D)){
                gameWorld.getPlayer().setDirection(1);
                changeState(State.WALK);
            }
            if(input.isPressed(Keys.A)){
                gameWorld.getPlayer().setDirection(0);
                changeState(State.WALK);
            }
            if(input.isPressed(Keys.SPACE)){
                changeState(State.JUMP);
            }
            break;

        case WALK:
            if (input.isPressed(Keys.SPACE)) {
                changeState(State.JUMP);
                break;
            }
            if(!collisionDetector.isTouchingGround()){
                changeState(State.FALL);
                break;
            }
            if(input.isPressed(Keys.SHIFT_LEFT) && cooldownDash == 0 && gameWorld.getPlayer().getBody().getLinearVelocity().x != 0){
                changeState(State.DASH);
                break;
            }
            // Nếu vẫn giữ A hoặc D thì tiếp tục WALK và cập nhật hướng
            if (input.isPressed(Keys.A)) {
                gameWorld.getPlayer().setDirection(0);
            } 
            else if (input.isPressed(Keys.D)) {
                gameWorld.getPlayer().setDirection(1);
            }
            else {
                // Không còn phím di chuyển -> về STAND
                changeState(State.STAND);
            }
            break;
        case JUMP:
            // xử lý JUMP...
            changeState(State.FALL);
            break;
        case FALL:
            if(collisionDetector.isTouchingGround()){
                changeState(State.STAND);
                break;
            }
            if(input.isPressed(Keys.SHIFT_LEFT) && cooldownDash == 0 && gameWorld.getPlayer().getBody().getLinearVelocity().x != 0){
                changeState(State.DASH);
                break;
            }
            break;
        case DASH:
            if(gameWorld.getPlayer().isDash()){
                changeState(State.DASH);
            }
            else
            {
                gameWorld.getPlayer().getBody().setLinearVelocity(new Vector2(0,0));
                gameWorld.getPlayer().resetDashTimer();
                cooldownDash = Gdx.graphics.getDeltaTime() * 240;
                if(collisionDetector.isTouchingGround()){
                    changeState(State.STAND);
                }
                else {
                    changeState(State.FALL);
                }
            }
            break;
    }
}
}
