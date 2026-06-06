package com.oop.wakuwaku.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.oop.wakuwaku.Main;
import com.oop.wakuwaku.Input.GameButton;
import com.oop.wakuwaku.Input.SettingsPanel;

public class MenuScreen extends ScreenAdapter {
    private final Main game;
    private final Stage stage;
    private final Texture bgTex, settingsTex, playTex;
    private final GameButton settingsButton, playButton;
    private final SettingsPanel settingsPanel;
    private final FitViewport viewport;
    private float stateTime = 0f;
    private Animation<TextureRegion> bgAnimation;
    private final float VIRTUAL_WIDTH = 1280;
    private final float VIRTUAL_HEIGHT = 720;

    public MenuScreen(Main game){
        this.game = game;
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        stage = new Stage(viewport);
        bgTex = new Texture("Buttons/startscreen.png");
        settingsTex = new Texture("Buttons/Settings.png");
        playTex = new Texture("Buttons/start.png");
        settingsButton = new GameButton(settingsTex, settingsTex, 420, 105, 243, 36);
        playButton = new GameButton(playTex, playTex, 430, 165, 153, 36);
        settingsPanel = new SettingsPanel(game, stage, new Texture("Buttons/settings_panel.png"), new Texture("Buttons/Paw.png"),
                new Texture("Buttons/Bar.png"), new Texture("Buttons/Close.png"), new Texture("Buttons/Close1.png"),
                new Texture("Buttons/Exit.png"), new Texture("Buttons/Exit1.png"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        TextureRegion[][] tmp = TextureRegion.split(bgTex, bgTex.getWidth() / 2, bgTex.getHeight());
        TextureRegion[] bgFrames = new TextureRegion[2];
        bgFrames[0] = tmp[0][0];
        bgFrames[1] = tmp[0][1];
        bgAnimation = new Animation<TextureRegion>(0.5f, bgFrames);
        bgAnimation.setPlayMode(Animation.PlayMode.LOOP);

        stage.addActor(playButton);
        stage.addActor(settingsButton);

        settingsButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                settingsPanel.setVisible(true);
            }
        });

        playButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        settingsPanel.update(viewport);
        stage.act(delta);

        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = bgAnimation.getKeyFrame(stateTime);

        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        game.batch.begin();
        if (currentFrame != null) {
            game.batch.draw(currentFrame, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        }
        if(settingsPanel.isVisible()) {
            playButton.setVisible(false);
            settingsButton.setVisible(false);
        }
        else {
            playButton.setVisible(true);
            settingsButton.setVisible(true);
        }
        game.batch.end();
        game.batch.begin();
        settingsPanel.draw(game.batch);
        stage.draw();
        game.batch.end();
    }

    @Override
    public void dispose () {
        stage.dispose();
        bgTex.dispose();
        settingsTex.dispose();
        playTex.dispose();
        GameButton.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
