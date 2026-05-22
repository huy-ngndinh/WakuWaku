package com.oop.wakuwaku.State;

import com.badlogic.gdx.Input;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.world.GameWorld;

public class Jump extends PlayerState {

    public static final Jump INSTANCE = new Jump();

    private boolean jumpRequest = false;
    private int direction;
    // private boolean justJumped = false;

    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) { 
        jumpRequest = true;
        // justJumped = true;
    }

    public boolean isJumpRequest() { return jumpRequest;}

    public void turnOffJumpRequest() { jumpRequest = false; }

    public int getDirection() { return this.direction; }

    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        float y_velocity = gameWorld.getPlayer().getVelocity().y;
        
        // Skip falling check on the first frame after jump to let impulse apply properly
        // if (justJumped) {
        //     justJumped = false;
        //     return;
        // }    
        
        if (collisionDetector.isTouchingGround() && y_velocity <= 0) {
            playerStateHandler.changeState(Idle.INSTANCE);
            // System.out.println("Case 1");
        } else if (y_velocity < 0) {
            playerStateHandler.changeState(Falling.INSTANCE);
            playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);
            // System.out.println("Case 2");
        } else if (input.isPressed(Input.Keys.K) && collisionDetector.isTouchingWall()) {
            playerStateHandler.changeState(WallAttach.INSTANCE);
            playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);
            // System.out.println("Case 3");
        }
    }

    public void exit() {}
}
