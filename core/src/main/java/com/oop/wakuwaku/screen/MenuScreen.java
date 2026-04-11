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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.oop.wakuwaku.Main;

public class MenuScreen extends ScreenAdapter {
    private Main game;
    private Stage stage;
    private Texture bgTex, playTex, setTex;
    private ImageButton play, sett;

    public MenuScreen(Main game){
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage); 

        bgTex = new Texture("background.jpg");
        playTex = new Texture("play.png");
        setTex  = new Texture("settings.png");

        play = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTex)));
        sett = new ImageButton(new TextureRegionDrawable(new TextureRegion(setTex)));

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Table menuButtons = new Table();
        menuButtons.right().bottom(); // Đẩy sang phải-dưới
        menuButtons.add(play).size(280, 115).padBottom(-5);
        menuButtons.row();
        menuButtons.add(sett).size(280, 110);

        root.add(menuButtons).expand().right().bottom().padRight(-10).padBottom(-5);

        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game)); 
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        if (game.batch != null) {
            game.batch.begin();
            game.batch.draw(bgTex, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            game.batch.end();
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose () {
        stage.dispose();
        bgTex.dispose();
        playTex.dispose();
        setTex.dispose();
    }
}