package com.oop.wakuwaku;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameAssetManager {
    public static final AssetManager manager = new AssetManager();
    public static final String UNTITLED_3 = "Untitled 3.png";
    public static final String BACKGROUND = "background.jpg";
    public static final String CAT = "cat.png";
    public static final String CHARACTER = "character.png";
    public static final String HEART = "heart.png";
    public static final String NG_LOGO = "ng.png";
    public static final String START_BTN = "start.png";
    public static final String START_BTN_2 = "start2.png";
    public static final String STOP_BTN_1 = "stop1.png";
    public static final String STOP_BTN_2 = "stop2.png";
    public static final String PIXEL_KIT = "pixel kit UI.png";

    public static final String CLICK_SOUND = "click.mp3";
    public static final String DROP_SOUND = "drop.mp3";
    public static final String BGM = "music.mp3";

    public static final String FONT_MAIN = "ui/font.fnt";

    public static void loadAll() {
        // Load Textures
        manager.load(UNTITLED_3, Texture.class);
        manager.load(BACKGROUND, Texture.class);
        manager.load(CAT, Texture.class);
        manager.load(CHARACTER, Texture.class);
        manager.load(HEART, Texture.class);
        manager.load(NG_LOGO, Texture.class);
        manager.load(START_BTN, Texture.class);
        manager.load(START_BTN_2, Texture.class);
        manager.load(STOP_BTN_1, Texture.class);
        manager.load(STOP_BTN_2, Texture.class);
        manager.load(PIXEL_KIT, Texture.class);

        // Load Audio
        manager.load(CLICK_SOUND, Sound.class);
        manager.load(DROP_SOUND, Sound.class);
        manager.load(BGM, Music.class);

        // Load Font
        manager.load(FONT_MAIN, BitmapFont.class);
    }
    
    public static Texture getTexture(String path) { return manager.get(path, Texture.class); }
    public static Sound getSound(String path) { return manager.get(path, Sound.class); }
    public static Music getMusic(String path) { return manager.get(path, Music.class); }
    public static BitmapFont getFont(String path) { return manager.get(path, BitmapFont.class); }

    public static boolean update() { return manager.update(); }
    public static void dispose() { manager.dispose(); }
}
