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
    private Texture bgTex, playTex, setTex, popupTex, closeTex;
    private ImageButton play, sett, closeBtn;
    private FitViewport viewport;
    private boolean showPopup = false;

    private Slider musicSlider, effectSlider;
    private Skin skin;

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

        skin = new Skin(Gdx.files.internal("cloud-form-ui.json"));

        bgTex = new Texture("background.jpg");
        playTex = new Texture("play.png");
        setTex  = new Texture("settings.png");
        popupTex = new Texture("settings_panel.png");
        closeTex = new Texture("close.png");

        play = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTex)));
        sett = new ImageButton(new TextureRegionDrawable(new TextureRegion(setTex)));
        closeBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(closeTex)));

        musicSlider = new Slider(0, 1, 0.1f, false, skin);
        musicSlider.setValue(0.5f);
        musicSlider.setSize(300, 200);

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Table menuButtons = new Table();
        menuButtons.right().bottom(); 
        menuButtons.add(play).size(410, 220).padLeft(1110).padBottom(-59);
        menuButtons.row();
        menuButtons.add(sett).size(410, 210).padLeft(1110);
        menuButtons.add(closeBtn).size(235, 125).padLeft(-3).padBottom(-30);

        root.add(menuButtons).expand().right().bottom().padRight(-3).padBottom(-30);

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
                System.out.println("Nut Settings da duoc bam!");
            }
        });

        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float vol = musicSlider.getValue();
                // Cập nhật âm lượng thực tế
                System.out.println("Music Volume: " + vol);
            }
        });

        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPopup = false;
                System.out.println("Nut Close da duoc bam!");
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
            float pW = popupTex.getWidth() / 2f;
            float pH = popupTex.getHeight() / 2f;
            float pX = (VIRTUAL_WIDTH - pW) / 2f;
            float pY = (VIRTUAL_HEIGHT - pH) / 2f;
            musicSlider.setVisible(true);
            game.batch.draw(popupTex, pX, pY, pW, pH);
            musicSlider.setPosition(pX + 500, pY + 320);
            closeBtn.setVisible(true);
            closeBtn.setPosition(pX + 481, pY + 210);
            stage.addActor(closeBtn);
            stage.addActor(musicSlider);
        }
        else {
            musicSlider.setVisible(false);
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
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); 
    }
}