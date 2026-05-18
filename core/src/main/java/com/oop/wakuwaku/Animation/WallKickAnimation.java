package com.oop.wakuwaku.Animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oop.wakuwaku.State.PlayerState;
import com.oop.wakuwaku.State.WallKick;
import com.oop.wakuwaku.world.Player;

public class WallKickAnimation extends AnimationState {
    public static final WallKickAnimation INSTANCE = new WallKickAnimation();

    private final int ANIMATION_FRAME = 3;
    private final Animation<TextureRegion> animation;

    private WallKickAnimation() {
        animation = initializeAnimation("animation/wallKick.png", 0.4f);
    }

    protected int getAnimationFrameCount() {
        return ANIMATION_FRAME;
    }

    public TextureRegion getTextureRegion(float stateTime, PlayerState playerState, Player player) {
        TextureRegion animationFrame = animation.getKeyFrame(stateTime, false);
        if (((WallKick) playerState).getWallDirection() == 0 && animationFrame.isFlipX()) animationFrame.flip(true, false);
        else if (((WallKick) playerState).getWallDirection() == 1 && !animationFrame.isFlipX()) animationFrame.flip(true, false);
        return animationFrame;
    }
}
