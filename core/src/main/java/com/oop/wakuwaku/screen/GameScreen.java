package com.oop.wakuwaku.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.oop.wakuwaku.Main;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.Physics;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.System.Render;
import com.oop.wakuwaku.input.GameInput;
import com.oop.wakuwaku.world.GameWorld;

import java.util.Vector;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen extends ScreenAdapter {
    private Main game;

    public static final float TILE_PIXEL = 32f;
    public static final float UNIT = 1f / TILE_PIXEL;


    // Box2d
    private Physics physics;
    private CollisionDetector collisionDetector;
    private PlayerStateHandler playerStateHandler;

    //gameinput
    private GameInput input;
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
        playerStateHandler = new PlayerStateHandler();
        gameworld = new GameWorld(physics.getWorld());
        render = new Render(gameworld.getMap().getTiledMap());
        input = new GameInput();

    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        //if(width <= 0 || height <= 0) return;
        render.updateViewport(width, height);
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void render(float delta) {
        input(delta);
        // logic();
        draw();
    }

    private void input(float delta) {
        playerStateHandler.updateState(delta, input, collisionDetector, gameworld);
        System.out.println(playerStateHandler.getCurrentState());
        PlayerStateHandler.State playerState = playerStateHandler.getCurrentState();
        switch (playerState) {
            case PlayerStateHandler.State.IDLE:
                gameworld.getPlayer().resetJumpFlag();
                gameworld.getPlayer().setGravity(1f);
                break;
            case PlayerStateHandler.State.WALK:
                if (gameworld.getPlayer().getDirection() == 0) gameworld.getPlayer().moveLeft();
                else gameworld.getPlayer().moveRight();
                break;
            case PlayerStateHandler.State.JUMP:
                gameworld.getPlayer().setGravity(1f);
                gameworld.getPlayer().jump(new Vector2(0, 3f));
                break;
            case PlayerStateHandler.State.WALL:
                gameworld.getPlayer().setGravity(0.2f);
                if (collisionDetector.isTouchingLeftWall()) gameworld.getPlayer().stickLeft();
                else gameworld.getPlayer().stickRight();
                break;
            case PlayerStateHandler.State.WALL_CLIMB:
                gameworld.getPlayer().setGravity(0.2f);
                gameworld.getPlayer().moveUp();
                break;
            case PlayerStateHandler.State.WALL_KICK:
                gameworld.getPlayer().setGravity(1f);
                gameworld.getPlayer().wall_kick();
                break;

        }

    }

    private void logic(){

    }

    private void draw(){

        render.draw();
        collisionDetector.resetCollision();
        physics.simulate(Gdx.graphics.getDeltaTime());
        // Scale matrix for Box2D debug renderer (pixels to world units)
        physics.getDebugRenderer().render(physics.getWorld(), render.getCamera().combined);
        // float worldWidth = viewport.getWorldWidth();
        // float worldHeight = viewport.getWorldHeight();

        // game.batch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight); // draw the background


        // game.batch.end();
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
