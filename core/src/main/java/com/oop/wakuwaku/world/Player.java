package com.oop.wakuwaku.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.oop.wakuwaku.System.PlayerStateHandler;

/**
 * This class manages the main character.
 */
public class Player extends Sprite{
    private float TILE_PIXEL = 32f;
    private float UNIT = 1f / TILE_PIXEL;

    private Body b2body;
    private CircleShape shape;

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
    }

    private int direction = 0;

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    // Utility functions
    public boolean isTouchingGround() {
        return true;
    }

    public void setGravity(float gravity) {
        b2body.setGravityScale(gravity);
    }

    public void stickRight() {
        Vector2 vel = b2body.getLinearVelocity();
        if (vel.y > 0) b2body.setLinearVelocity(new Vector2(0, 0));
        else b2body.setLinearVelocity(new Vector2(0, vel.y));
    }

    public void stickLeft() {
        Vector2 vel = b2body.getLinearVelocity();
        if (vel.y > 0) b2body.setLinearVelocity(new Vector2(0, 0));
        else b2body.setLinearVelocity(new Vector2(0, vel.y));
    }

    //Basic movement
    public void moveLeft(){
        this.b2body.applyForce(new Vector2(-30f, 0), this.b2body.getWorldCenter(), true);
        Vector2 velocity = this.b2body.getLinearVelocity();
        if (velocity.x < -10f) this.b2body.setLinearVelocity(new Vector2(-10f, velocity.y));
    }

    public void moveRight(){
        this.b2body.applyForce(new Vector2(30f, 0), this.b2body.getWorldCenter(), true);
        Vector2 velocity = this.b2body.getLinearVelocity();
        if (velocity.x > 10f) this.b2body.setLinearVelocity(new Vector2(10f, velocity.y));
    }

    public void moveUp() {
        this.b2body.applyForce(new Vector2(0f, 10f), this.b2body.getWorldCenter(), true);
        Vector2 velocity = this.b2body.getLinearVelocity();
        if (velocity.y > 2f) this.b2body.setLinearVelocity(new Vector2(velocity.x, 2f));
    }

    private boolean jumpFlag = false;

    public void resetJumpFlag() {
        jumpFlag = false;
    }

    public void jump(Vector2 impulse){
        if (!jumpFlag) {
            this.b2body.applyLinearImpulse(impulse, this.b2body.getWorldCenter(), true);
            jumpFlag = true;
        }
    }

    private boolean wallKickFlag = false;

    public void resetWallKickFlag() {
        wallKickFlag = false;
    }

    public void wall_kick() {
        if (!wallKickFlag) {
            this.b2body.applyLinearImpulse(new Vector2(-direction * 2f, 3f), this.b2body.getWorldCenter(), true);
            wallKickFlag = true;
        }
    }

    // Dashing
    private int cnt_dash_screen = 0;
    private boolean isDashing = false;
    private float cooldown = 0f;

    public boolean isDash(){
        return isDashing;
    }
    public void setDash(){
        isDashing = true;
    }

    public void dash(float delta){
        Vector2 velocity = this.b2body.getLinearVelocity();
        if(velocity.x > 0){
            this.b2body.applyLinearImpulse(new Vector2(5f,0), this.b2body.getWorldCenter(), true);
            //add block timer
            // stop after 20 frames (assuming 60 FPS, this is about 0.166 seconds)
            if(cnt_dash_screen >= 20){
                this.b2body.setLinearVelocity(new Vector2(0,0));
                cnt_dash_screen = 0;
                isDashing = false;
            } else {
                cnt_dash_screen++;
            }
        }
        else if (velocity.x < 0){
            this.b2body.applyLinearImpulse(new Vector2(-5f, 0), this.b2body.getWorldCenter(), true);
            //add block timer
            // blockTimer = 0.1f; // Block input for 0.1 seconds
            if(cnt_dash_screen >= 20){
                this.b2body.setLinearVelocity(new Vector2(0,0));
                cnt_dash_screen = 0;
                isDashing = false;
            } else {
                cnt_dash_screen++;
            }
        }
    }




}
