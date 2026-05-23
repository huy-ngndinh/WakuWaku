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

        private boolean holdingSpace = false;
        private int holdTimeSpace = 0;

        @Override
        public boolean keyDown(int keycode) {
            // xử lí space key press
            if (keycode == Input.Keys.SPACE) {
                holdingSpace = true;
                holdTimeSpace = 0;      // reset timer
            }
            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            // xử lí space key release
            if (keycode == Input.Keys.SPACE) {
                holdingSpace = false;
            }
            return true;
        }

        public void update(float delta) {
            if (holdingSpace) {
                holdTimeSpace += 1;
            }
        }

        public boolean isHoldingSpace() {
            return holdingSpace;
        }

        public int getHoldTimeSpace() {
            return holdTimeSpace;
        }
}
