package com.oop.wakuwaku.System;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Physics {
    private World world;
    private Box2DDebugRenderer debugRenderer;

    public Physics() {
      this.world = new World(new Vector2(0f, -10f), false);
      this.debugRenderer = new Box2DDebugRenderer();
      
    }

    public World getWorld() {
      return this.world;
    }

    public Box2DDebugRenderer getDebugRenderer() {
      return this.debugRenderer;
    }

    public void simulate(float delta) {
      world.step(delta, 6, 2);
    }
}
