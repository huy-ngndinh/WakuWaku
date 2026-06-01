package com.oop.wakuwaku.System;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.WorldManifold;

public class CollisionDetector implements ContactListener {

    private boolean leftWallContact;
    private boolean rightWallContact;
    
    private boolean groundContact;

    private boolean leftHookContact;
    private boolean rightHookContact;

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
            if (normal.x > 0.5f) {
                leftWallContact = true;
            }
            if (normal.x < -0.5f) {
                rightWallContact = true;
            }
        }
        if (checkGroundCollision(fixtureA, fixtureB) || checkGroundCollision(fixtureB, fixtureA)) {
            WorldManifold worldManifold = contact.getWorldManifold();
            Vector2 normal = worldManifold.getNormal();
            if (fixtureA.getBody().getUserData().equals("player"))
                normal.scl(-1);
            if (normal.y > 0.5f) {
                groundContact = true;
            }
        }
        if (checkHookCollision(fixtureA, fixtureB) || checkHookCollision(fixtureB, fixtureA)) {
            WorldManifold worldManifold = contact.getWorldManifold();
            Vector2 normal = worldManifold.getNormal();
            if (!fixtureB.getBody().getUserData().equals("player"))
                normal.scl(-1);
            if (normal.x > 0.5f) {
                leftHookContact = true;
            }
            if (normal.x < -0.5f){
                rightHookContact = true;
            }
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

    private boolean checkHookCollision(Fixture A, Fixture B) {
        return (A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("hook"));
    }

    private boolean checkWallCollision(Fixture A, Fixture B) {
        return (A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("wallcoliision"));
    }

    private boolean checkGroundCollision(Fixture A, Fixture B) {
        return (A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("ground"));
    }

    @Override
    public void beginContact(Contact contact) {
    }

    @Override
    public void endContact(Contact contact) {
    }
}
