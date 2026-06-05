package com.oop.wakuwaku.System;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class CollisionDetector implements ContactListener {

    private boolean leftWallContact;
    private boolean rightWallContact;

    private boolean groundContact;

    private boolean leftHookContact;
    private boolean rightHookContact;

    private boolean goalContact;

    private float hookBoundingBoxTop;

    public CollisionDetector() {
        leftWallContact = false;
        rightWallContact = false;
        groundContact = false;
        leftHookContact = false;
        rightHookContact = false;
    }

    public void resetContact() {
        leftWallContact = false;
        rightWallContact = false;
        groundContact = false;
        leftHookContact = false;
        rightHookContact = false;
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (checkWallCollision(fixtureA, fixtureB) || checkWallCollision(fixtureB, fixtureA)) {
            WorldManifold worldManifold = contact.getWorldManifold();
            Vector2 normal = worldManifold.getNormal();
            if (!fixtureB.getBody().getUserData().equals("player"))
                normal.scl(-1);
            if (normal.x > 0.2f) {
                leftWallContact = true;
            }
            if (normal.x < -0.2f) {
                rightWallContact = true;
            }
        }
        if (checkGroundCollision(fixtureA, fixtureB) || checkGroundCollision(fixtureB, fixtureA)) {
            WorldManifold worldManifold = contact.getWorldManifold();
            Vector2 normal = worldManifold.getNormal();
            if (fixtureA.getBody().getUserData().equals("player"))
                normal.scl(-1);
            if (normal.y > 0.2f) {
                groundContact = true;
            }
        }
        if (checkHookCollision(fixtureA, fixtureB) || checkHookCollision(fixtureB, fixtureA)) {
            WorldManifold worldManifold = contact.getWorldManifold();
            Vector2 normal = worldManifold.getNormal();
            if (!fixtureB.getBody().getUserData().equals("player")) {
                normal.scl(-1);
                setHookBoundingBoxTop(getBoundingBoxTop(fixtureB));
            } else {
                setHookBoundingBoxTop(getBoundingBoxTop(fixtureA));
            }
            if (normal.x > 0.2f) {
                leftHookContact = true;
            }
            if (normal.x < -0.2f){
                rightHookContact = true;
            }
        }
        if (checkGoalCollision(fixtureA, fixtureB) || checkGoalCollision(fixtureB, fixtureA)) {
            goalContact = true;
        }
    }

    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    public boolean isTouchingWall() {
        return leftWallContact || rightWallContact;
    }

    public boolean isTouchingLeftWall() {
        return leftWallContact;
    }

    public boolean isTouchingRightWall() {
        return rightWallContact;
    }

    public boolean isTouchingGround() {
        return groundContact;
    }

    public boolean isTouchingHook() {
        return leftHookContact || rightHookContact;
    }

    public boolean isTouchingGoal() {
        return goalContact;
    }

    private boolean checkHookCollision(Fixture A, Fixture B) {
        return (A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("hook"));
    }

    private boolean checkWallCollision(Fixture A, Fixture B) {
        return (A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("wallclimb"));
    }

    private boolean checkGroundCollision(Fixture A, Fixture B) {
        return (A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("ground"));
    }

    private boolean checkGoalCollision(Fixture A, Fixture B) {
        return (A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("goal"));
    }

    /**
     * Get the top Y coordinate of the fixture
     * @param fixture The fixture
     * @return Top Y coordinate value
     */
    private float getBoundingBoxTop(Fixture fixture) {
        PolygonShape shape = (PolygonShape) fixture.getShape();
        Vector2 v = new Vector2();
        float topY = Float.NEGATIVE_INFINITY;

        for (int i = 0; i < shape.getVertexCount(); i++) {
            shape.getVertex(i, v);
            topY = Math.max(topY, fixture.getBody().getPosition().y + v.y);
        }
        return topY;
    }

    private void setHookBoundingBoxTop(float value) {
        hookBoundingBoxTop = value;
    }

    public float getHookBoundingBoxTop() {
        return hookBoundingBoxTop;
    }

    @Override
    public void beginContact(Contact contact) {
    }

    @Override
    public void endContact(Contact contact) {
    }
}
