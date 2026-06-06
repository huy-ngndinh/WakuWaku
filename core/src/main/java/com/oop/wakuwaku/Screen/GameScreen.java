package com.oop.wakuwaku.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.oop.wakuwaku.Exception.OutOfBoundException;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.Main;
import com.oop.wakuwaku.State.*;
import com.oop.wakuwaku.System.*;
import com.oop.wakuwaku.world.GameWorld;
import com.oop.wakuwaku.world.Player;

/** First screen of the application.*/
public class GameScreen extends ScreenAdapter {
    private final Main game;

    // Box2d
    private Physics physics;
    private CollisionDetector collisionDetector;
    private PlayerStateHandler playerStateHandler;

    // Animation
    private AnimationHandler animationHandler;

    // Game input
    private GameInput input;

    // Game world (Map, Player)
    private GameWorld gameworld;

    // Renderer
    private Render render;


    public GameScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {

        physics = new Physics();
        collisionDetector = new CollisionDetector();
        physics.getWorld().setContactListener(collisionDetector);
        animationHandler = new AnimationHandler();
        gameworld = new GameWorld(physics.getWorld());
        input = new GameInput();
        playerStateHandler = new PlayerStateHandler(input, collisionDetector, gameworld, animationHandler);
        render = new Render(gameworld.getMap().getTiledMap());

        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        render.updateViewport(width, height);
        // Resize your screen here. The parameters represent the new window size.
    }


    @Override
    public void render(float delta) {
        input.update(delta);

        logic(delta);


        // if the player touches the goal -> start a timer to the next screen
        if (playerStateHandler.getCurrentState() instanceof Goal) {
            Goal currentState = (Goal) playerStateHandler.getCurrentState();
            if (currentState.frameCountEnded()) {
                game.setScreen(new ResultScreen(game, 1));
                return;
            }
        }


        try {
            collisionDetector.isInGame();
        } catch (OutOfBoundException e) {
            System.out.println(e.getMessage());
            game.setScreen(new MenuScreen(game));
        }

        draw(delta);

    }

    private void logic(float delta) {
        // WF : getcurState -> actor thực hiện action -> update

        PlayerState playerState = playerStateHandler.getCurrentState();
        Player player = gameworld.getPlayer();

        switch (playerState.getClass().getSimpleName()) {
            // case stop
            case "Idle":
            case "BeforeJump":
            case "Goal":
                player.stop();
                break;
            // case stop with Y coordinate clamping
            case "WallHanging":
                player.hanging(collisionDetector.getHookBoundingBoxTop());
                break;
            //case move
            case "Walking":
                int direction = player.getDirection();
                if (direction == 1) player.moveRight();
                else player.moveLeft();
                break;

            case "Falling":
                Falling fallState = (Falling) playerState;
                player.fallDown(fallState.getFallCoeff());
                break;

            case "Jump":
                Jump jumpState = (Jump) playerState;
                if (jumpState.isJumpRequest()) {
                    player.jump(input.getHoldTimeSpace());
                    jumpState.turnOffJumpRequest();
                }
                break;
            case "BeforeWallKick":
            case "WallAttach":
                player.slide(); // trượt tường
                break;

            case "WallClimb":
                player.climbUp(1f);
                break;

            case "WallKick":
                WallKick wallKickState = (WallKick) playerState;
                if (wallKickState.isJumpRequest()) {
                    player.wall_kick(input.getHoldTimeSpace());
                    wallKickState.turnOffJumpRequest();
                }
                break;

            //case locked animation
            case "WallClimbOver":
                player.climbUp(3f);
                player.climbHorizon(5f);
                break;
        }
        playerStateHandler.updateState(delta);

    }

    private void draw(float delta){
        // reset
        render.reset();
        // draw map
        render.drawMap(gameworld.getPlayer());
        // update physics
        collisionDetector.resetContact();
        physics.simulate(Gdx.graphics.getDeltaTime());
        // begin rendering
        render.beginRender();
        // draw player/animation
        TextureRegion animationRegion = animationHandler.getCurrentAnimationFrame(delta, gameworld.getPlayer(), playerStateHandler);
        render.drawPlayer(gameworld.getPlayer(), animationRegion);
        if (playerStateHandler.getCurrentState() instanceof BeforeJump) render.drawIndicator(gameworld.getPlayer(), input.getHoldTimeSpace(), 0);
        else if (playerStateHandler.getCurrentState() instanceof BeforeWallKick) render.drawIndicator(gameworld.getPlayer(), input.getHoldTimeSpace(), -gameworld.getPlayer().getDirection());
        render.endRender();
        // debug mode, comment out when finished
//        physics.getDebugRenderer().render(physics.getWorld(), render.getCamera().combined);
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
    public void hide() {}

    @Override
    public void dispose() {

    }
}
