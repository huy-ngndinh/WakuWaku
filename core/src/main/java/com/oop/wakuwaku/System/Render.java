package com.oop.wakuwaku.System;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
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
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);

        viewport = new FitViewport(30, 20, camera);

        batch = new SpriteBatch();

        mapRenderer = new OrthogonalTiledMapRenderer(map, Physics.UNIT);
        mapRenderer.setView(camera);

    }

    public void reset() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void drawPlayer(Player player, TextureRegion animationRegion) {
        float width = animationRegion.getRegionWidth() * Physics.UNIT * 1.0f;
        float height = animationRegion.getRegionHeight() * Physics.UNIT * 1.0f;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(animationRegion, player.getPosition().x - width / 2, player.getPosition().y - height / 2, width, height);
        batch.end();
    }

    /**
     * Draw the tiled map using <a href="https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/maps/tiled/renderers/OrthogonalTiledMapRenderer.java" ><code>OrthogonalTiledMapRenderer</code></a>.
     */
    public void draw(Player player){
        viewport.apply();
        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        camera.zoom = 0.6f;
        //camera.update();
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
