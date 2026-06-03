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

    public Game_Button(Main game, String normalPath, String pressedPath, float x, float y, float width, float height) {
        super(createButtonStyle(normalPath, pressedPath));

        this.game = game;

        this.setPosition(x, y);
        this.setSize(width, height);

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.clickSound != null && game.settingsPopup != null) {
                    game.clickSound.play(game.settingsPopup.effectVolume);
                }
            }
        });
    }

    private static ImageButtonStyle createButtonStyle(String normalPath, String pressedPath) {
        Texture normalTex = new Texture(normalPath);
        Texture pressedTex = new Texture(pressedPath);

        ImageButtonStyle style = new ImageButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(normalTex));
        style.down = new TextureRegionDrawable(new TextureRegion(pressedTex));

        return style;
    }
}