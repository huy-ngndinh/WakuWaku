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
    private float TILE_PIXEL = 32f;
    private float UNIT = 1f / TILE_PIXEL;

    private Body b2body;
    private CircleShape shape;

    private float stateTimer;
    private int direction;

    public Player(World world) {
        //this.world = world;

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

    

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    // Utility functions

    public void setGravity(float gravity) {
        b2body.setGravityScale(gravity);
    }
    public Body getBody(){
        return this.b2body;
    }

    public void getPos(){
        System.err.println(
        "x : " + this.b2body.getLinearVelocity().x + 
        "\ny : "+ this.b2body.getLinearVelocity().y);
    }

    //Basic movement
    //private final float MAX_SPEED;
    public void moveLeft(){
        //this.b2body.setLinearVelocity(-MAX_SPEED,this.b2body.getLinearVelocity().y);
        if(this.b2body.getLinearVelocity().x >= -2)
            this.b2body.applyLinearImpulse(new Vector2(-2f, 0), this.b2body.getWorldCenter(), true);
    }
    
    public void moveRight(){
        //Vector2 velocity = this.b2body.getLinearVelocity();
        //this.b2body.setLinearVelocity(MAX_SPEED,this.b2body.getLinearVelocity().y);
        if(this.b2body.getLinearVelocity().x <= 2)
            this.b2body.applyLinearImpulse(new Vector2(2f, 0), this.b2body.getWorldCenter(), true);
    }

    public void moveUp(){
        this.b2body.applyLinearImpulse(new Vector2(0,15f), this.b2body.getWorldCenter(), true);
    }

    public void fallDown(){
        //this.b2body.applyLinearImpulse(new Vector2(0,-1f), this.b2body.getWorldCenter(), true);
    } 


    // Dashing
    private float dashTimer = 0;
    private float dashTime = Gdx.graphics.getDeltaTime() * 20;//.. là số frame muốn bị block

    public void dash(float delta){
        Vector2 velocity = this.b2body.getLinearVelocity();
        if(velocity.x > 0){
            this.b2body.applyLinearImpulse(new Vector2(5f,0), this.b2body.getWorldCenter(), true);
            
        }
        else if (velocity.x < 0){
            this.b2body.applyLinearImpulse(new Vector2(-5f, 0), this.b2body.getWorldCenter(), true);
        }
        dashTimer += delta;
    }
    public boolean isDash(){
        return this.dashTimer <= this.dashTime;
    }
    public void resetDashTimer(){
        this.dashTimer = 0;
    }

    //
}
