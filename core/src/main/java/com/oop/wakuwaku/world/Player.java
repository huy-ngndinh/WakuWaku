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

    private Body b2body;
    private CircleShape shape;

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
        shape = new CircleShape();
        shape.setRadius(0.5f);
        fdef.shape = shape;
        fdef.friction = 0.0f;
        b2body.createFixture(fdef);
        //def state
        direction = 0;
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

    public void jump(int direction, int holdTime){
        float fJump;
        if(holdTime < 30) { fJump = 3f;}
        else if(holdTime < 90) { fJump = 5f;}
        else { fJump = 7f;}
        float fHorizontal = 5f;
        this.b2body.applyLinearImpulse(new Vector2(direction * fHorizontal, fJump), this.b2body.getWorldCenter(), true);
    }

    public void fallDown(){
        float fFall = -4f;
        this.b2body.setLinearVelocity(new Vector2(0, fFall));
    }

    // public void fallLeft() {
    //     // persist momentum
    //     Vector2 currentVelocity = this.b2body.getLinearVelocity();
    //     currentVelocity.x = Math.min(-0.5f, currentVelocity.x);
    //     currentVelocity.y = -3f;
    //     this.b2body.setLinearVelocity(currentVelocity);
    // }

    // public void fallRight() {
    //     // persist momentum
    //     Vector2 currentVelocity = this.b2body.getLinearVelocity();
    //     currentVelocity.x = Math.max(0.5f, currentVelocity.x);
    //     currentVelocity.y = -3f;
    //     this.b2body.setLinearVelocity(currentVelocity);
    // }

    public void hang(){
        this.b2body.setTransform(3f,5f, 0);
    }
    public void slide() {
        this.b2body.setLinearVelocity(new Vector2(0, -0.5f));
    }
    public void climb() {
        this.b2body.setLinearVelocity(new Vector2(0, 1f));
    }

    public void wallSprint(int direction) {
        if (direction == -1) this.b2body.applyLinearImpulse(new Vector2(1f, 5f), this.b2body.getWorldCenter(), true);
        else this.b2body.applyLinearImpulse(new Vector2(-1f, 5f), this.b2body.getWorldCenter(), true);
    }

    public void wall_kick(int direction) {
        if (direction == 1) {
            // wall kick to right
            this.b2body.applyLinearImpulse(new Vector2(5f, 3f), this.b2body.getWorldCenter(), true);
            setDirection(1);
        } else {
            // wall kick to left
            this.b2body.applyLinearImpulse(new Vector2(-5f, 3f), this.b2body.getWorldCenter(), true);
            setDirection(-1);
        }
    }
}
