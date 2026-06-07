package com.oop.wakuwaku.Input;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class GameButton extends ImageButton {
    private static final Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));

    public GameButton(Texture normalTex, Texture pressedTex, float x, float y, float width, float height) {
        super(createButtonStyle(normalTex, pressedTex));

        this.setPosition(x, y);
        this.setSize(width, height);

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
            }
        });
    }

    public void resize(int width, int height) {
        this.setPosition(width - 100, height - 100);
    }

    private static ImageButtonStyle createButtonStyle(Texture normalTex, Texture pressedTex) {
        ImageButtonStyle style = new ImageButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(normalTex));
        style.down = new TextureRegionDrawable(new TextureRegion(pressedTex));
        return style;
    }

    public static void dispose() {
        if (clickSound != null) {
            clickSound.dispose();
        }
    }
}
