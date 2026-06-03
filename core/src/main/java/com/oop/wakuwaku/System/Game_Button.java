package com.oop.wakuwaku.System;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.Texture;
import com.oop.wakuwaku.Main;

public class Game_Button extends ImageButton {
    private final Main game;

    private Texture normalTex;
    private Texture pressedTex;

    public Game_Button(final Main game, String normalPath, String pressedPath, float x, float y, float width, float height) {
        super(new ImageButtonStyle());

        this.game = game;
        this.setPosition(x, y);
        this.setSize(width, height);

        this.normalTex = new Texture(normalPath);
        this.pressedTex = new Texture(pressedPath);
        this.getStyle().up = new TextureRegionDrawable(new TextureRegion(normalTex));
        this.getStyle().down = new TextureRegionDrawable(new TextureRegion(pressedTex));

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.clickSound != null && game.settingsPopup != null) {
                    game.clickSound.play(game.settingsPopup.effectVolume);
                }
            }
        });
    }

    public void dispose() {
        if (normalTex != null) {
            normalTex.dispose();
            normalTex = null;
        }
        if (pressedTex != null) {
            pressedTex.dispose();
            pressedTex = null;
        }
    }
}