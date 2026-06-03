package com.oop.wakuwaku;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oop.wakuwaku.System.SettingsPopup;
import com.oop.wakuwaku.Screen.MenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public Vector2 touchPos;
    public SettingsPopup settingsPopup;
    public boolean showPopup = false;
    public Music music;
    public Sound clickSound;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MenuScreen(this));
        settingsPopup = new SettingsPopup(this);
        touchPos = new Vector2();
        font = new BitmapFont();
        music = Gdx.audio.newMusic(Gdx.files.internal("minecraft.mp3"));
        music.setLooping(true);
        clickSound = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
    }

    @Override
    public void dispose() {
        batch.dispose();
        settingsPopup.dispose();
        music.dispose();
        clickSound.dispose();
    }

}
