package com.palomino.luis.dicaprio.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.palomino.luis.dicaprio.DiCaprio;
import com.palomino.luis.dicaprio.Scenes.Hud;
import com.palomino.luis.dicaprio.Sprites.Enemy;
import com.palomino.luis.dicaprio.Sprites.Leonardo;
import com.palomino.luis.dicaprio.Tools.B2WorldCreator;
import com.palomino.luis.dicaprio.Tools.WorldContactListener;

import java.util.Random;

/**
 * Created by Carlos on 5/9/2016.
 */
public class Level3 implements Screen{
    private boolean repeat = true;

    //Create hud
    private Hud hud, hud2;

    //Create Hud textures and animations
    private int gunPosition = -300;
    private int gunPosition2 = -300;
    private Texture gun, gun2, leoHead;
    private TextureRegion currentFrame;
    private Animation healthAnim, healthAnim2;
    Array<TextureRegion> frames;
    private float stateTimer = 0f;

    //Global Variables
    private DiCaprio game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d Variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //Sprites
    private Leonardo player;
    private Leonardo player2;
    private boolean TwoPlayers;

    //Constructor begins
    public Level3(DiCaprio game, boolean TwoPlayers){
        this.TwoPlayers = TwoPlayers;

        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(new Texture("health1.png")));
        frames.add(new TextureRegion(new Texture("health2.png")));
        healthAnim = new Animation(.3f, frames);
        healthAnim2 = new Animation(.3f, frames);

        this.game = game;
        //Create a game cam
        gameCam = new OrthographicCamera();
        //FitViewport to aintain virtual aspect despite screen size
        gamePort = new FitViewport(game.V_WIDTH / game.PPM, game.V_HEIGHT / game.PPM,gameCam);


        //Load the map and set up map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("Cave.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/game.PPM);

        //Set the gamecam to be centered correctly at the start of the game
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();

        Random random = new Random();
        creator = new B2WorldCreator(world, map, game, random.nextInt(4));

        world.setContactListener(new WorldContactListener());

