package com.oop.wakuwaku.System;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.oop.wakuwaku.Input.GameButton;
import com.oop.wakuwaku.Input.SettingsPanel;
import com.oop.wakuwaku.Main;

public class UserInterfaceHandler {
    Render render;
    // UI
    private Stage stage;
    private GameButton pauseButton;
    private SettingsPanel settingsPanel;

    public UserInterfaceHandler(Render render, Main game) {
        this.render = render;

        stage = new Stage(render.getUIViewport());

        pauseButton = new GameButton(new Texture("Buttons/Pause.png"), new Texture("Buttons/Pause1.png"), 980, 630, 70, 64);
        settingsPanel = new SettingsPanel(game, stage, new Texture("Buttons/settings_panel.png"), new Texture("Buttons/Paw.png"),
            new Texture("Buttons/Bar.png"), new Texture("Buttons/Close.png"), new Texture("Buttons/Close1.png"),
            new Texture("Buttons/Exit.png"), new Texture("Buttons/Exit1.png"));
        pauseBtn();
    }

    public void updateSettingPanel() {
        settingsPanel.update(render.getUIViewport());
    }

    public void drawSettingPanel(SpriteBatch batch) {
        settingsPanel.draw(batch);
    }

    public Stage getStage() {
        return stage;
    }

    public boolean isSettingPanelVisible() {
        return settingsPanel.isVisible();
    }

    public float getVolume() {
        return settingsPanel.getMusicVolume();
    }

    public void setPauseButton(boolean visible) {
        pauseButton.setVisible(visible);
    }

    private void pauseBtn() {
        stage.addActor(pauseButton);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingsPanel.setVisible(true);
            }
        });
    }
}
