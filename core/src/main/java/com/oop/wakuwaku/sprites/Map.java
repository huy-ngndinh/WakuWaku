package com.oop.wakuwaku.sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Map {
    private static final float TILE_PIXEL = 32f;
    private static final float UNIT = 1f / TILE_PIXEL;
    
    private BodyDef bdef;
    private PolygonShape shape;
    private FixtureDef fdef;
    private World world;
    private TiledMap map;

    private Body body;
    public Map(World world, TiledMap map) {
        this.world = world;
        this.map = map;
        bdef = new BodyDef();
        shape = new PolygonShape();
        fdef = new FixtureDef();

        // create map objects
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            // define body for the all map object
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) * UNIT, (rect.getY() + rect.getHeight() / 2) * UNIT);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) * UNIT, (rect.getHeight() / 2) * UNIT);
            fdef.shape = shape;
            
            body.createFixture(fdef);
        }
    }

}