        player = new Leonardo(world, false);
        if(TwoPlayers){
            player2 = new Leonardo(world, true);
            hud2 = new Hud(game.batch, player2, true, 3);
        }
        hud = new Hud(game.batch, player, false, 3);
        //set the sound
        game.levelMusic.play();

    }


    @Override
    public void show() {
        gun = new Texture("gun.png");
        gun2 = new Texture("gun.png");
        leoHead = new Texture("LeoHead.png");
    }

    //Variable for double jump
    private int doubleJump = 2, doubleJump2 = 2;


    public void handleInput(float dt){

        if(player.b2body.getLinearVelocity().y == 0){
            doubleJump = 2;
        }
        if((Gdx.input.isKeyJustPressed(Input.Keys.UP)) && (doubleJump > 0)){
            player.b2body.applyLinearImpulse(new Vector2(0,(2f * doubleJump)), player.b2body.getWorldCenter(), true);
            doubleJump--;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2){
            player.b2body.applyLinearImpulse(new Vector2(0.07f, 0), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2){
            player.b2body.applyLinearImpulse(new Vector2(-0.07f, 0), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            player.reload();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.B)){
            player.changeWeapon();
            if(player.arma == 1){
                gun = new Texture("gun.png");
                gunPosition = -300;
            }else{
                gun = new Texture("shotgun.png");
                gunPosition = -340;
            }
        }
        if((Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) || (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && Gdx.input.isKeyPressed(Input.Keys.DOWN))){
            if(player.getChargerAmo() > 0) {
                player.fire();
                player.subtractAmo();
                //Play sounds
                if(player.arma == 1){
                    game.gun1.play();
                }else{
                    game.gun2.play();
                }
            }else {
                game.trigger2.play();
            }
        }

        if(TwoPlayers){
            SecondPlayerInput(dt);
        }
    }

    public void SecondPlayerInput(float dt){
        if(player2.b2body.getLinearVelocity().y == 0){
            doubleJump2 = 2;
        }
        if((Gdx.input.isKeyJustPressed(Input.Keys.W)) && (doubleJump2 > 0)){
            player2.b2body.applyLinearImpulse(new Vector2(0,(2f * doubleJump2)), player2.b2body.getWorldCenter(), true);
            doubleJump2--;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) && player2.b2body.getLinearVelocity().x <= 2){
            player2.b2body.applyLinearImpulse(new Vector2(0.07f, 0), player2.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) && player2.b2body.getLinearVelocity().x >= -2){
            player2.b2body.applyLinearImpulse(new Vector2(-0.07f, 0), player2.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            player2.reload();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
            player2.changeWeapon();
            if(player2.arma == 1){
                gun2 = new Texture("gun.png");
                gunPosition2 = -300;
            }else{
                gun2 = new Texture("shotgun.png");
                gunPosition2 = -340;
            }
        }
        if((Gdx.input.isKeyJustPressed(Input.Keys.Q)) || (Gdx.input.isKeyJustPressed(Input.Keys.Q) && Gdx.input.isKeyPressed(Input.Keys.S))){
            if(player2.getChargerAmo() > 0) {
                player2.fire();
                player2.subtractAmo();
                //Play sounds
                if(player2.arma == 1){
                    game.gun1.play();
                }else{
                    game.gun2.play();
                }
            }else {
                game.trigger2.play();
            }
        }
    }

    public void checkLimits(float dt){
        if((player.b2body.getPosition().y < 0) && repeat){
            player.subtractHealth(100);
            repeat = false;
        }
        for(Enemy enemy : creator.getEnemies()){
            if((enemy.b2body.getPosition().y < 0) && !enemy.isDestroyed()){
                enemy.setSetToDestroyed();
            }
        }
    }

    public void update(float delta){
        handleInput(delta);
        checkLimits(delta);
        //Check if leoIsDeath
        if(!hud.isAlive){
            Gdx.app.log("Leo", "died:(");
        }

        world.step(1 / 60f, 6, 2);

        player.update(delta);

        if(TwoPlayers){
            player2.update(delta);
            hud2.update(delta);
        }

        for(Enemy enemy: creator.getEnemies()){
            enemy.update(delta);
            if(!enemy.isDestroyed() && enemy.getX() < player.getX() + 960 / DiCaprio.PPM){
                enemy.b2body.setActive(true);
            }
        }

        hud.update(delta);

        if(TwoPlayers)
            gameCam.position.x = (player.b2body.getPosition().x + player2.b2body.getPosition().x)/2;
        else
            gameCam.position.x = player.b2body.getPosition().x;


        gameCam.update();
        renderer.setView(gameCam);

    }

    @Override
    public void render(float delta) {
        //separate our update logic from render
        update(delta);

        //Clear the game screen with black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        //render out Box2DDebugLines
        //b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        if(TwoPlayers){
            player2.draw(game.batch);
        }
        for(Enemy enemy : creator.getEnemies()){
            enemy.draw(game.batch);
        }
        game.batch.end();

        //Set batch to draw what the Hud camera sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        if(TwoPlayers)
            hud2.stage.draw();

        //Render hud graphics
        renderHudGraphics(delta);

        if(TwoPlayers){
            if((player.health <= 0) || (player2.health <= 0)){
                game.setScreen(new GameOver(game));
                dispose();
                DiCaprio.levelMusic.stop();
                DiCaprio.blood.play();
            }
        }else{
            if(player.health <= 0){
                game.setScreen(new GameOver(game));
                dispose();
                DiCaprio.levelMusic.stop();
                DiCaprio.blood.play();
            }
        }

        if (player.isNextLevel()) {
            game.setScreen(new LoadingScreen(3,game, TwoPlayers));
        }
        if(TwoPlayers){
            if (player2.isNextLevel()) {
                game.setScreen(new LoadingScreen(3,game, TwoPlayers));
            }
        }
    }

    public void renderHudGraphics(float dt){

        if(player.getHealth() <20){
            healthAnim.setFrameDuration(.01f);
        } else if(player.getHealth() < 40){
            healthAnim.setFrameDuration(.05f);
        }else if(player.getHealth() < 60){
            healthAnim.setFrameDuration(.1f);
        }else if (player.getHealth() < 80){
            healthAnim.setFrameDuration(.2f);
        }else{
            healthAnim.setFrameDuration(.3f);
        }

        if(TwoPlayers){
            if(player2.getHealth() <20){
                healthAnim2.setFrameDuration(.01f);
            } else if(player2.getHealth() < 40){
                healthAnim2.setFrameDuration(.05f);
            }else if(player2.getHealth() < 60){
                healthAnim2.setFrameDuration(.1f);
            }else if (player2.getHealth() < 80){
                healthAnim2.setFrameDuration(.2f);
            }else{
                healthAnim2.setFrameDuration(.3f);
            }
        }

        stateTimer += Gdx.graphics.getDeltaTime();
        currentFrame = healthAnim.getKeyFrame(stateTimer, true);

        game.batch.begin();
        game.batch.draw(gun, game.V_WIDTH / 2 + gunPosition, game.V_HEIGHT - 64);
        game.batch.draw(currentFrame, game.V_WIDTH / 2 + 80, game.V_HEIGHT - 64);
        game.batch.draw(leoHead, game.V_WIDTH / 2 +270, game.V_HEIGHT - 75);
        if(TwoPlayers){
            currentFrame = healthAnim2.getKeyFrame(stateTimer, true);
            game.batch.draw(currentFrame, game.V_WIDTH / 2 + 80, game.V_HEIGHT - 130);
            game.batch.draw(gun2, game.V_WIDTH / 2 + gunPosition2, game.V_HEIGHT - 130);
        }
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gun.dispose();
        gun2.dispose();
        leoHead.dispose();
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        if(TwoPlayers){
            hud2.dispose();
        }
    }
}
