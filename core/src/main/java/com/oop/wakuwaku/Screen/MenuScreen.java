package com.oop.wakuwaku.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.oop.wakuwaku.Main;
import com.oop.wakuwaku.System.Game_Button;

public class MenuScreen extends ScreenAdapter {
    private final Main game;
    private Stage stage;
    private Texture bgTex;
    private FitViewport viewport;
    private float stateTime = 0f;
    private Animation<TextureRegion> bgAnimation;
    private Game_Button PlayButton, SettingsButton, Exit_Button;

    private final float VIRTUAL_WIDTH = 1280;
    private final float VIRTUAL_HEIGHT = 720;

    public MenuScreen(Main game){
        this.game = game;
    }

    @Override
    public void show() {
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        bgTex = new Texture("Buttons/startscreen.png");
        PlayButton = new Game_Button(game, "Buttons/start.png", "Buttons/start.png", 430, 165, 153, 36);
        SettingsButton = new Game_Button(game, "Buttons/Settings.png", "Buttons/Settings.png", 420, 105, 243, 36);
        Exit_Button = new Game_Button(game, "Buttons/Exit.png", "Buttons/Exit1.png", 568, 250, 144, 48);

        TextureRegion[][] tmp = TextureRegion.split(bgTex, bgTex.getWidth() / 2, bgTex.getHeight());
        TextureRegion[] bgFrames = new TextureRegion[2];
        bgFrames[0] = tmp[0][0];
        bgFrames[1] = tmp[0][1];
        bgAnimation = new Animation<TextureRegion>(0.5f, bgFrames);
        bgAnimation.setPlayMode(Animation.PlayMode.LOOP);

        stage.addActor(PlayButton);
        stage.addActor(SettingsButton);
        stage.addActor(Exit_Button);

        PlayButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.showPopup) return;
                game.setScreen(new GameScreen(game));
            }
        });

        SettingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.showPopup) return;
                game.showPopup = true;
            }
        });

        Exit_Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!game.showPopup) return;
                game.showPopup = false;
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = bgAnimation.getKeyFrame(stateTime);

        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(currentFrame, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.end();

        if (game.showPopup) {
            PlayButton.setVisible(false);
            SettingsButton.setVisible(false);
            Exit_Button.setVisible(true);
        } else {
            PlayButton.setVisible(true);
            SettingsButton.setVisible(true);
            Exit_Button.setVisible(false);
        }
        stage.act(delta);
        game.batch.begin();
        game.settingsPopup.updateAndDraw(viewport);
        stage.draw();
        game.batch.end();
    }

    @Override
    public void dispose () {
        stage.dispose();
        bgTex.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
