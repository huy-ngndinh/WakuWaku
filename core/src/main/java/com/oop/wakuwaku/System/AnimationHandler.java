package com.oop.wakuwaku.System;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oop.wakuwaku.world.Player;

public class AnimationHandler {

    private static final int IDLE_ANIMATION_FRAMES = 8;
    private static final int RUN_ANIMATION_FRAMES = 10;
    private final Animation<TextureRegion> runAnimation;
    private final Animation<TextureRegion> idleAnimation;

    private float stateTime = 0f;

    public AnimationHandler() {
        // Load the sprite sheet as a Texture
        Texture runTexture = new Texture(Gdx.files.internal("animation/cat-run.png"));
        Texture idleTexture = new Texture(Gdx.files.internal("animation/cat-idle.png"));
        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tempRunRegion = TextureRegion.split(runTexture, runTexture.getWidth() / RUN_ANIMATION_FRAMES, runTexture.getHeight());
        TextureRegion[][] tempIdleRegion = TextureRegion.split(idleTexture, idleTexture.getWidth() / IDLE_ANIMATION_FRAMES, idleTexture.getHeight());
        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] runRegion = new TextureRegion[RUN_ANIMATION_FRAMES];
        for (int i = 0; i < RUN_ANIMATION_FRAMES; i++) {
            runRegion[i] = tempRunRegion[0][i];
        }
        TextureRegion[] idleRegion = new TextureRegion[IDLE_ANIMATION_FRAMES];
        for (int i = 0; i < IDLE_ANIMATION_FRAMES; i++) {
            idleRegion[i] = tempIdleRegion[0][i];
        }

        // Initialize the Animation with the frame interval and array of frames
        runAnimation = new Animation<TextureRegion>(1f / RUN_ANIMATION_FRAMES, runRegion);
        idleAnimation = new Animation<TextureRegion>(1f / IDLE_ANIMATION_FRAMES, idleRegion);

    }

    public TextureRegion getCurrentAnimationFrame(float deltaTime, Player player, PlayerStateHandler playerStateHandler) {
        PlayerStateHandler.State currentState = playerStateHandler.getCurrentState();
        if (playerStateHandler.isDifferentState()) {
            stateTime = 0;
        } else {
            stateTime += deltaTime;
        }
        TextureRegion runFrame;
        switch (currentState) {
            case PlayerStateHandler.State.STAND:
                return idleAnimation.getKeyFrame(stateTime, true);
            case PlayerStateHandler.State.DASH:
            case PlayerStateHandler.State.FALL:
            case PlayerStateHandler.State.JUMP:
            case PlayerStateHandler.State.WALK:
                runFrame = runAnimation.getKeyFrame(stateTime, true);
                if (player.getDirection() == 1 && runFrame.isFlipX()) {
                    runFrame.flip(true, false);
                } else if (player.getDirection() == 0 && !runFrame.isFlipX()) {
                    runFrame.flip(true, false);
                }
                return runFrame;
        }
        return idleAnimation.getKeyFrame(stateTime, true);
    }
}
