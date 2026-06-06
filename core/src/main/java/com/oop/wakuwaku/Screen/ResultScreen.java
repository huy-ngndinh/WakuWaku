package com.oop.wakuwaku.Screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.oop.wakuwaku.FactManager.FactManager;
import com.oop.wakuwaku.Main;
import com.oop.wakuwaku.Transition.InTransition;
import com.oop.wakuwaku.Transition.OutTransition;


public class ResultScreen extends ScreenAdapter {

    private final Main game;
    private Stage stage;
    private Skin skin;
    private ScreenViewport viewport;
    private Texture transitionTexture;
    private InTransition inTransition;
    private OutTransition outTransition;
    private int factIndex;
    private SpriteBatch batch;

    public ResultScreen(Main game, int factIndex) { // thêm tham số
        this.game = game;
        this.factIndex = factIndex;
    }

    @Override
    public void show() {
        viewport = new ScreenViewport();
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        // transition
        inTransition = new InTransition(viewport);
        outTransition = new OutTransition(viewport);
        transitionTexture = new Texture(Gdx.files.internal("asset_work/transition/transition.png"));
        inTransition.setTransition();
        batch = new SpriteBatch();

        // scene2d.ui
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Background
        Image bg = new Image(new Texture("ResultAsset/BGResultScreen.png"));
        bg.setFillParent(true);

        //  Label
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
            Gdx.files.internal("ResultAsset/aseprite.ttf")
        );
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 15;
        parameter.color = Color.BLACK;
        BitmapFont arcadeFont = generator.generateFont(parameter);

        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = arcadeFont;

        Label factText = new Label(FactManager.getFact(factIndex), labelStyle);
        factText.setWrap(true);
        factText.setAlignment(Align.center);
        float fontScale = stage.getWidth() / 800f;
        factText.setFontScale(fontScale);

        Image board = new Image(new Texture("ResultAsset/newfact.png"));

        Table factTable = new Table();
        factTable.add(factText)
            .center().expandX()
            .fillX()
            .pad(20);;
        //factTable.add(factText).center().width(200).pad(20);

        Stack boardStack = new Stack();
        boardStack.add(board);
        boardStack.add(factTable);

        Texture buttonSheet = new Texture("ResultAsset/NextButton.png");
        int halfWidth = buttonSheet.getWidth() / 2;
        int btnHeight = buttonSheet.getHeight();

        TextureRegion normal  = new TextureRegion(buttonSheet,0,0, halfWidth, btnHeight);
        TextureRegion pressed = new TextureRegion(buttonSheet, halfWidth, 0, halfWidth, btnHeight);

        ImageButton nextButton = new ImageButton(
            new TextureRegionDrawable(normal),
            new TextureRegionDrawable(pressed)
        );
        nextButton.getImage().setScaling(Scaling.fit);
        nextButton.getImageCell().grow();

        Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                outTransition.setTransition();
//                game.setScreen(new MenuScreen(game));
            }
        });

        // Layout
        Table root = new Table();
        root.setFillParent(true);
        root.row();

        root.add(boardStack)
            .width(stage.getWidth() * 0.7f)
            .height(stage.getHeight() * 0.65f)
            .padTop(stage.getHeight() * 0.05f)
            .top()
        ;
        root.row();
        root.add(nextButton)
            .width(stage.getWidth() * 0.4f)
            .height(stage.getHeight() * 0.4f)
            .padTop(-stage.getHeight() * 0.1f);
        Stack rootStack = new Stack();
        rootStack.setFillParent(true);
        rootStack.add(bg);
        rootStack.add(root);

        stage.addActor(rootStack);
    }

    public void drawTransition(float delta, boolean type) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        float width = viewport.getWorldWidth();
        float height = viewport.getWorldHeight();

        float yPosition;
        if (!type) {
            inTransition.update(delta);
            yPosition = inTransition.getYPosition();
        } else {
            outTransition.update(delta);
            yPosition = outTransition.getYPosition();
        }

//        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
//        pixmap.setColor(Color.BLUE);
//        pixmap.fill();
//        Texture pixel = new Texture(pixmap);
//        pixmap.dispose();

        batch.draw(transitionTexture, 0, yPosition, width, height);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();

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

        if (outTransition.isTransitionFinished()) game.setScreen(new MenuScreen(game));
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        stage.clear();
        show();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
