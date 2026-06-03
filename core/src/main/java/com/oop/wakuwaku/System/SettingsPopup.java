package com.oop.wakuwaku.System;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.wakuwaku.Main;

public class SettingsPopup {
    private final Main game;
    private final Texture popupTex;
    private final Texture catHeadTex;

    public float musicVolume = 0.5f;
    public float effectVolume = 0.5f;

    private final int sliderMinX = 625, sliderMaxX = 825;
    private final int sliderY_music = 400, sliderY_effect = 450;
    private final int catWidth = 72, catHeight = 65;


    private boolean isTouching_Music = false;
    private boolean isTouching_Effect = false;


    public SettingsPopup(Main game) {
        this.game = game;
        this.popupTex = new Texture("settings_panel.png");
        this.catHeadTex = new Texture("cat_head.png");
    }

    public void updateAndDraw(Viewport viewport) {
        if (!game.showPopup) return;

        if (Gdx.input.isTouched()) {
            game.touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(game.touchPos);

            if (!isTouching_Effect) {
                if (isTouching_Music) {
                    float x = MathUtils.clamp(game.touchPos.x, sliderMinX, sliderMaxX);
                    musicVolume = (x - sliderMinX) / (sliderMaxX - sliderMinX);
                }
                else if (game.touchPos.x >= sliderMinX && game.touchPos.x <= sliderMaxX && game.touchPos.y >= sliderY_music - 25 && game.touchPos.y <= sliderY_music + 25) {
                    isTouching_Music = true;
                }
            }

            if (!isTouching_Music) {
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
            isTouching_Music = false;
            isTouching_Effect = false;
        }

        game.music.setVolume(musicVolume);

        game.batch.draw(popupTex, 340, 200, 600, 360);


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
    }
}