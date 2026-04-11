package com.oop.wakuwaku.System;

import javax.swing.text.StyledEditorKit;

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
        return Ground > 0;
    }

    private boolean checkCollisionWall(Fixture A, Fixture B) {
        return 
        (A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("wall"));
    }
    private boolean checkCollisionGround(Fixture A, Fixture B) {
        return 
        (A.getBody().getUserData().equals("player") && B.getBody().getUserData().equals("wall"));
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (checkCollisionWall(fixtureA, fixtureB)) wallContactCounter++;
        if (checkCollisionWall(fixtureB, fixtureA)) wallContactCounter++;

       // System.out.println(fixtureA.toString() + ' ' + fixtureB.toString());
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (checkCollision(fixtureA, fixtureB)) wallContactCounter--;
        if (checkCollision(fixtureB, fixtureA)) wallContactCounter--;
    }
}
