package com.oop.wakuwaku.State;
import com.badlogic.gdx.Input;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.world.GameWorld;

public class WallHanging extends PlayerState {

    public static final WallHanging INSTANCE = new WallHanging();

    private int direction;
    private boolean jumpRequest = false;

    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        
    }

    public int getWallDirection() {
        return direction;
    }
    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if(input.isPressed(Input.Keys.J)){
            playerStateHandler.changeState(WallClimbOver.INSTANCE);
            playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);
        }
        // nếu ko cho leo qua tường thì cho rơi
        else {
            playerStateHandler.changeState(Falling.INSTANCE);
            playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);
        }
    }

    public void exit() {

    }
}
