package com.oop.wakuwaku.System;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.wakuwaku.Transition.InTransition;
import com.oop.wakuwaku.Transition.OutTransition;
import com.oop.wakuwaku.world.Player;


/**
 * The class responsible for drawing the sprite and tiled map of the game.
 */
public class Render {
    private final FitViewport viewport;
    private final ScreenViewport transitionViewport;
    private final ScreenViewport uiViewport;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer mapRenderer;

    // Transition
    private final Texture transitionTexture;
    private final InTransition inTransition;
    private final OutTransition outTransition;

    public Render(TiledMap map) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);

        viewport = new FitViewport(30, 20, camera);

        transitionViewport = new ScreenViewport();
        transitionViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        uiViewport = new ScreenViewport();
        uiViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

        inTransition = new InTransition(transitionViewport);
        outTransition = new OutTransition(transitionViewport);
        transitionTexture = new Texture(Gdx.files.internal("asset_work/transition/transition.png"));

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

    public void drawTransition(float delta, boolean type) {
        transitionViewport.apply();
        batch.setProjectionMatrix(transitionViewport.getCamera().combined);
        float width = transitionViewport.getWorldWidth();
        float height = transitionViewport.getWorldHeight();

        float yPosition;
        if (!type) {
            inTransition.update(delta);
            yPosition = inTransition.getYPosition();
        } else {
            outTransition.update(delta);
            yPosition = outTransition.getYPosition();
        }

        batch.draw(transitionTexture, 0, yPosition, width, height);
    }

    public boolean isTransitionBegin(boolean type) {
        if (!type) {
            return inTransition.isTransitionBegin();
        } else {
            return outTransition.isTransitionBegin();
        }
    }

    public void setTransition(boolean type) {
        if (!type) {
            inTransition.setTransition();
        } else {
            outTransition.setTransition();
        }
    }

    public boolean isTransitionFinished(boolean type) {
        if (!type) {
            return inTransition.isTransitionFinished();
        } else {
            return outTransition.isTransitionFinished();
        }
    }

    public void drawUI(UserInterfaceHandler uihandler) {
        uiViewport.apply();
        batch.setProjectionMatrix(uiViewport.getCamera().combined);
        uihandler.drawSettingPanel(batch);
    }

    public void drawStage(UserInterfaceHandler uihandler) {
        uiViewport.apply();
        uihandler.getStage().draw();
    }

    /**
     * Update camera viewport
     * @param width The new width
     * @param height The new height
     */
    public void updateViewport(int width, int height){
        viewport.update(width, height, true);
        transitionViewport.update(width, height, true);
        uiViewport.update(width, height, false);
    }

    /**
     * Return an instance of <a href="https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/graphics/OrthographicCamera.java"><code>OrthographicCamera</code></a> used to render the game. Only one instance of the camera should exist and is managed by this class.
     * @return <code>OrthographicCamera</code>
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    public Viewport getUIViewport() {
        return uiViewport;
    }

    public SpriteBatch getSpriteBatch() {
        return batch;
    }
}
