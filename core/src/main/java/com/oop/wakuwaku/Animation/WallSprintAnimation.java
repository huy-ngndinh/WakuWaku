package com.oop.wakuwaku.Animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oop.wakuwaku.State.PlayerState;
import com.oop.wakuwaku.State.WallSprint;
import com.oop.wakuwaku.world.Player;

public class WallSprintAnimation extends AnimationState {
    public static final WallSprintAnimation INSTANCE = new WallSprintAnimation();

    private final int ANIMATION_FRAME = 2;
    private final Animation<TextureRegion> animation;

    private WallSprintAnimation() {
        animation = initializeAnimation("animation/wallSprint.png", 0.4f);
    }

    protected int getAnimationFrameCount() {
        return ANIMATION_FRAME;
    }

    public TextureRegion getTextureRegion(float stateTime, PlayerState playerState, Player player) {
        TextureRegion animationFrame = animation.getKeyFrame(stateTime, false);
        if (((WallSprint) playerState).getWallDirection() == -1 && animationFrame.isFlipX()) animationFrame.flip(true, false);
        else if (((WallSprint) playerState).getWallDirection() == 1 && !animationFrame.isFlipX()) animationFrame.flip(true, false);
        return animationFrame;
    }
}
