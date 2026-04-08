package com.oop.wakuwaku.System;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.oop.wakuwaku.screen.GameScreen;

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

        mapRenderer = new OrthogonalTiledMapRenderer(map, GameScreen.UNIT);
        mapRenderer.setView(camera);

    }
    public void draw(){
        viewport.apply();
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
    }
    public void updateViewport(int width, int height){
        viewport.update(width, height, true);
    }
    public OrthographicCamera getCamera() {
        return camera;
    }
}
