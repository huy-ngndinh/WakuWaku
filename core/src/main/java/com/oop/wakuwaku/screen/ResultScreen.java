package com.oop.wakuwaku.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.oop.wakuwaku.Main;

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

        Texture menu_up = new Texture("ng.png");
        Texture menu_down = new Texture("pixel kit UI copy.png");
        ImageButton menuButton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(menu_up)),
            new TextureRegionDrawable(new TextureRegion(menu_down))
        );

        Texture fs_up = new Texture("stop1.png");
        Texture fs_down = new Texture("stop2.png");
        ImageButton Out = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(fs_up)),
            new TextureRegionDrawable(new TextureRegion(fs_down))
        );

        Texture game_end = new Texture("pixel kit UI.png");
        ImageButton Endbutton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(game_end))
        );

        Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                game.setScreen(new GameScreen(game));
            }
        });

        Out.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                game.setScreen(new MenuScreen(game));
            }
        });


        table.add(Endbutton)
            .width(200)
            .height(80)
            .pad(10);

        table.row();

        table.add(menuButton)
            .width(200)
            .height(80)
            .pad(10);

        table.row();

        table.add(Out)
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
