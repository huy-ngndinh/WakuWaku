package com.oop.wakuwaku.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.oop.wakuwaku.Main;

import javax.script.ScriptEngineManager;


/** First screen of the application. Displayed after the application is created. */
public class FirstScreen extends ScreenAdapter {
    private Main game;
    private Sprite characterSprite;
    private Array<Sprite> dropSprites;
    float dropTimer;
    public Rectangle characterRectangle;
    public Rectangle dropRectangle;


    // Assets
    private Texture backgroundTexture;
    private Texture characterTexture;
    private Texture dropTexture;
    private Sound dropSound;
    private Music music;

    private FitViewport viewport;

    public FirstScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {

        backgroundTexture = new Texture("background.jpg");
        characterTexture = new Texture("cat.png");
        dropTexture = new Texture("heart.png");

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));

        viewport = new FitViewport(8, 5);

        characterSprite = new Sprite(characterTexture);
        characterSprite.setSize(2,2);

        dropSprites = new Array<>();
        characterRectangle = new Rectangle();
        dropRectangle = new Rectangle();


    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        //if(width <= 0 || height <= 0) return;
        viewport.update(width, height, true);
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();
    }
    private void input(){
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            characterSprite.translateX(speed*delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            characterSprite.translateX(-speed*delta);
        } else if (Gdx.input.isKeyPressed((Input.Keys.UP))){
            characterSprite.translateY(speed*delta);
        }else if (Gdx.input.isKeyPressed((Input.Keys.DOWN))){
            characterSprite.translateY(-speed*delta);
        }

        if (Gdx.input.isTouched()){
            game.touchPos.set(Gdx.input.getX(),Gdx.input.getY());
            viewport.unproject(game.touchPos);
            characterSprite.setCenter(game.touchPos.x,game.touchPos.y);
        }
    }

    private void logic(){
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float characterWidth = characterSprite.getWidth();
        float characterHeight = characterSprite.getHeight();

        characterSprite.setX(MathUtils.clamp(characterSprite.getX(),0,worldWidth - characterWidth));
        characterSprite.setY(MathUtils.clamp(characterSprite.getY(),0,worldHeight - characterHeight));

        float delta = Gdx.graphics.getDeltaTime();

        characterRectangle.set(characterSprite.getX(), characterSprite.getY(), characterWidth, characterHeight);

        for (int i = dropSprites.size - 1; i >= 0; i--) {
            Sprite dropSprite = dropSprites.get(i); // Get the sprite from the list
            float dropWidth = dropSprite.getWidth();
            float dropHeight = dropSprite.getHeight();

            dropSprite.translateY(-2f * delta);
            dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropWidth, dropHeight);

            if (dropSprite.getY() < -dropHeight){
                dropSprites.removeIndex(i);
                game.setScreen(new ResultScreen(game));
                return;
            }
            else if (characterRectangle.overlaps(dropRectangle)){
                dropSprites.removeIndex(i);
            }
        }

        dropTimer +=delta;
        if (dropTimer > 1f)
        {
            dropTimer = 0;
            createDroplet();
        }
    }

    private void draw(){
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();

        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        game.batch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        game.batch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight); // draw the background
        //game.batch.draw(characterTexture,2,1,2,2);
        characterSprite.draw(game.batch);

        for (Sprite dropSprite: dropSprites){
            dropSprite.draw(game.batch);
        }


        game.batch.end();
    }

    private void createDroplet(){
        float dropWidth = 1;
        float dropHeight = 1;

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        Sprite dropSprite = new Sprite(dropTexture);
        dropSprite.setSize(dropWidth, dropHeight);
        dropSprite.setX(MathUtils.random(0f, worldWidth - dropWidth));
        dropSprite.setY(worldHeight);
        dropSprites.add(dropSprite);

    }


    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        characterTexture.dispose();
        dropTexture.dispose();
        dropSound.dispose();
        music.dispose();
    }
}
