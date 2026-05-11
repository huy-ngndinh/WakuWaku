package com.oop.wakuwaku.System;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class CollisionDetector implements ContactListener {
    private int wallContactCounter;
    private int groundContactCounter;

    private boolean leftWallContact;
    private boolean rightWallContact;

    public CollisionDetector() {
        wallContactCounter = 0;
        groundContactCounter = 0;
        leftWallContact = false;
        rightWallContact = false;
    }

    public void resetWallContact() {
        leftWallContact = false;
        rightWallContact = false;
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (!checkWallCollision(fixtureA, fixtureB) && !checkWallCollision(fixtureB, fixtureA)) return;

        WorldManifold worldManifold = contact.getWorldManifold();
        Vector2 normal = worldManifold.getNormal();
        if (!fixtureB.getBody().getUserData().equals("player")) normal.scl(-1);

        if (normal.x > 0.5f) {
            leftWallContact = true;
        }

        if (normal.x < -0.5f) {
            rightWallContact = true;
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
        return groundContactCounter > 0;
    }

    private boolean checkCollision(Fixture A, Fixture B) {
        return
        (A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("wall")) ||
        (A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("ground"));
    }

    private boolean checkWallCollision(Fixture A, Fixture B) {
        return (A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("wall"));
    }

    private boolean checkCollisionGround(Fixture A, Fixture B) {
        return
        (A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("ground"));
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (checkCollisionGround(fixtureA, fixtureB)) groundContactCounter++;
        if (checkCollisionGround(fixtureB, fixtureA)) groundContactCounter++;
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (checkCollisionGround(fixtureA, fixtureB)) groundContactCounter--;
        if (checkCollisionGround(fixtureB, fixtureA)) groundContactCounter--;
    }
}
