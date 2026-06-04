package com.oop.wakuwaku.Animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oop.wakuwaku.State.PlayerState;
import com.oop.wakuwaku.world.Player;

public class GoalAnimation extends AnimationState {
    public static final GoalAnimation INSTANCE = new GoalAnimation();

    private final int ANIMATION_FRAME = 6;
    private final Animation<TextureRegion> animationSitting, animationWaving;

    private GoalAnimation() {
        animationSitting = initializeAnimation("animation/goalSitting.png", 1f);
        animationWaving = initializeAnimation("animation/goalWaving.png", 1f);
    }

    protected int getAnimationFrameCount() {
        return ANIMATION_FRAME;
    }

    public TextureRegion getTextureRegion(float stateTime, PlayerState playerState, Player player) {
        TextureRegion animationFrame;
        if (animationSitting.isAnimationFinished(stateTime)) {
            animationFrame = animationWaving.getKeyFrame(stateTime, true);
        } else {
            animationFrame = animationSitting.getKeyFrame(stateTime, false);
        }
        if (player.getDirection() == 1 && !animationFrame.isFlipX()) animationFrame.flip(true, false);
        else if (player.getDirection() == -1 && animationFrame.isFlipX()) animationFrame.flip(true, false);
        return animationFrame;
    }
}
