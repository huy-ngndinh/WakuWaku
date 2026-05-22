package com.oop.wakuwaku.Animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oop.wakuwaku.State.PlayerState;
import com.oop.wakuwaku.State.WallAttach;
import com.oop.wakuwaku.world.Player;

public class WallAttachAnimation extends AnimationState {
    public static final WallAttachAnimation INSTANCE = new WallAttachAnimation();

    private final int ANIMATION_FRAME = 6;
    private final Animation<TextureRegion> animation;

    private WallAttachAnimation() {
        animation = initializeAnimation("animation/wallAttach.png", 1f);
    }

    protected int getAnimationFrameCount() {
        return ANIMATION_FRAME;
    }

    public TextureRegion getTextureRegion(float stateTime, PlayerState playerState, Player player) {
        TextureRegion animationFrame = animation.getKeyFrame(stateTime, true);
        if (((WallAttach) playerState).getWallDirection() == 0 && animationFrame.isFlipX()) animationFrame.flip(true, false);
        else if (((WallAttach) playerState).getWallDirection() == 1 && !animationFrame.isFlipX()) animationFrame.flip(true, false);
        return animationFrame;
    }
}
