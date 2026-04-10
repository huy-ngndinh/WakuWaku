package com.oop.wakuwaku.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.oop.wakuwaku.Main;
import com.oop.wakuwaku.System.Physics;
import com.oop.wakuwaku.System.Render;
import com.oop.wakuwaku.input.GameInput;
import com.oop.wakuwaku.world.GameWorld;
/** First screen of the application. Displayed after the application is created. */
public class GameScreen extends ScreenAdapter {
    private Main game;

    public static final float TILE_PIXEL = 32f;
    public static final float UNIT = 1f / TILE_PIXEL; 

    
    // Box2d
    private Physics physics;

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
        // Handle user input here. This method is called every frame from render().
        if (input.isPressed(Input.Keys.A)) {
            gameworld.getPlayer().moveLeft();
        }
        if (input.isPressed(Input.Keys.D)) {
            gameworld.getPlayer().moveRight();
        }
        if (input.isPressed(Input.Keys.SPACE)) {
            gameworld.getPlayer().jump();
        }
        if(input.isPressed(Input.Keys.SHIFT_LEFT)){
            if(!gameworld.getPlayer().isDash()){
                gameworld.getPlayer().setDash();
            }
            if(gameworld.getPlayer().isDash())gameworld.getPlayer().dash(delta);
        }
    }

    private void logic(){
        
    }   

    private void draw(){

        render.draw();
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
