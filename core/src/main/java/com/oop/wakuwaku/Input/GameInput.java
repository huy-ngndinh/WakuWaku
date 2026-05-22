package com.oop.wakuwaku.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class GameInput extends InputAdapter{
    /** Check if a key is pressed
     * @param key The key needed to check. Keys are defined in <a href="https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/Input.java" >Input.java</a>
     * @return <code>True</code> if the key is pressed, <code>False</code> otherwise
     */
        public boolean isPressed(int key) { return Gdx.input.isKeyPressed(key); }
        public boolean isJustPressed(int key) { return Gdx.input.isKeyJustPressed(key); }

        private boolean holdingSpace = false;
        private int holdTime = 0;

        @Override
        public boolean keyDown(int keycode) {
            if (keycode == Input.Keys.SPACE) {
                holdingSpace = true;
                holdTime = 0;      // reset timer
            }
            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            if (keycode == Input.Keys.SPACE) {
                holdingSpace = false;

                System.out.println("Held for: " + holdTime);
            }
            return true;
        }

        public void update(float delta) {
            if (holdingSpace) {
                holdTime += 1;
            }
        }

        public boolean isHoldingSpace() {
            return holdingSpace;
        }
        public int getHoldTime() {
            return holdTime;
        }
}
