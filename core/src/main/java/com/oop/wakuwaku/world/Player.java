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

    public enum State{FALLING,JUMPING,STANDING,RUNNING,CLIMBING};
    public State curState;
    public State preState;
    private int faceDirection;
    private float stateTimer;

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
        curState = State.STANDING;
        preState = State.STANDING;
        stateTimer = 0;
        faceDirection = 1;//default facing right 
    }

    
    
    // Utility functions
    public boolean isTouchingGround() {
        return true;// Kiểm tra nếu player đang chạm đất (đơn giản bằng cách kiểm tra nếu vận tốc y gần bằng 0);
    }
    public void getPos(){
        System.err.println(
        "x : " + this.b2body.getLinearVelocity().x + 
        "\ny : "+ this.b2body.getLinearVelocity().y);
    }
    // thiết lập máy trạng thái (FSM)

    public State getState(){
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && preState == State.JUMPING))
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else return State.STANDING;
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

    public void jump(){
        //this.b2body.applyForce(new Vector2(0, 100f), this.b2body.getWorldCenter(), true);
        this.b2body.applyLinearImpulse(new Vector2(0, 10f), this.b2body.getWorldCenter(), true);
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
