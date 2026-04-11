package com.oop.wakuwaku.System;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionDetector implements ContactListener {
    private int wallContactCounter;
    private int groundContactCounter;
    public CollisionDetector() {

        wallContactCounter = 0;
        groundContactCounter = 0;
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isTouchingWall() {
        return wallContactCounter > 0;
    }
    public boolean isTouchingGround() {
        return groundContactCounter > 0;
    }

    private boolean checkCollision(Fixture A, Fixture B) {
        return 
        (A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("wall")) ||
        (A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("ground"));
    }

    private boolean checkCollisionWall(Fixture A, Fixture B) {
        return 
        (A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("wall"));
    } 

    private boolean checkCollisionGround(Fixture A, Fixture B) {
        return 
        (A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("ground"));
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (checkCollisionWall(fixtureA, fixtureB)) wallContactCounter++;
        if (checkCollisionWall(fixtureB, fixtureA)) wallContactCounter++;
        if (checkCollisionGround(fixtureA, fixtureB)) groundContactCounter++;
        if (checkCollisionGround(fixtureB, fixtureA)) groundContactCounter++;
        //System.out.println("wall : " + wallContactCounter + "\nground: " + groundContactCounter);
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (checkCollisionWall(fixtureA, fixtureB)) wallContactCounter--;
        if (checkCollisionWall(fixtureB, fixtureA)) wallContactCounter--;
        if (checkCollisionGround(fixtureA, fixtureB)) groundContactCounter--;
        if (checkCollisionGround(fixtureB, fixtureA)) groundContactCounter--;

    }
}
