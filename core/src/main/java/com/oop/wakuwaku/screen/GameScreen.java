package com.oop.wakuwaku.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.wakuwaku.Main;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen implements Screen {
    
    private Main game;
    //gamecam
    private Viewport viewport;
    private OrthographicCamera camera;

    //Tile map
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer render;

    //Box2d
    private World world;
    private Box2DDebugRenderer b2dr;
    
    public GameScreen(Main game){
        this.game = game;
        //set the camera
        camera = new OrthographicCamera();
        viewport = new FitViewport(400,208, camera);
        camera.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);
       //texture = new Texture("background.png");
    
       //load map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map.tmx");
        render = new OrthogonalTiledMapRenderer(map);        
        
        //gameWorld;
        //dosleep = true <=> object đó có được bật để tính toán trong mô phỏng vật lí không, các obj fixed thì ko cần tính toán 
        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        //ground
        for(MapObject object : map.getLayers().get(4).getObjects()){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth()/2, rect.getY() + rect.getHeight()/2);
            
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);//giao tiếp với bdef để lấy addr trung tâm của obj
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //coins
        for(MapObject object : map.getLayers().get(4).getObjects()){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth()/2, rect.getY() + rect.getHeight()/2);
            
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);//giao tiếp với bdef để lấy addr trung tâm của obj
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }
    
    @Override
    public void show() {
         // This method is called when this screen becomes the current screen for a Game.
    }
    public void input(float dt){
        if(Gdx.input.isKeyPressed(Keys.D)){
            camera.translate(100*dt, 0);
        }
        else if(Gdx.input.isKeyPressed(Keys.A)){
            camera.translate(-100*dt, 0);
        }
    }
    public void update(float dt){
        input(dt);
        //camera.update(); 
        render.setView(camera);
    }
    @Override
    public void render(float dt) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        update(dt);
        logic();
        draw();
    }

    private void logic(){
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
    }
    private void draw(){
        
        ScreenUtils.clear(Color.WHITE);
        viewport.apply();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        game.batch.begin();
        //DRAW FROM HERE
        render.render();
        b2dr.render(world,camera.combined);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        // Resize your screen here. The parameters represent the new window size.
        viewport.update(width, height, true); // true centers the camera
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}