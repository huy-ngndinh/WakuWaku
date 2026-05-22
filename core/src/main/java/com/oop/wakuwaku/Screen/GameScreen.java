package com.oop.wakuwaku.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.Main;
import com.oop.wakuwaku.State.BeforeJump;
import com.oop.wakuwaku.State.Falling;
import com.oop.wakuwaku.State.Idle;
import com.oop.wakuwaku.State.Jump;
import com.oop.wakuwaku.State.PlayerState;
import com.oop.wakuwaku.State.Walking;
import com.oop.wakuwaku.State.WallAttach;
import com.oop.wakuwaku.State.WallClimb;
import com.oop.wakuwaku.State.WallHanging;
import com.oop.wakuwaku.State.WallKick;
import com.oop.wakuwaku.System.AnimationHandler;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.Physics;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.System.Render;
import com.oop.wakuwaku.world.GameWorld;
import com.oop.wakuwaku.world.Player;

/** First screen of the application.*/
public class GameScreen extends ScreenAdapter {
    private Main game;

    // Box2d
    private Physics physics;
    private CollisionDetector collisionDetector;
    private PlayerStateHandler playerStateHandler;

    // Animation
    private AnimationHandler animationHandler;

    //gameinput
    private GameInput input;

    // Game world (Map, Player)
    private GameWorld gameworld;

    //render
    private Render render;

    public GameScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {

        physics = new Physics();
        collisionDetector = new CollisionDetector();
        physics.getWorld().setContactListener(collisionDetector);
        animationHandler = new AnimationHandler();
        playerStateHandler = new PlayerStateHandler(this.animationHandler);
        gameworld = new GameWorld(physics.getWorld());
        render = new Render(gameworld.getMap().getTiledMap());
        input = new GameInput();

        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        render.updateViewport(width, height);
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void render(float delta) {
        logic(delta);
//        if (input.isPressed(Input.Keys.SPACE)) {
//            GameInput.counter++;
//            System.out.println(GameInput.counter);
//        }
        //System.out.println(playerStateHandler.getCurrentState().getClass().getSimpleName());
        // if(playerStateHandler.getCurrentState() instanceof Jump) {
        //     System.out.println(((Jump) playerStateHandler.getCurrentState()).isJumpRequest());
        // }
        
        // System.out.println(gameworld.getPlayer().getVelocity().y);  
        input.update(delta);
        draw(delta);
    }

    private void logic(float delta) {
        // WF : getcurState -> actor thực hiện action -> update

        PlayerState playerState = playerStateHandler.getCurrentState();
        Player player = gameworld.getPlayer();

        if (playerState instanceof Idle) {
            player.stop();
        } else if (playerState instanceof Walking) {
            int direction = player.getDirection();
            if (direction == 0) player.moveRight();
            else player.moveLeft();
        } else if (playerState instanceof Falling) {
            player.fallDown();
        } else if (playerState instanceof Jump) {
            int direction = ((Jump) playerState).getDirection();
            boolean jumpRequest = ((Jump) playerState).isJumpRequest();
            if (jumpRequest) {
                player.jump(input.getHoldTime());
                ((Jump) playerState).turnOffJumpRequest();
            }
        } else if (playerState instanceof WallAttach) {
            player.slide(); // trượt tường
        } else if (playerState instanceof WallClimb) {
            player.climb();
        } else if (playerState instanceof WallKick) {
            if (((WallKick) playerState).isJumpRequest()) {
                player.wall_kick(((WallKick) playerState).getWallDirection());
                ((WallKick) playerState).turnOffJumpRequest();
            }
        }else if (playerState instanceof BeforeJump) {
            System.out.println("In before jump");
        }
        else if (playerState instanceof WallHanging) {
            player.hang();
        }

        playerStateHandler.updateState(delta, input, collisionDetector, gameworld);
    }

    private void draw(float delta){
        // reset
        render.reset();
        // draw map
        render.draw(gameworld.getPlayer());
        // draw player/animation
        TextureRegion animationRegion = animationHandler.getCurrentAnimationFrame(delta, gameworld.getPlayer(), playerStateHandler);
        render.drawPlayer(gameworld.getPlayer(), animationRegion);
        // update physics
        collisionDetector.resetContact();
        physics.simulate(Gdx.graphics.getDeltaTime());
        physics.getDebugRenderer().render(physics.getWorld(), render.getCamera().combined);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

    }
}
