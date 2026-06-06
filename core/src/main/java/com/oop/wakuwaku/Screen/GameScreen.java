package com.oop.wakuwaku.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.oop.wakuwaku.Exception.OutOfBoundException;
import com.oop.wakuwaku.FactManager.RandomFact;
import com.oop.wakuwaku.Input.GameButton;
import com.oop.wakuwaku.Input.GameInput;
import com.oop.wakuwaku.Input.SettingsPanel;
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

    private final FitViewport viewport = new FitViewport(1280, 720);
    private final Stage stage = new Stage(viewport);
    private final GameButton pauseButton = new GameButton(new Texture("Buttons/Pause.png"), new Texture("Buttons/Pause1.png"), 1200, 646, 70, 64);
    private final SettingsPanel settingsPanel;


    public GameScreen(Main game) {
        this.game = game;
        settingsPanel = new SettingsPanel(game, stage, new Texture("Buttons/settings_panel.png"), new Texture("Buttons/Paw.png"),
                new Texture("Buttons/Bar.png"), new Texture("Buttons/Close.png"), new Texture("Buttons/Close1.png"),
                new Texture("Buttons/Exit.png"), new Texture("Buttons/Exit1.png"));
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

        com.badlogic.gdx.InputMultiplexer multiplexer = new com.badlogic.gdx.InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(input);
        Gdx.input.setInputProcessor(multiplexer);
        pauseBtn();
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        render.updateViewport(width, height);
        // Resize your screen here. The parameters represent the new window size.
        viewport.update(width, height, true);
    }


    @Override
    public void render(float delta) {
        settingsPanel.update(viewport);

        if(settingsPanel.isVisible()) {
            pauseButton.setVisible(false);
        } 
        else {
            pauseButton.setVisible(true);
            input.update(delta);

            logic(delta);


            // if the player touches the goal -> start a timer to the next screen
            if (playerStateHandler.getCurrentState() instanceof Goal) {
                Goal currentState = (Goal) playerStateHandler.getCurrentState();
                if (currentState.frameCountEnded()) {
                    RandomFact fact = new RandomFact();
                    game.setScreen(new ResultScreen(game, fact.getRandomFact()));
                    return;
                }
            }


            try {
                collisionDetector.isInGame();
            } catch (OutOfBoundException e) {
                System.out.println(e.getMessage());
                game.setScreen(new MenuScreen(game));
            }
        }

        stage.act(delta);

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
        game.batch.begin();
        settingsPanel.draw(game.batch);
        game.batch.end();
        stage.draw();
    }

    private void pauseBtn() {
        stage.addActor(pauseButton);
        pauseButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                settingsPanel.setVisible(true);
           }
        });
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
        GameButton.dispose();
    }
}
