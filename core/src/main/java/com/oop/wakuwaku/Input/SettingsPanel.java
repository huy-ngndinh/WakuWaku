package com.oop.wakuwaku.Input;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.oop.wakuwaku.Screen.MenuScreen;
import com.oop.wakuwaku.Main;

public class SettingsPanel {
    private final Main game;
    private final Texture popupTex, PawTex, BarTex;
    private final Slider musicSlider, effectSlider;
    private GameButton CloseButton, exitButton;
    private boolean Visible = false;
    private final com.badlogic.gdx.math.Vector3 tempMouseCoords = new com.badlogic.gdx.math.Vector3();

    public SettingsPanel(Main game, Stage stage, Texture popupTex, Texture PawTex, Texture BarTex,
                         Texture Close, Texture Close1, Texture Exit, Texture Exit1) {
        this.game = game;
        this.popupTex = popupTex;
        this.PawTex = PawTex;
        this.BarTex = BarTex;
        musicSlider = new Slider(358);
        effectSlider = new Slider(428);
        CloseButton = new GameButton(Close, Close1, 480, 250, 144, 48);
        CloseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        exitButton = new GameButton(Exit, Exit1, 556, 250, 144, 48);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
        stage.addActor(CloseButton);
        stage.addActor(exitButton);
    }

    private void UpdateSliders(Viewport viewport) {
        tempMouseCoords.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(tempMouseCoords);
        if(!musicSlider.isTouching())        effectSlider.update(tempMouseCoords.x, tempMouseCoords.y);
        if(!effectSlider.isTouching())  musicSlider.update(tempMouseCoords.x, tempMouseCoords.y);
    }

    private void UpdateButtons() {
        Screen currentScreen = game.getScreen();
        if(currentScreen instanceof MenuScreen) {
            CloseButton.setPosition(480, 250);
            exitButton.setVisible(false);
            CloseButton.setVisible(true);
        }
        else {
            CloseButton.setPosition(400, 250);
            CloseButton.setVisible(true);
            exitButton.setVisible(true);
        }
        if(!Visible) {
            CloseButton.setVisible(false);
            exitButton.setVisible(false);
        }
    }

    public void update(Viewport viewport) {
        if(Visible) {
            UpdateSliders(viewport);
        }
        UpdateButtons();
    }

    public void draw(SpriteBatch batch) {
        if(Visible) {
            batch.draw(popupTex, 286, 200, 506, 360);
            musicSlider.draw(batch, PawTex, BarTex);
            effectSlider.draw(batch, PawTex, BarTex);
        }
    }

    public void setVisible(boolean visible) {
        this.Visible = visible;
    }

    public boolean isVisible() {
        return Visible;
    }
}
