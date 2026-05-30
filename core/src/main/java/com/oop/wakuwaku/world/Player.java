package com.oop.wakuwaku.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.oop.wakuwaku.System.Physics;

/**
 * This class manages the main character.
 */
public class Player extends Sprite{

    private final Body b2body;

    private int direction;
    private int jumpDirection;

    public Player(World world) {
        // define player
        BodyDef bdef = new BodyDef();
        bdef.position.set(64f * Physics.UNIT, 150f * Physics.UNIT);
        bdef.type = BodyDef.BodyType.DynamicBody;
        // sau khi def body trong world thì ta sẽ truyền bdef đó vào hàm createBody của world để tạo ra body trong world
        b2body = world.createBody(bdef);
        b2body.setUserData("player");
        // fixture def: chứa hình dạng và physics của body
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f);
        fdef.shape = shape;
        fdef.friction = 0.0f;
        b2body.createFixture(fdef);
        //def state
        direction = 1;
        jumpDirection = 0;
    }

    /*
    Utility function
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setJumpDirection(int jumpDirection) { this.jumpDirection = jumpDirection; }

    public int getJumpDirection() { return this.jumpDirection; }

    public int getDirection() {
        return direction;
    }

    public Vector2 getPosition() {
        return b2body.getPosition();
    }

    public Vector2 getVelocity() { return b2body.getLinearVelocity(); }

    /*
    Basic movements
     */
    public void stop() {
        this.b2body.setLinearVelocity(new Vector2(0, 0));
    }

    public void moveLeft(){
        this.b2body.setLinearVelocity(new Vector2(-3.5f, 0));
    }

    public void moveRight(){
        this.b2body.setLinearVelocity(new Vector2(3.5f, 0));
    }

    public void jump(int holdTime){
        float fJump;
        if(holdTime < 30) { fJump = 3f;}
        else if(holdTime < 90) { fJump = 5f;}
        else { fJump = 7f;}
        float fHorizontal = 5f;
        this.b2body.applyLinearImpulse(new Vector2(this.jumpDirection * fHorizontal, fJump), this.b2body.getWorldCenter(), true);
    }

    public void fallDown(){
        Vector2 currentVelocity = b2body.getLinearVelocity();
        currentVelocity.y = -4f;
        this.b2body.setLinearVelocity(currentVelocity);
    }

    

    public void slide() {
        this.b2body.setLinearVelocity(new Vector2(0, -0.5f));
    }
    public void climbUp(float y) {
        this.b2body.setLinearVelocity(new Vector2(0, y));
    }
    public void climbHorizon(float x){
        // this.b2body.setLinearVelocity(new Vector2(x*direction, 0));
        this.b2body.applyLinearImpulse(new Vector2(x * direction, 0), this.b2body.getWorldCenter(), true);
        // this.b2body.setTransform(new Vector2(this.b2body.getPosition().x + x*direction,this.b2body.getPosition().y), 0f);
    }

    public void wall_kick(int holdTime) {
        float fJump;
        if(holdTime < 30) {
            fJump = 3f;
        } else if(holdTime < 90) {
            fJump = 5f;
        } else {
            fJump = 7f;
        }
        float fHorizontal = 5f;
        this.b2body.applyLinearImpulse(new Vector2(fHorizontal * direction, fJump), this.b2body.getWorldCenter(), true);
    }

    public void setGravity(float gravity) {
        this.b2body.setGravityScale(gravity);
    }
}
