package com.oop.wakuwaku.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.oop.wakuwaku.Main;

public class MenuScreen extends ScreenAdapter {
    private Main game;
    private Stage stage;
    private Texture bgTex, playTex, setTex, popupTex, closeTex, catHeadTex;
    private ImageButton play, sett, closeBtn;
    private float musicVolume = 0.5f, sliderMinX = 0f, sliderMaxX = 1f, sliderY;
    private float catWith = 100, catHeight = 100, catX, catY;
    private FitViewport viewport;
    private boolean showPopup = false;

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

        bgTex = new Texture("background.jpg");
        playTex = new Texture("play.png");
        setTex  = new Texture("settings.png");
        popupTex = new Texture("settings_panel.png");
        closeTex = new Texture("close.png");
        catHeadTex = new Texture("cat_head.png");

        play = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTex)));
        sett = new ImageButton(new TextureRegionDrawable(new TextureRegion(setTex)));
        closeBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(closeTex)));

        // size
        play.setSize(410, 220);
        play.setPosition(870, 120);
        sett.setSize(410, 210);
        sett.setPosition(870, -20);
        closeBtn.setSize(235, 125);
        closeBtn.setPosition(521, 235);

        stage.addActor(play);
        stage.addActor(sett);
        stage.addActor(closeBtn);

        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!showPopup)   game.setScreen(new GameScreen(game));
            }
        });

        sett.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPopup = true;
            }
        });

        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPopup = false;
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(bgTex, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        if (showPopup) {
            game.batch.draw(popupTex, 40, 25, 1200, 670);
            closeBtn.setVisible(true);
        }
        else {
            closeBtn.setVisible(false);
        }
        game.batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose () {
        stage.dispose();
        bgTex.dispose();
        playTex.dispose();
        setTex.dispose();
        popupTex.dispose();
        catHeadTex.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); 
    }
}