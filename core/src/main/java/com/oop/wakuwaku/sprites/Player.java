package com.oop.wakuwaku.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
public class Player extends Sprite{
    private float TILE_PIXEL = 32f;
    private float UNIT = 1f / TILE_PIXEL; 
    
    private World world; 
    private Body b2body;
    private CircleShape shape;
    private int jumpCount = 0;
    private static final int MAX_JUMPS = 1;  // Single jump - nhảy 1 lần cho đến khi chạm đất
    private int groundContactCount = 0;  // Đếm số va chạm với mặt đất

    public Player(World world) {
        this.world = world;

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

    public Body getBody() {
        return this.b2body;
    }
    /**
     * Kiểm tra nhân vật có chạm đất không
     * @return true nếu chạm đất, false nếu đang bay
     */
    public boolean isGrounded() {
        return groundContactCount > 0;
    }
    
    /**
     * Thiết lập trạng thái chạm đất (được gọi từ ContactListener)
     * @param grounded true khi bắt đầu va chạm, false khi kết thúc va chạm
     */
    public void setGrounded(boolean grounded) {
        if (grounded) {
            groundContactCount++;
        } else {
            groundContactCount = Math.max(0, groundContactCount - 1);
        }
    }
    
    /**
     * Kiểm tra có thể nhảy không (còn lượng nhảy)
     * @return true nếu còn có thể nhảy, false nếu đã hết lượt nhảy
     */
    public boolean canJump() {
        return jumpCount < MAX_JUMPS;
    }
    
    /**
     * Thực hiện nhảy (tăng jumpCount)
     */
    public void jump() {
        if (canJump()) {
            jumpCount++;
        }
    }
    
    /**
     * Reset số lần nhảy khi chạm đất
     */
    public void resetJumpCount() {
        jumpCount = 0;
    }
    
}   
