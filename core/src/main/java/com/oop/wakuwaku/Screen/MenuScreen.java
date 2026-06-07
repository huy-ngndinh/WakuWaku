package com.oop.wakuwaku.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.oop.wakuwaku.Main;
import com.oop.wakuwaku.Input.GameButton;
import com.oop.wakuwaku.Input.SettingsPanel;
import com.oop.wakuwaku.Transition.InTransition;
import com.oop.wakuwaku.Transition.OutTransition;

public class MenuScreen extends ScreenAdapter {
    private static boolean FIRST_TIME = true;
    private final Main game;
    private final Stage stage;
    private final Texture bgTex, settingsTex, playTex;
    private final GameButton settingsButton, playButton;
    private final SettingsPanel settingsPanel;
    private final FitViewport viewport;
    private float stateTime = 0f;
    private Animation<TextureRegion> bgAnimation;
    private final float VIRTUAL_WIDTH = 1080;
    private final float VIRTUAL_HEIGHT = 720;
    // Music
    Music music;
    // Transition
    private ScreenViewport transitionViewport;
    private final Texture transitionTexture;
    private final InTransition inTransition;
    private final OutTransition outTransition;
    private final SpriteBatch batch;

    public MenuScreen(Main game){
        this.game = game;
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        stage = new Stage(viewport);
        bgTex = new Texture("Buttons/startscreen.png");
        settingsTex = new Texture("Buttons/Settings.png");
        playTex = new Texture("Buttons/start.png");
        settingsButton = new GameButton(settingsTex, settingsTex, 350, 105, 243, 36);
        playButton = new GameButton(playTex, playTex, 360, 165, 153, 36);
        settingsPanel = new SettingsPanel(game, stage, new Texture("Buttons/settings_panel.png"), new Texture("Buttons/Paw.png"),
                new Texture("Buttons/Bar.png"), new Texture("Buttons/Close.png"), new Texture("Buttons/Close1.png"),
                new Texture("Buttons/Exit.png"), new Texture("Buttons/Exit1.png"));
        // Music
        music = Gdx.audio.newMusic(Gdx.files.internal("audio/menu.mp3"));
        music.setLooping(true);
        music.setVolume(settingsPanel.getMusicVolume());
        music.play();
        // transition
        transitionViewport = new ScreenViewport();
        transitionViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        inTransition = new InTransition(transitionViewport);
        outTransition = new OutTransition(transitionViewport);
        transitionTexture = new Texture(Gdx.files.internal("transition/transition.png"));
        if (!FIRST_TIME) inTransition.setTransition();
        else FIRST_TIME = false;
        batch = new SpriteBatch();
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
//                game.setScreen(new GameScreen(game));
                outTransition.setTransition();
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

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        if (currentFrame != null) {
            batch.draw(currentFrame, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        }
        if(settingsPanel.isVisible()) {
            playButton.setVisible(false);
            settingsButton.setVisible(false);
        }
        else {
            playButton.setVisible(true);
            settingsButton.setVisible(true);
        }
        batch.end();

        batch.begin();
        settingsPanel.draw(batch);
        stage.draw();
        batch.end();

        // Music
        music.setVolume(settingsPanel.getMusicVolume());

        // Transition
        if (inTransition.isTransitionBegin() && !inTransition.isTransitionFinished()) {
            batch.begin();
            drawTransition(delta, false);
            batch.end();
        }

        if (outTransition.isTransitionBegin() && !outTransition.isTransitionFinished()) {
            batch.begin();
            drawTransition(delta, true);
            batch.end();
        }

        if (outTransition.isTransitionFinished()) game.setScreen(new GameScreen(game));
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

    @Override
    public void dispose () {
        stage.dispose();
        bgTex.dispose();
        settingsTex.dispose();
        playTex.dispose();
        GameButton.dispose();
    }

    @Override
    public void hide() {
        music.stop();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
