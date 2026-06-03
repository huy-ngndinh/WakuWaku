package com.oop.wakuwaku.Animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oop.wakuwaku.State.PlayerState;
import com.oop.wakuwaku.world.Player;

/**
 * Superclass of all animation state.
 */
public abstract class AnimationState {

    /**
     * Get animation based on spritesheet
     * @param filePath path to spritesheet
     * @param timeLimit total time per one full cycle of animation
     * @return <a href="https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/graphics/g2d/Animation.java">Animation</a>
     */
    protected Animation<TextureRegion> initializeAnimation(String filePath, float timeLimit) {
        int ANIMATION_FRAME = getAnimationFrameCount();
        Texture animationTexture = new Texture(Gdx.files.internal(filePath));
        TextureRegion[][] tempAnimationRegion = TextureRegion.split(animationTexture, animationTexture.getWidth() / ANIMATION_FRAME, animationTexture.getHeight());
        TextureRegion[] animationRegion = new TextureRegion[ANIMATION_FRAME];
        for (int i = 0; i < ANIMATION_FRAME; i++) animationRegion[i] = tempAnimationRegion[0][i];
        return new Animation<TextureRegion>(timeLimit / ANIMATION_FRAME, animationRegion);
    }

    /**
     * Get the number of animation frame in the spritesheet
     * @return number of animation frame
     */
    protected abstract int getAnimationFrameCount();

    /**
     * Get an animation frame
     * @param stateTime elapsed time
     * @param currentState current state of player
     * @param player player itself
     * @return an animation frame (<a href="https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/graphics/g2d/TextureRegion.java">TextureRegion</a>).
     */
    public abstract TextureRegion getTextureRegion(float stateTime, PlayerState currentState, Player player);
}
