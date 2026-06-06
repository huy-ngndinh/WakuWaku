package com.oop.wakuwaku.Transition;

import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class OutTransition {

    private float yPosition;
    private final float startYPosition;
    private float elapsedTime;
    private final float duration = 1f;
    private boolean transitionBegin;
    private boolean transitionFinished;

    public OutTransition(ScreenViewport viewport) {
        yPosition = startYPosition = viewport.getWorldHeight();
        elapsedTime = 0;
        transitionBegin = false;
        transitionFinished = false;
    }

    public void update(float delta) {
        if (!transitionBegin) return;
        elapsedTime += delta;
        float eased = smoothStep(Math.min(1f, elapsedTime / duration));
        yPosition = startYPosition * (1f - eased);
        if (yPosition <= 0) transitionFinished = true;
    }

    private float smoothStep(float time) { return time * time * (3 - 2 * time); }

    public float getYPosition() {
        return yPosition;
    }

    public boolean isTransitionBegin() { return transitionBegin; }

    public void setTransition() {
        transitionBegin = true;
    }

    public boolean isTransitionFinished() {
        return transitionFinished;
    }
}
