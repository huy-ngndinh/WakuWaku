package com.oop.wakuwaku.Animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oop.wakuwaku.State.PlayerState;
import com.oop.wakuwaku.world.Player;

public class FallingAnimation extends AnimationState{
    public static final FallingAnimation INSTANCE = new FallingAnimation();

    private final int ANIMATION_FRAME = 1;
    private final Animation<TextureRegion> animation;

    private FallingAnimation() {
        animation = initializeAnimation("animation/fall.png", 1f);
    }

    protected int getAnimationFrameCount() {
        return ANIMATION_FRAME;
    }

    public TextureRegion getTextureRegion(float stateTime, PlayerState playerState, Player player) {
        TextureRegion animationFrame = animation.getKeyFrame(stateTime, true);
        if (player.getDirection() == 0 && !animationFrame.isFlipX()) animationFrame.flip(true, false);
        else if (player.getDirection() == 1 && animationFrame.isFlipX()) animationFrame.flip(true, false);
        return animationFrame;
    }
}
