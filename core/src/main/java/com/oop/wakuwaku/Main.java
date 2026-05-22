package com.oop.wakuwaku;

import com.badlogic.gdx.Game;
import com.oop.wakuwaku.Screen.MenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
    }

}
