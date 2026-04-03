package com.oop.wakuwaku.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.oop.wakuwaku.Main;
import com.oop.wakuwaku.GameAssetManager;

public class MenuScreen extends ScreenAdapter {
    private Main game;
    private Skin skin;
    private Stage stage;

    public MenuScreen(Main game){
        this.game = game;
    }

    @Override
    public void show(){
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Layout
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Texture up = GameAssetManager.getTexture(GameAssetManager.START_BTN);
        Texture down = GameAssetManager.getTexture(GameAssetManager.START_BTN_2);

        ImageButton button = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(up)),
            new TextureRegionDrawable(new TextureRegion(down))
        );

        table.add(button).pad(20);

        // Sound
        Sound clickSound = GameAssetManager.getSound(GameAssetManager.CLICK_SOUND);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                game.setScreen(new FirstScreen(game));
            }
        });

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("you just clicked me >.<");
            }
        });
    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(Color.WHITE);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose () {
        skin.dispose();
        stage.dispose();
    }
}
