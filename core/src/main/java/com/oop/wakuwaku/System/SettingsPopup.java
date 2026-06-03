package com.oop.wakuwaku.System;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.wakuwaku.Main;

public class SettingsPopup {
    private final Main game;
    private final Texture popupTex;
    private final Texture PawTex;
    private final Texture Music_BarTex, Effect_BarTex;

    public static float musicVolume = 0.5f;
    public static float effectVolume = 0.5f;

    private final int sliderMinX = 625, sliderMaxX = 850;
    private final int sliderY_music = 428, sliderY_effect = 358;
    private final int catWidth = 46, catHeight = 42;


    private boolean isTouching_Music = false;
    private boolean isTouching_Effect = false;


    public SettingsPopup(Main game) {
        this.game = game;
        popupTex = new Texture("Buttons/settings_panel.png");
        PawTex = new Texture("Buttons/Paw.png");
        Music_BarTex = new Texture("Buttons/Bar.png");
        Effect_BarTex = new Texture("Buttons/Bar.png");
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


        float CatX_music = sliderMinX + musicVolume * (sliderMaxX - sliderMinX) - catWidth / 2f;
        game.batch.draw(Music_BarTex, sliderMinX - 15, sliderY_music, sliderMaxX - sliderMinX + 30, 30);
        game.batch.draw(PawTex, CatX_music, sliderY_music - 3, catWidth, catHeight);

        float CatX_effect = sliderMinX + effectVolume * (sliderMaxX - sliderMinX) - catWidth / 2f;
        game.batch.draw(Effect_BarTex, sliderMinX - 15, sliderY_effect, sliderMaxX - sliderMinX + 30, 30);
        game.batch.draw(PawTex, CatX_effect, sliderY_effect - 3, catWidth, catHeight);
    }

    public void dispose() {
        popupTex.dispose();
        PawTex.dispose();
        Music_BarTex.dispose();
        Effect_BarTex.dispose();
    }
}