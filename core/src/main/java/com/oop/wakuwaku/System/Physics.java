package com.oop.wakuwaku.System;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The class responsible for controlling the general physic logic of the game. Use <code>Box2D</code> internally
 */
public class Physics {
    private World world;
    private Box2DDebugRenderer debugRenderer;

    public Physics() {
      this.world = new World(new Vector2(0f, -10f), false);
      this.debugRenderer = new Box2DDebugRenderer();

    }

    /**
     * Return the <a href="https://github.com/libgdx/libgdx/blob/master/extensions/gdx-box2d/gdx-box2d/src/com/badlogic/gdx/physics/box2d/World.java"><code>World</code></a> instance. There should only be one instance of <code>World</code> which is managed by this class.
     * @return <code>World</code>
     */
    public World getWorld() {
      return this.world;
    }

    /**
     * Return the <a href="https://github.com/libgdx/libgdx/blob/master/extensions/gdx-box2d/gdx-box2d/src/com/badlogic/gdx/physics/box2d/Box2DDebugRenderer.java"><code>Box2DDebugRenderer</code></a> instance. There should only be one instance of <code>Box2DDebugRenderer</code> which is managed by this class.
     * @return <code>Box2DDebugRenderer</code>
     */
    public Box2DDebugRenderer getDebugRenderer() {
      return this.debugRenderer;
    }

    /**
     * Calculate the position, velocity, force, etc. of physics objects defined in <code>World</code> after <code>delta</code> seconds.
     * @param delta The passing time. Should use <a href="https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/Graphics.java#L206"><code>com.libgdx.Graphics.getDeltaTime()</code></a>
     */
    public void simulate(float delta) {
      world.step(delta, 6, 2);
    }
}
