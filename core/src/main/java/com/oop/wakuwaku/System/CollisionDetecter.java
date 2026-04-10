package com.oop.wakuwaku.System;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionDetecter implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();
        System.out.println("Collision detected between " + A.getBody().getUserData() + " and " + B.getBody().getUserData());
    }   
    @Override
    public void endContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();
        System.out.println("Collision ended between " + A.getBody().getUserData() + " and " + B.getBody().getUserData());
    }
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
