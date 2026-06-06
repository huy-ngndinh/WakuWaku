package com.oop.wakuwaku.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Slider {
    private final int sliderMinX = 625, sliderMaxX = 850;
    private int y;
    private final int catWidth = 46, catHeight = 42;
    private boolean isTouching = false;
    private float value = 0.5f;

    public Slider(int y) {
        this.y = y;
    }

    public void update(float X, float Y) {
        if(Gdx.input.isTouched()) {
            if (isTouching) {
                X = Math.max(sliderMinX, Math.min(sliderMaxX, X));
                value = (X - sliderMinX) / 225f;
            }
            else if (X >= sliderMinX && X <= sliderMaxX && Y >= y - 25 && Y <= y + 25) {
                isTouching = true;
            }
        } else {
            isTouching = false;
        }
    }

    public void draw(SpriteBatch batch, Texture PawTex, Texture BarTex) {
        batch.draw(BarTex, 610, y, 255, 30);
        batch.draw(PawTex, sliderMinX + (sliderMaxX - sliderMinX) * value - catWidth / 2, y - 3, catWidth, catHeight);
    }

    public float getValue() {
        return value;
    }
    
    public boolean isTouching() {
        return isTouching;
    }
    
    public void setTouching(boolean touching) {
        isTouching = touching;
    }
}
