package com.oop.wakuwaku.Animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oop.wakuwaku.State.PlayerState;
import com.oop.wakuwaku.State.WallClimb;
import com.oop.wakuwaku.world.Player;

public class WallClimbAnimation extends AnimationState{
    public static final WallClimbAnimation INSTANCE = new WallClimbAnimation();

    private final int ANIMATION_FRAME = 4;
    private final Animation<TextureRegion> animation;

    private WallClimbAnimation() {
        animation = initializeAnimation("animation/wallClimb.png", 1f);
    }

    protected int getAnimationFrameCount() {
        return ANIMATION_FRAME;
    }

    public TextureRegion getTextureRegion(float stateTime, PlayerState playerState, Player player) {
        TextureRegion animationFrame = animation.getKeyFrame(stateTime, true);
        if (((WallClimb) playerState).getWallDirection() == -1 && animationFrame.isFlipX()) animationFrame.flip(true, false);
        else if (((WallClimb) playerState).getWallDirection() == 1 && !animationFrame.isFlipX()) animationFrame.flip(true, false);
        return animationFrame;
    }
}
