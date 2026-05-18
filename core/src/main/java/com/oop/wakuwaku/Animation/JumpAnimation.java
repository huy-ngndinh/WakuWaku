package com.oop.wakuwaku.Animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oop.wakuwaku.State.PlayerState;
import com.oop.wakuwaku.world.Player;

public class JumpAnimation extends AnimationState {
    public static final JumpAnimation INSTANCE = new JumpAnimation();

    private final int ANIMATION_FRAME = 3;
    private final Animation<TextureRegion> animation;

    private JumpAnimation() {
        animation = initializeAnimation("animation/jump.png", 0.4f);
    }

    protected int getAnimationFrameCount() {
        return ANIMATION_FRAME;
    }

    public TextureRegion getTextureRegion(float stateTime, PlayerState playerState, Player player) {
        TextureRegion animationFrame = animation.getKeyFrame(stateTime, false);
        if (player.getDirection() == 0 && !animationFrame.isFlipX()) animationFrame.flip(true, false);
        else if (player.getDirection() == 1 && animationFrame.isFlipX()) animationFrame.flip(true, false);
        return animationFrame;
    }
}
