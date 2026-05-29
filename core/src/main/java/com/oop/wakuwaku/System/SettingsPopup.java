package com.oop.wakuwaku.System;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.wakuwaku.Main;

public class SettingsPopup {
    private final Main game;
    private final Texture popupTex;
    private final Texture catHeadTex;
    private final Texture closeTex;

    public float musicVolume = 0.5f;
    public float effectVolume = 0.5f;

    private final int sliderMinX = 625, sliderMaxX = 825;
    private final int sliderY_music = 400, sliderY_effect = 450;
    private final int catWidth = 72, catHeight = 65;

    private final int closeX = 521, closeY = 235, closeW = 235, closeH = 125;

    private boolean isTouching_Music = false;
    private boolean isTouching_Effect = false;
    private boolean isClickingClose = false; 

    private final Sound clickSound;

    public SettingsPopup(Main game) {
        this.game = game;
        this.popupTex = new Texture("settings_panel.png");
        this.catHeadTex = new Texture("cat_head.png");
        this.closeTex = new Texture("close.png");
        this.clickSound = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
    }

    public void updateAndDraw(Viewport viewport, Music currentMusic) {
        if (!game.showPopup) return;

        if (Gdx.input.isTouched()) {
            game.touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(game.touchPos);

            if (!isTouching_Music && !isTouching_Effect) {
                if (game.touchPos.x >= closeX && game.touchPos.x <= closeX + closeW && game.touchPos.y >= closeY && game.touchPos.y <= closeY + closeH) {
                    isClickingClose = true;
                }
            }

            if (!isTouching_Effect && !isClickingClose) {
                if (isTouching_Music) {
                    float x = MathUtils.clamp(game.touchPos.x, sliderMinX, sliderMaxX);
                    musicVolume = (x - sliderMinX) / (sliderMaxX - sliderMinX);
                }
                else if (game.touchPos.x >= sliderMinX && game.touchPos.x <= sliderMaxX && game.touchPos.y >= sliderY_music - 25 && game.touchPos.y <= sliderY_music + 25) {
                    isTouching_Music = true;
                }
            }

            if (!isTouching_Music && !isClickingClose) {
                if (isTouching_Effect) {
                    float x = MathUtils.clamp(game.touchPos.x, sliderMinX, sliderMaxX);
                    effectVolume = (x - sliderMinX) / (sliderMaxX - sliderMinX);
                }
                else if (game.touchPos.x >= sliderMinX && game.touchPos.x <= sliderMaxX && game.touchPos.y >= sliderY_effect - 25 && game.touchPos.y <= sliderY_effect + 25) {
                    isTouching_Effect = true;
                }
            }
        }
        else {
            if (isClickingClose) {
                clickSound.play(effectVolume);
                game.showPopup = false;
                isClickingClose = false;
            }
            isTouching_Music = false;
            isTouching_Effect = false;
        }

        if (currentMusic != null) {
            currentMusic.setVolume(musicVolume);
        }

        game.batch.draw(popupTex, 40, 25, 1200, 670);

        game.batch.draw(closeTex, closeX, closeY, closeW, closeH);

        float musicCatX = sliderMinX + musicVolume * (sliderMaxX - sliderMinX) - catWidth / 2f;
        float musicCatY = sliderY_music - catHeight / 2f;
        game.batch.draw(catHeadTex, musicCatX, musicCatY, catWidth, catHeight);

        float catX_effect = sliderMinX + effectVolume * (sliderMaxX - sliderMinX) - catWidth / 2f;
        float catY_effect = sliderY_effect - catHeight / 2f;
        game.batch.draw(catHeadTex, catX_effect, catY_effect, catWidth, catHeight);
    }

    public void dispose() {
        popupTex.dispose();
        catHeadTex.dispose();
        closeTex.dispose();
        clickSound.dispose();
    }
}