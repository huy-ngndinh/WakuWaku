package com.oop.wakuwaku.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.oop.wakuwaku.Main;
import com.oop.wakuwaku.System.Physics;
import com.oop.wakuwaku.sprites.Map;
import com.oop.wakuwaku.sprites.Player;
/** First screen of the application. Displayed after the application is created. */
public class FirstScreen extends ScreenAdapter {
    private Main game;
    private Sprite characterSprite;
    private Array<Sprite> dropSprites;

    private TiledMap map;
    private FitViewport viewport;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private final float TILE_PIXEL = 32f;
    private final float UNIT = 1f / TILE_PIXEL; 

    
    // Box2d
    private Physics physics;
    private Player player;
    private Map mapObject;
    
    public FirstScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() { 

        physics = new Physics();
        player = new Player(physics.getWorld());

        viewport = new FitViewport(30, 20);
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);

        map = new TmxMapLoader().load("./raw_asset/ver01.tmx"); 
        mapRenderer = new OrthogonalTiledMapRenderer(map, UNIT);
        mapRenderer.setView(camera);
        mapObject = new Map(physics.getWorld(), map);
        
    }       

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        //if(width <= 0 || height <= 0) return;
        viewport.update(width, height, true);
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
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.getBody().applyForce(new Vector2(-10f,0), player.getBody().getWorldCenter(), true);
            // clamp max velocity
            Vector2 velocity = player.getBody().getLinearVelocity();
            if (velocity.x < -10f) player.getBody().setLinearVelocity(new Vector2(-10f, velocity.y));
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.getBody().applyForce(new Vector2(10f, 0), player.getBody().getWorldCenter(), true);
            // clamp max velocity
            Vector2 velocity = player.getBody().getLinearVelocity();
            if (velocity.x > 10f) player.getBody().setLinearVelocity(new Vector2(10f, velocity.y));
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            
            player.getBody().applyLinearImpulse(new Vector2(0, 10f), player.getBody().getWorldCenter(), true);
        }
    }

    private void logic(){
        
    }   

    private void draw(){
        viewport.apply();
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
        physics.simulate(Gdx.graphics.getDeltaTime());
        
        // Scale matrix for Box2D debug renderer (pixels to world units)
        physics.getDebugRenderer().render(physics.getWorld(), camera.combined);
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
