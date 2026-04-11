package com.oop.wakuwaku.System;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class CollisionDetector implements ContactListener {
    private boolean leftWallContact;
    private boolean rightWallContact;
    private boolean groundContact;

    public CollisionDetector() {
        leftWallContact = false;
        rightWallContact = false;
        groundContact = false;
    }

    public void resetCollision() {
        leftWallContact = false;
        rightWallContact = false;
        groundContact = false;
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

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Vector2 normal = contact.getWorldManifold().getNormal();
        leftWallContact |= checkLeftWallCollision(fixtureA, fixtureB, normal) || checkLeftWallCollision(fixtureB, fixtureA, normal);
        rightWallContact |= checkRightWallCollision(fixtureA, fixtureB, normal) || checkRightWallCollision(fixtureB, fixtureA, normal);
        groundContact |= checkGroundCollision(fixtureA, fixtureB, normal) || checkGroundCollision(fixtureB, fixtureA, normal);
    }

    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    @Override
    public void beginContact(Contact contact) {
    }

    @Override
    public void endContact(Contact contact) {
    }

    private boolean checkLeftWallCollision(Fixture A, Fixture B, Vector2 normal) {
        return A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("wall") && normal.x > 0.5f;
    }

    private boolean checkRightWallCollision(Fixture A, Fixture B, Vector2 normal) {
        return A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("wall")  && normal.x < -0.5f;
    }

    private boolean checkGroundCollision(Fixture A, Fixture B, Vector2 normal) {
        return A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("ground");
    }
}
