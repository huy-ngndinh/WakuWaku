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
    // Utility functions
    public boolean isGrounded() {
        // Kiểm tra nếu player đang chạm đất (đơn giản bằng cách kiểm tra nếu vận tốc y gần bằng 0)
        return Math.abs(this.b2body.getLinearVelocity().y) < 0.01f;
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

    private int jumpCount = 0;
    public void jump(){
        if(jumpCount <= 2){
            this.b2body.applyForce(new Vector2(0, 60f), this.b2body.getWorldCenter(), true);
            jumpCount++;
        }
        else{
            jumpCount = 0;
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
