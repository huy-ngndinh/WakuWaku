package com.oop.wakuwaku.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.oop.wakuwaku.Main;
import com.oop.wakuwaku.System.CollisionDetector;
import com.oop.wakuwaku.System.Physics;
import com.oop.wakuwaku.System.PlayerStateHandler;
import com.oop.wakuwaku.System.Render;
import com.oop.wakuwaku.input.GameInput;
import com.oop.wakuwaku.world.GameWorld;
/** First screen of the application.*/
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
        render.updateViewport(width, height);
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void render(float delta) {
        input(delta);

        // logic();
        System.out.println(playerStateHandler.getCurrentState());

        draw();
//        System.out.println(collisionDetector.isTouchingLeftWall() + " " + collisionDetector.isTouchingRightWall());
    }

    private void input(float delta) {
        //WF : update -> getcurState -> actor thực hiện action
        playerStateHandler.updateState(delta, input, collisionDetector, gameworld);
        PlayerStateHandler.State playerState = playerStateHandler.getCurrentState();
        switch (playerState) {
            // thực hiện hành động thực tế của actor
            case PlayerStateHandler.State.STAND:
                gameworld.getPlayer().stop();
                break;
            case PlayerStateHandler.State.WALK:
                if(gameworld.getPlayer().getDirection() == 1) gameworld.getPlayer().moveRight();
                else gameworld.getPlayer().moveLeft();
                break;
            case PlayerStateHandler.State.JUMP:
                gameworld.getPlayer().jump();
                break;
            case PlayerStateHandler.State.FALL:
                gameworld.getPlayer().fallDown();
                break;
            case PlayerStateHandler.State.ON_WALL:
                gameworld.getPlayer().stop();
                break;
            case PlayerStateHandler.State.DASH:
                gameworld.getPlayer().dash(delta);
                break;
            case PlayerStateHandler.State.CLIMB:
                gameworld.getPlayer().climb();
                break;
            case PlayerStateHandler.State.WALL_KICK:
                int direction = collisionDetector.isTouchingLeftWall() ? 0 : 1;
                gameworld.getPlayer().wall_kick(direction);
                break;
        }
    }

    private void logic(){

    }

    private void draw(){
        // draw sprites
        render.draw();
        // update physics
        collisionDetector.resetWallContact();
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
