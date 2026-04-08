package com.oop.wakuwaku.input;

import com.badlogic.gdx.Gdx;

public class GameInput {
    public boolean isPressed(int key){
        return Gdx.input.isKeyPressed(key);
    }
}
