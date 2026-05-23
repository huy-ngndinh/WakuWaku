package com.oop.wakuwaku.State;
import com.badlogic.gdx.Input;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.world.GameWorld;

import java.util.function.IntUnaryOperator;

public class WallHanging extends PlayerState {

    public static final WallHanging INSTANCE = new WallHanging();

    private int direction;

    public void enter(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        gameWorld.getPlayer().setGravity(0);
        if (collisionDetector.isTouchingLeftWall()) {
            direction = 1;
        } else {
            direction = -1;
        }
    }

    public int getWallDirection() {
        return direction;
    }

    public void update(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if(input.isPressed(Input.Keys.J)){
            playerStateHandler.getCurrentState().exit(delta, playerStateHandler, input, collisionDetector, gameWorld);
            playerStateHandler.changeState(WallClimbOver.INSTANCE);
            playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);

        }
        // nếu ko cho leo qua tường thì cho rơi
        else if (input.isPressed(Input.Keys.SPACE)){
            System.out.println("Stopped hanging");
            playerStateHandler.getCurrentState().exit(delta, playerStateHandler, input, collisionDetector, gameWorld);
            playerStateHandler.changeState(Falling.INSTANCE);
            playerStateHandler.getCurrentState().enter(delta, playerStateHandler, input, collisionDetector, gameWorld);
        }
    }

    public void exit(float delta, PlayerStateHandler playerStateHandler, GameInput input, CollisionDetector collisionDetector, GameWorld gameWorld) {
        gameWorld.getPlayer().setGravity(1);
    }
}
