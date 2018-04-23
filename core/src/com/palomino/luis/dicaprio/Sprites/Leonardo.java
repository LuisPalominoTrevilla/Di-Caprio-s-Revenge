package com.palomino.luis.dicaprio.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.palomino.luis.dicaprio.DiCaprio;
import com.palomino.luis.dicaprio.Scenes.Hud;

/**
 * Created by Carlos on 4/14/2016.
 */
public class Leonardo extends Sprite{
    public enum State {DUCKING, JUMPING, STANDING, RUNNING}
    public State currentState;
    public State previousState;
    private Animation leoRun;
    private TextureRegion leoJump;
    private TextureRegion leoDuck;

    public boolean nextLevel;

    //temporal TextureRegions and ANimations
    private Animation leoRun1;
    private TextureRegion leoJump1;
    private TextureRegion leoDuck1;
    private Animation leoRun2;
    private TextureRegion leoJump2;
    private TextureRegion leoDuck2;
    private TextureRegion leoStand1;
    private TextureRegion leoStand2;

    private Array<TextureRegion> frames;

    private boolean runningRight;
    private float stateTimer;

    public World world;
    public Body b2body;
    private TextureRegion leo;

    private Array<Bullet> bullets;
    public boolean Player2;

    //Life and amo variables
    public int arma;
    public Integer amoWeapon1;
    public Integer amoWeapon2;
    public Integer chargerWeapon1;
    public Integer chargerWeapon2;
    public static final Integer charger1 = 10;
    public static final Integer charger2 = 6;

    public Integer Totalamo;
    public Integer chargerAmo;
    public Integer health;
    public Integer score;


    public Leonardo(World world, boolean Player2){

        health = 100;
        arma = 1;
        chargerWeapon1 = 10;
        chargerWeapon2 = 6;
        amoWeapon1 = 90;
        amoWeapon2 = 20;
        score = 0;

        chargerAmo = chargerWeapon1;
        Totalamo = amoWeapon1;

        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        this.Player2 = Player2;

        frames = new Array<TextureRegion>();

        if(!Player2){
            leoStand1 = new TextureRegion(new Texture("LD CAMINAR 1.png"));
            frames.add(new TextureRegion(new Texture("LD CAMINAR 1.png")));
            frames.add(new TextureRegion(new Texture("LD CAMINAR 2.png")));
            frames.add(new TextureRegion(new Texture("LD CAMINAR 3.png")));
            frames.add(new TextureRegion(new Texture("LD CAMINAR 4.png")));
            frames.add(new TextureRegion(new Texture("LD CAMINAR 5.png")));
            frames.add(new TextureRegion(new Texture("LD CAMINAR 6.png")));
            frames.add(new TextureRegion(new Texture("LD CAMINAR 7.png")));
            frames.add(new TextureRegion(new Texture("LD CAMINAR 8.png")));
            leoRun1 = new Animation(0.1f, frames);
            frames.clear();
            leoJump1 = new TextureRegion(new Texture("LD SALTAR 1.png"));
            leoDuck1 = new TextureRegion(new Texture("LD AGACHARSE 1.png"));

            leoStand2 = new TextureRegion(new Texture("LD2 CAMINAR 1.png"));
            frames.add(new TextureRegion(new Texture("LD2 CAMINAR 1.png")));
            frames.add(new TextureRegion(new Texture("LD2 CAMINAR 2.png")));
            frames.add(new TextureRegion(new Texture("LD2 CAMINAR 3.png")));
            frames.add(new TextureRegion(new Texture("LD2 CAMINAR 4.png")));
            frames.add(new TextureRegion(new Texture("LD2 CAMINAR 5.png")));
            frames.add(new TextureRegion(new Texture("LD2 CAMINAR 6.png")));
            frames.add(new TextureRegion(new Texture("LD2 CAMINAR 7.png")));
            frames.add(new TextureRegion(new Texture("LD2 CAMINAR 8.png")));
            leoRun2 = new Animation(0.1f, frames);
            frames.clear();
            leoJump2 = new TextureRegion(new Texture("LD2 SALTAR.png"));
            leoDuck2 = new TextureRegion(new Texture("LD2 AGACHARSE.png"));
        }else {
            leoStand1 = new TextureRegion(new Texture("LDK CAMINAR 1.png"));
            frames.add(new TextureRegion(new Texture("LDK CAMINAR 1.png")));
            frames.add(new TextureRegion(new Texture("LDK CAMINAR 2.png")));
            frames.add(new TextureRegion(new Texture("LDK CAMINAR 3.png")));
            frames.add(new TextureRegion(new Texture("LDK CAMINAR 4.png")));
            frames.add(new TextureRegion(new Texture("LDK CAMINAR 5.png")));
            frames.add(new TextureRegion(new Texture("LDK CAMINAR 6.png")));
            frames.add(new TextureRegion(new Texture("LDK CAMINAR 7.png")));
            frames.add(new TextureRegion(new Texture("LDK CAMINAR 8.png")));
            leoRun1 = new Animation(0.1f, frames);
            frames.clear();
            leoJump1 = new TextureRegion(new Texture("LDK SALTAR 1.png"));
            leoDuck1 = new TextureRegion(new Texture("LDK AGACHARSE 1.png"));

            leoStand2 = new TextureRegion(new Texture("LDK2 CAMINAR 1.png"));
            frames.add(new TextureRegion(new Texture("LDK2 CAMINAR 1.png")));
            frames.add(new TextureRegion(new Texture("LDK2 CAMINAR 2.png")));
            frames.add(new TextureRegion(new Texture("LDK2 CAMINAR 3.png")));
            frames.add(new TextureRegion(new Texture("LDK2 CAMINAR 4.png")));
            frames.add(new TextureRegion(new Texture("LDK2 CAMINAR 5.png")));
            frames.add(new TextureRegion(new Texture("LDK2 CAMINAR 6.png")));
            frames.add(new TextureRegion(new Texture("LDK2 CAMINAR 7.png")));
            frames.add(new TextureRegion(new Texture("LDK2 CAMINAR 8.png")));
            leoRun2 = new Animation(0.1f, frames);
            frames.clear();
            leoJump2 = new TextureRegion(new Texture("LDK2 SALTAR 2.png"));
            leoDuck2 = new TextureRegion(new Texture("LDK2 AGACHARSE 2.png"));
        }

        leoRun = leoRun1;
        leo = leoStand1;
        leoJump = leoJump1;
        leoDuck = leoDuck1;
        defineLeonardo();

        setBounds(0, 0, 140 / DiCaprio.PPM, 160 / DiCaprio.PPM);
        setRegion(leo);

        bullets = new Array<Bullet>();
    }

