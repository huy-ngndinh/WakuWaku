package com.oop.wakuwaku.Transition;

import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class InTransition {

    private float yPosition;
    private final float endYPosition;
    private float elapsedTime;
    private final float duration = 1f;
    private boolean transitionBegin;
    private boolean transitionFinished;

    public InTransition(ScreenViewport viewport) {
        yPosition = 0f;
        endYPosition = -viewport.getWorldHeight();
        elapsedTime = 0f;
        transitionBegin = false;
        transitionFinished = false;
    }

    public void update(float delta) {
        if (!transitionBegin) return;

        elapsedTime += delta;

        float eased = smoothStep(Math.min(1f, elapsedTime / duration));

        yPosition = endYPosition * eased;

        if (elapsedTime >= duration) {
            yPosition = endYPosition;
            transitionFinished = true;
        }
    }

    private float smoothStep(float time) {
        return time * time * (3f - 2f * time);
    }

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
