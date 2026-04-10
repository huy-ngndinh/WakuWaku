package com.oop.wakuwaku.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * This class manages the main character.
 */
public class Player extends Sprite{
    private float TILE_PIXEL = 32f;
    private float UNIT = 1f / TILE_PIXEL;

    private Body b2body;
    private CircleShape shape;
    private int jumpCount = 0;

    public Player(World world) {
        //this.world = world;

        // define player

        BodyDef bdef = new BodyDef();
        bdef.position.set(64f * UNIT, 150f * UNIT);
        bdef.type = BodyDef.BodyType.DynamicBody;
        // sau khi def body trong world thì ta sẽ truyền bdef đó vào hàm createBody của world để tạo ra body trong world
        b2body = world.createBody(bdef);

        // fixture def: chứa hình dạng và physics của body
        FixtureDef fdef = new FixtureDef();
        shape = new CircleShape();
        shape.setRadius(0.5f);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public void updatePhysics(Vector2 force){
        this.b2body.applyForce(force, this.b2body.getWorldCenter(), true);
        Vector2 velocity = this.b2body.getLinearVelocity();
        if (velocity.x < -10f) this.b2body.setLinearVelocity(new Vector2(-10f, velocity.y));
        if(velocity.x > 10f) this.b2body.setLinearVelocity(new Vector2(10f, velocity.y));
    }

}
