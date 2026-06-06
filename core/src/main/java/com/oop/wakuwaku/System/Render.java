package com.oop.wakuwaku.System;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
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
    private final FitViewport viewport;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer mapRenderer;

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

    public void beginRender() {
        batch.begin();
    }

    public void endRender() {
        batch.end();
    }

    public void drawPlayer(Player player, TextureRegion animationRegion) {
        float width = animationRegion.getRegionWidth() * Physics.UNIT * 1.0f;
        float height = animationRegion.getRegionHeight() * Physics.UNIT * 1.0f;

        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.draw(animationRegion, player.getPosition().x - width / 2, player.getPosition().y - height / 2, width, height);
    }

    public void drawIndicator(Player player, float spaceTime, float direction) {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);

        int size = 16;
        Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        // fill interior with blue
        if(spaceTime < 30) pixmap.setColor(Color.RED);
        else if(spaceTime < 90) pixmap.setColor(Color.YELLOW);
        else pixmap.setColor(Color.GREEN);
        pixmap.fill();
        // draw black border on top
        pixmap.setColor(Color.BLACK);
        pixmap.drawRectangle(0, 0, size, size);
        Texture pixel = new Texture(pixmap);
        pixmap.dispose();
        if (direction == 0) batch.draw(pixel, player.getPosition().x, player.getPosition().y + player.getHeight() / 3f, 0.3f, 0.3f);
        else batch.draw(pixel, player.getPosition().x + direction * player.getWidth() / 2f, player.getPosition().y, 0.3f, 0.3f);
    }

    /**
     * Draw the tiled map using <a href="https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/maps/tiled/renderers/OrthogonalTiledMapRenderer.java" ><code>OrthogonalTiledMapRenderer</code></a>.
     */
    public void drawMap(Player player){
        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        camera.zoom = 0.6f;
        camera.update();
        viewport.apply();
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
