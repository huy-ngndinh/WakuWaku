package com.oop.wakuwaku.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.oop.wakuwaku.Main;
import com.oop.wakuwaku.GameAssetManager;

public class ResultScreen extends ScreenAdapter {

    private Main game;
    private Stage stage;
    private Skin skin;

    public ResultScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Texture menu_up = GameAssetManager.getTexture(GameAssetManager.NG_LOGO);
        Texture menu_down = GameAssetManager.getTexture(GameAssetManager.PIXEL_KIT);

        ImageButton menuButton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(menu_up)),
            new TextureRegionDrawable(new TextureRegion(menu_down))
        );

        Texture fs_up = GameAssetManager.getTexture(GameAssetManager.STOP_BTN_1);
        Texture fs_down = GameAssetManager.getTexture(GameAssetManager.STOP_BTN_2);

        ImageButton outButton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(fs_up)),
            new TextureRegionDrawable(new TextureRegion(fs_down))
        );

        Texture game_end = GameAssetManager.getTexture(GameAssetManager.PIXEL_KIT);

        ImageButton endButton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(game_end))
        );

        Sound clickSound = GameAssetManager.getSound(GameAssetManager.CLICK_SOUND);

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                game.setScreen(new FirstScreen(game));
            }
        });

        outButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                game.setScreen(new MenuScreen(game));
            }
        });

        // Layout
        table.add(endButton)
            .width(200)
            .height(80)
            .pad(10);

        table.row();

        table.add(menuButton)
            .width(200)
            .height(80)
            .pad(10);

        table.row();

        table.add(outButton)
            .width(200)
            .height(80)
            .pad(10);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