    public void changeWeapon(){
        if(arma == 1){
            //Create array of texture regions
            leoRun = leoRun2;
            leo = leoStand2;
            leoJump = leoJump2;
            leoDuck = leoDuck2;
            arma = 2;
            amoWeapon1 = Totalamo;
            chargerWeapon1 = chargerAmo;
            Totalamo = amoWeapon2;
            chargerAmo = chargerWeapon2;
        }else{
            leoRun = leoRun1;
            leo = leoStand1;
            leoJump = leoJump1;
            leoDuck = leoDuck1;
            arma = 1;
            amoWeapon2 = Totalamo;
            chargerWeapon2 = chargerAmo;
            Totalamo = amoWeapon1;
            chargerAmo = chargerWeapon1;
        }
    }


    public void update(float dt){

            setRegion(getFrame(dt));
            if (!Player2) {
                if (currentState == State.DUCKING) {
                    if (runningRight) {
                        setBounds((b2body.getPosition().x - getWidth() / 2) + .1f, (b2body.getPosition().y - getHeight() / 2) - .14f, 140 / DiCaprio.PPM, 160 / DiCaprio.PPM);
                    } else {
                        setBounds((b2body.getPosition().x - getWidth() / 2) - .1f, (b2body.getPosition().y - getHeight() / 2) - .14f, 140 / DiCaprio.PPM, 160 / DiCaprio.PPM);
                    }

                } else {
                    if (runningRight) {
                        setBounds((b2body.getPosition().x - getWidth() / 2) + .1f, (b2body.getPosition().y - getHeight() / 2) - .015f, 140 / DiCaprio.PPM, 160 / DiCaprio.PPM);
                    } else {
                        setBounds((b2body.getPosition().x - getWidth() / 2) - .1f, (b2body.getPosition().y - getHeight() / 2) - .015f, 140 / DiCaprio.PPM, 160 / DiCaprio.PPM);
                    }

                }
            } else {
                if (currentState == State.DUCKING) {
                    if (runningRight) {
                        setBounds((b2body.getPosition().x - getWidth() / 2) + .1f, (b2body.getPosition().y - getHeight() / 2) - .11f, 140 / DiCaprio.PPM, 180 / DiCaprio.PPM);
                    } else {
                        setBounds((b2body.getPosition().x - getWidth() / 2) - .1f, (b2body.getPosition().y - getHeight() / 2) - .11f, 140 / DiCaprio.PPM, 180 / DiCaprio.PPM);
                    }

                } else {
                    if (runningRight) {
                        setBounds((b2body.getPosition().x - getWidth() / 2) + .1f, (b2body.getPosition().y - getHeight() / 2), 140 / DiCaprio.PPM, 180 / DiCaprio.PPM);
                    } else {
                        setBounds((b2body.getPosition().x - getWidth() / 2) - .1f, (b2body.getPosition().y - getHeight() / 2), 140 / DiCaprio.PPM, 180 / DiCaprio.PPM);
                    }

                }
            }

            for (Bullet bullet : bullets) {
                bullet.update(dt);
                if (bullet.isDestroyed())
                    bullets.removeValue(bullet, true);
            }
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = leoJump;
            case RUNNING:
                region = leoRun.getKeyFrame(stateTimer, true);
                break;
            case DUCKING:
                region = leoDuck;
                break;
            case STANDING:
            default:
                region = leo;
                break;
        }

        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 ||runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer+dt: 0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(!Player2){
            if(b2body.getLinearVelocity().y > 0){
                return State.JUMPING;
            }
            else if (b2body.getLinearVelocity().x != 0){
                return State.RUNNING;
            }else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                return State.DUCKING;
            }
            else{
                return State.STANDING;
            }
        }else {
            if(b2body.getLinearVelocity().y > 0){
                return State.JUMPING;
            }
            else if (b2body.getLinearVelocity().x != 0){
                return State.RUNNING;
            }else if (Gdx.input.isKeyPressed(Input.Keys.S)){
                return State.DUCKING;
            }
            else{
                return State.STANDING;
            }
        }
    }

    public void defineLeonardo(){
        BodyDef bdef = new BodyDef();
        if(!Player2){
            bdef.position.set(128 / DiCaprio.PPM, 128 / DiCaprio.PPM);
        }else {
            bdef.position.set(192 / DiCaprio.PPM, 128 / DiCaprio.PPM);
        }

        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10 / DiCaprio.PPM, 75 / DiCaprio.PPM);
        fdef.filter.categoryBits = DiCaprio.LEO_BIT;
        fdef.filter.maskBits = DiCaprio.GROUND_BIT | DiCaprio.AMO_BIT | DiCaprio.HEALTH_BIT | DiCaprio.BOSS_BIT | DiCaprio.OBJECT_BIT | DiCaprio.ENEMY_BIT | DiCaprio.SPIT_BIT | DiCaprio.LEO_BIT | DiCaprio.META_BIT | DiCaprio.LASER_BIT |DiCaprio.COLLECTIBLE_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void fire(){
        bullets.add(new Bullet(world, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false, this));
    }

    public void draw(Batch batch){
        super.draw(batch);
        for(Bullet bullet : bullets)
            bullet.draw(batch);
    }

    public int getHealth(){
        return health;
    }

    public void subtractAmo(){
        chargerAmo--;
    }

    public int getChargerAmo(){
        return chargerAmo;
    }

    public void subtractHealth(int value){
        if((health - value) >= 0){
            health -= value;
        }else {
            health = 0;
        }
    }
    public void addHealth(int value){
        if((health + value) <= 100){
            health += value;
        }
        else{
            health = 100;
        }
    }

    public void reload(){
        if(arma == 1){
            if(chargerAmo < charger1){
                if(Totalamo > 0){
                    int tmp = (charger1-chargerAmo);
                    if((Totalamo - tmp) >= 0){
                        DiCaprio.reload.play();
                        chargerAmo = charger1;
                        addAmo(-tmp);
                    }else{
                        chargerAmo = Totalamo + chargerAmo;
                        addAmo(-Totalamo);
                    }
                }
            }
        }else{
            if(chargerAmo < charger2){
                if(Totalamo > 0) {
                    int tmp = (charger2 - chargerAmo);
                    DiCaprio.reload.play();
                    if((Totalamo - tmp) >= 0) {
                        chargerAmo = charger2;
                        addAmo(-tmp);
                    }else{
                        chargerAmo = Totalamo + chargerAmo;
                        addAmo(-Totalamo);
                    }
                }
            }
        }
    }

    public void addAmo(int value){
        Totalamo += value;
    }

    public void addScore(int value){
        score += value;
    }

    public boolean isNextLevel(){
        return nextLevel;
    }


}
