package com.oop.wakuwaku.Transition;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class OutTransition {

    private float alpha;
    private float elapsedTime;
    private final float duration = 0.7f;

    private boolean transitionBegin;
    private boolean transitionFinished;

    private final int ANIMATION_FRAME = 1;
    private Animation<TextureRegion> animation;

    public OutTransition() {
        alpha = 0f; // start transparent
        elapsedTime = 0f;
        transitionBegin = false;
        transitionFinished = false;

        Texture spriteSheet = new Texture("transition/transition.png");
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, 360, 240);
        TextureRegion[] frames = new TextureRegion[ANIMATION_FRAME];
        for (int i = 0; i < ANIMATION_FRAME; i++) frames[i] = tmp[0][i];
        animation = new Animation<>(1f, frames);
    }

    public void update(float delta) {
        if (!transitionBegin) return;

        elapsedTime += delta;

        float progress = Math.min(1f, elapsedTime / duration);
        float eased = smoothStep(progress);

        alpha = eased;

        if (progress >= 1f) {
            alpha = 1f;
            transitionFinished = true;
        }
    }

    private float smoothStep(float t) {
        t = 1f - (1f - t) * (1f - t) * (1f - t);
        return t;
    }

    public float getAlpha() {
        return alpha;
    }

    public TextureRegion getCurrentFrame() {
        return animation.getKeyFrame(elapsedTime, true);
    }

    public void setTransition() {
        transitionBegin = true;
    }

    public boolean isTransitionFinished() {
        return transitionFinished;
    }

    public boolean isTransitionBegin() {
        return transitionBegin;
    }
}
