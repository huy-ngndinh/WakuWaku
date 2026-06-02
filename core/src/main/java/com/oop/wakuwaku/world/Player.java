package com.oop.wakuwaku.world;

import com.badlogic.gdx.Gdx;
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
    public static float TILE_PIXEL = 32f;
    public static float UNIT = 1f / TILE_PIXEL;

    private Body b2body;
    private CircleShape shape;

    private int direction;
    // dash
    private float dashTimer = 0;
    private float dashTime = Gdx.graphics.getDeltaTime() * 10;//.. là số frame muốn bị block

    public Player(World world) {
        // define player
        BodyDef bdef = new BodyDef();
        bdef.position.set(64f * UNIT, 150f * UNIT);
        bdef.type = BodyDef.BodyType.DynamicBody;
        // sau khi def body trong world thì ta sẽ truyền bdef đó vào hàm createBody của world để tạo ra body trong world
        b2body = world.createBody(bdef);
        b2body.setUserData("player");
        // fixture def: chứa hình dạng và physics của body
        FixtureDef fdef = new FixtureDef();
        shape = new CircleShape();
        shape.setRadius(0.5f);
        fdef.shape = shape;
        b2body.createFixture(fdef);
        //def state
        direction = 0;
    }

    public Body getBody(){
        return this.b2body;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public Vector2 getPosition() {
        return b2body.getPosition();
    }

    //Basic movement
    public void stop() {
        this.b2body.setLinearVelocity(new Vector2(0, 0));
    }

    public void moveLeft(){
        this.b2body.setLinearVelocity(new Vector2(-3.5f, 0));
    }

    public void moveRight(){
        this.b2body.setLinearVelocity(new Vector2(3.5f, 0));
    }

    public void jump(){
        Vector2 currentVelocity = this.b2body.getLinearVelocity();
        if (currentVelocity.x < 0) this.b2body.applyLinearImpulse(new Vector2(-2f,5f), this.b2body.getWorldCenter(), true);
        else if (currentVelocity.x > 0) this.b2body.applyLinearImpulse(new Vector2(2f, 5f), this.b2body.getWorldCenter(), true);
        else this.b2body.applyLinearImpulse(new Vector2(0f, 5f), this.b2body.getWorldCenter(), true);
    }

    public void fallDown(){
        //this.b2body.applyLinearImpulse(new Vector2(0,-1f), this.b2body.getWorldCenter(), true);
    }

    // Dashing
    public void dash(float delta){
        // only dash at the first frame of the DASH state
        if (notDash()) {
            Vector2 velocity = this.b2body.getLinearVelocity();
            if (velocity.x > 0) {
                this.b2body.setLinearVelocity(new Vector2(25f, 0));
            } else if (velocity.x < 0) {
                this.b2body.setLinearVelocity(new Vector2(-25f, 0));
            }
        }
        dashTimer += delta;
    }

    private boolean notDash(){
        return this.dashTimer == 0;
    }

    public boolean isInDash(){
        return this.dashTimer <= this.dashTime;
    }

    public void resetDashTimer(){
        this.dashTime = Gdx.graphics.getDeltaTime() * 10;
        this.dashTimer = 0;
    }

    public void climb() {
        this.b2body.setLinearVelocity(new Vector2(0, 2f));
    }

    public void wall_kick(int direction) {
        if (direction == 0) {
            // wall kick to right
            this.b2body.applyLinearImpulse(new Vector2(3.5f, 2.5f), this.b2body.getWorldCenter(), true);
            setDirection(1);
        } else {
            // wall kick to left
            this.b2body.applyLinearImpulse(new Vector2(-3.5f, 2.5f), this.b2body.getWorldCenter(), true);
            setDirection(0);
        }
    }
}
