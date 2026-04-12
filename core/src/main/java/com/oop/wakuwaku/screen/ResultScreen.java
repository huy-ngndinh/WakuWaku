package com.oop.wakuwaku.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.oop.wakuwaku.FactManager;
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

        Image bg = new Image(new Texture("Pixelart.jpeg"));
        bg.setFillParent(true);


        Stack rootStack = new Stack();
        rootStack.setFillParent(true);

        Table root = new Table();
        root.setFillParent(true);

        // Stack for board & Mission + Text

        Texture menu_up = new Texture("continue.png");
        Texture menu_down = new Texture("continue.png");
        Texture boardTexture = new Texture("board.png");
        Label factText = new Label(FactManager.getFact(1),skin);
        factText.setWrap(true);
        factText.setAlignment(Align.center);

        Image board = new Image(boardTexture);

        Table missionTable = new Table();
        missionTable.center();
        missionTable.add(factText).center().width(200).pad(20);
        //missionTable.setFillParent(true);

        Stack stack = new Stack();
        stack.add(board);
        stack.add(missionTable);

        ImageButton menuButton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(menu_up)),
            new TextureRegionDrawable(new TextureRegion(menu_down))
        );

        Texture fs_up = new Texture("home.png");
        Texture fs_down = new Texture("home.png");
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

        Table returnTable = new Table();
        menuButton.getImage().setScaling(Scaling.fit);
        menuButton.getImageCell().grow();
        Out.getImage().setScaling(Scaling.fit);
        Out.getImageCell().grow();

        returnTable.add(menuButton).width(100).height(100);
        returnTable.add(Out).width(100).height(100);;



        root.add(stack).padBottom(20)
            .width(500)
            .height(300);
        root.row();
        root.add(returnTable).center();

        rootStack.add(bg);
        rootStack.add(root);
        stage.addActor(rootStack);
        /*
        root.add(Endbutton)
            .width(200)
            .height(80)
            .pad(10);*/


        /*root.add(menuButton)
            .width(200)
            .height(80)
            .pad(10);

        root.add(Out)
            .width(200)
            .height(80)
            .pad(10);*/
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
