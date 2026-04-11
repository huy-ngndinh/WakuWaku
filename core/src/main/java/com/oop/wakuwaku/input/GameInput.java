package com.oop.wakuwaku.input;

import com.badlogic.gdx.Gdx;

public class GameInput {
    /** Check if a key is pressed
     * @param key The key needed to check. Keys are defined in <a href="https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/Input.java" >Input.java</a>
     * @return <code>True</code> if the key is pressed, <code>False</code> otherwise
     */
    public boolean isPressed(int key){
        return Gdx.input.isKeyPressed(key);
    }

    public boolean isJustPressed(int key) {
        return Gdx.input.isKeyJustPressed(key);
    }
}
