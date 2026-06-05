package com.oop.wakuwaku.world;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * This class manages the <code>TiledMap</code> instance.
 */
public class Map {
    private static final float TILE_PIXEL = 32f;
    private static final float UNIT = 1f / TILE_PIXEL;

    private BodyDef bdef;
    private PolygonShape shape;
    private FixtureDef fdef;
    private TiledMap map;
    private Body body;

    public Map(World world) {

        map = new TmxMapLoader().load("./asset_work/maps/house.tmx");
        bdef = new BodyDef();
        shape = new PolygonShape();
        fdef = new FixtureDef();
        // create collision hitbox
        // use layer name instead of layer id because id depends on layer order in tsx file
        createBody(world, map.getLayers().get("ground").getObjects(), "ground");
        createBody(world, map.getLayers().get("wallcollision").getObjects(), "wallcollision");
        createBody(world, map.getLayers().get("wallclimb").getObjects(), "wallclimb");
        createBody(world, map.getLayers().get("hook").getObjects(), "hook");
        createBody(world, map.getLayers().get("goal").getObjects(), "goal");
    }

    private void createBody(World world, MapObjects mapObjects, String type) {
        for(MapObject object : mapObjects) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            // define body for the all map object
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) * UNIT, (rect.getY() + rect.getHeight() / 2) * UNIT);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) * UNIT, (rect.getHeight() / 2) * UNIT);
            fdef.shape = shape;
            fdef.friction = 0.0f;

            body.createFixture(fdef);

            body.setUserData(type);
        }
    }

    /**
     * Return the <a href="https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/maps/tiled/TiledMap.java"><code>TiledMap</code></a> instance. Only one should exist per level and is managed by this class.
     * @return TiledMap
     */
    public TiledMap getTiledMap(){
        return map;
    }

}
