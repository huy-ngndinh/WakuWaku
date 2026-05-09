package com.oop.wakuwaku.System;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.oop.wakuwaku.screen.GameScreen;
import com.oop.wakuwaku.world.Player;


/**
 * The class responsible for drawing the sprite and tiled map of the game.
 */
public class Render {
    private FitViewport viewport;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
//    private GameWorld gameWorld;

    public Render(TiledMap map) {
        viewport = new FitViewport(30, 20);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        mapRenderer = new OrthogonalTiledMapRenderer(map, GameScreen.UNIT);
        mapRenderer.setView(camera);

    }

    public void drawPlayer(Player player, TextureRegion animationRegion) {
        float width = animationRegion.getRegionWidth() * Player.UNIT * 2.0f;
        float height = animationRegion.getRegionHeight() * Player.UNIT * 2.0f;
        batch.begin();
        batch.draw(animationRegion, player.getPosition().x - width / 2, player.getPosition().y - height / 3, width, height);
        batch.end();
    }

    /**
     * Draw the tiled map using <a href="https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/maps/tiled/renderers/OrthogonalTiledMapRenderer.java" ><code>OrthogonalTiledMapRenderer</code></a>.
     */
    public void draw(){
        viewport.apply();
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    /**
     * Update camera viewport
     * @param width The new width
     * @param height The new height
     */
    public void updateViewport(int width, int height){
        viewport.update(width, height, true);
    }

    /**
     * Return an instance of <a href="https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/graphics/OrthographicCamera.java"><code>OrthographicCamera</code></a> used to render the game. Only one instance of the camera should exist and is managed by this class.
     * @return <code>OrthographicCamera</code>
     */
    public OrthographicCamera getCamera() {
        return camera;
    }
}
