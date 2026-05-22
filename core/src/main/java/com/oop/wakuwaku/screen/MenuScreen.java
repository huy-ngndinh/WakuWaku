package com.oop.wakuwaku.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
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
    private float catX_music, catY_music, catX_effect, catY_effect, musicVolume = 0.5f, effectVolume = 0.5f;
    private int sliderMinX = 625, sliderMaxX = 825, sliderY_effect = 450, sliderY_music = 400, catWith = 72, catHeight = 65;
    private FitViewport viewport;
    private boolean showPopup = false, isTouching_Music = false, isTouching_Effect = false;
    private Music music;
    private Sound clickSound;

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

        music = Gdx.audio.newMusic(Gdx.files.internal("minecraft.mp3"));
        music.setLooping(true);
        music.setVolume(musicVolume);
        music.play();
        clickSound = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));

        bgTex = new Texture("background.jpg");
        playTex = new Texture("play.png");
        setTex  = new Texture("settings.png");
        popupTex = new Texture("settings_panel.png");
        closeTex = new Texture("close.png");
        catHeadTex = new Texture("cat_head.png");

        play = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTex)));
        sett = new ImageButton(new TextureRegionDrawable(new TextureRegion(setTex)));
        closeBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(closeTex)));

        // size and position
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
                clickSound.play();
                if(!showPopup)   game.setScreen(new GameScreen(game));
            }
        });

        sett.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                showPopup = true;
            }
        });

        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                showPopup = false;
            }
        });
    }

    private float input(boolean isTouching, float Vol, int sliderMinX, int sliderMaxX, int sliderY) {
        if(!showPopup) {
            isTouching = false;
            return Vol;
        }
        if(Gdx.input.isTouched()) {
            game.touchPos.set(Gdx.input.getX(),Gdx.input.getY());
            viewport.unproject(game.touchPos);

            if(isTouching) {
                    float x = Math.min(sliderMaxX, game.touchPos.x);
                    x = Math.max(sliderMinX, x);
                    Vol = (x - sliderMinX) / (sliderMaxX - sliderMinX);
                    Vol = Math.round(Vol * 100.0f) / 100.0f;
                    System.out.println(Vol);
                    return Vol;
            }
            else {
                if(game.touchPos.x >= sliderMinX && game.touchPos.x <= sliderMaxX && game.touchPos.y >= sliderY - 20 && game.touchPos.y <= sliderY + 20) {
                    isTouching = true;
                    Vol = (game.touchPos.x - sliderMinX) / (sliderMaxX - sliderMinX);
                    Vol = Math.round(Vol * 100.0f) / 100.0f;
                    System.out.println(Vol);
                    return Vol;
                }
            }
        }
        else    isTouching = false;
        return Vol;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(bgTex, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        if (showPopup) {
            musicVolume = input(isTouching_Music, musicVolume, sliderMinX, sliderMaxX, sliderY_music);
            catX_music = sliderMinX + musicVolume * (sliderMaxX - sliderMinX) - catWith / 2;
            catY_music = sliderY_music - catHeight / 2;
            music.setVolume(musicVolume);

            effectVolume = input(isTouching_Effect, effectVolume, sliderMinX, sliderMaxX, sliderY_effect);
            catX_effect = sliderMinX + effectVolume * (sliderMaxX - sliderMinX) - catWith / 2;
            catY_effect = sliderY_effect - catHeight / 2;
            
            closeBtn.setVisible(true);
            game.batch.draw(popupTex, 40, 25, 1200, 670);
            game.batch.draw(catHeadTex, catX_music, catY_music, catWith, catHeight);
            game.batch.draw(catHeadTex, catX_effect, catY_effect, catWith, catHeight);
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
        music.dispose();
        catHeadTex.dispose();
        clickSound.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); 
    }
}