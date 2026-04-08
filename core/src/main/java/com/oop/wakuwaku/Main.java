package com.oop.wakuwaku;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oop.wakuwaku.screen.MenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public Vector2 touchPos;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MenuScreen(this));
        touchPos = new Vector2();
        font = new BitmapFont();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
