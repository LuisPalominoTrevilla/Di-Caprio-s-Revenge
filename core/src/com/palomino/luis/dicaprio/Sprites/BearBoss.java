package com.palomino.luis.dicaprio.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.palomino.luis.dicaprio.DiCaprio;
import com.palomino.luis.dicaprio.Scenes.Hud;

/**
 * Created by Carlos on 5/10/2016.
 */
public class BearBoss extends Enemy {
    private float stateTimer;
    private float stateTime;
    private Animation walkAnimation;
    private Animation runAnimation;
    private Animation attackAnimation;
    private Array<TextureRegion> frames;
    int seg, soundType;
    private boolean hit;

    private enum State {STANDING, RUNNING, ATTACKING, DEAD};
    private State currentState;
    private State previousState;

    private int health;

    public BearBoss(World world, float x, float y) {
        super(world, x, y);
        currentState = State.STANDING;
        previousState = State.STANDING;

        hit = false;

        frames = new Array<TextureRegion>();

        frames.add(new TextureRegion(new Texture("bear_run5.png")));
        frames.add(new TextureRegion(new Texture("bear_run4.png")));
        frames.add(new TextureRegion(new Texture("bear_run3.png")));
        frames.add(new TextureRegion(new Texture("bear_run2.png")));
        frames.add(new TextureRegion(new Texture("bear_run1.png")));
        frames.add(new TextureRegion(new Texture("bear_run2.png")));
        frames.add(new TextureRegion(new Texture("bear_run3.png")));
        frames.add(new TextureRegion(new Texture("bear_run4.png")));
        runAnimation = new Animation(0.1f, frames);
        frames.clear();

        frames.add(new TextureRegion(new Texture("bear_walk1.png")));
        frames.add(new TextureRegion(new Texture("bear_walk2.png")));
        frames.add(new TextureRegion(new Texture("bear_walk3.png")));
        frames.add(new TextureRegion(new Texture("bear_walk4.png")));
        frames.add(new TextureRegion(new Texture("bear_walk5.png")));
        walkAnimation = new Animation(0.2f, frames);
        frames.clear();


        frames.add(new TextureRegion(new Texture("bear_attack2.png")));
        frames.add(new TextureRegion(new Texture("bear_attack.png")));
        attackAnimation = new Animation(.5f, frames);
        frames.clear();

        stateTimer = 0;
        setBounds(getX(), getY(), 600 / DiCaprio.PPM, 600 / DiCaprio.PPM);
        health = 1000;

        DiCaprio.bear.play();


        velocity = new Vector2(-.045f,0);

    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroyed && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
        }
        else if(!destroyed){
            if(currentState != State.DEAD) {
                if ((b2body.getLinearVelocity().x <= 3.5f) && (b2body.isActive())) {
                    b2body.applyLinearImpulse(velocity, b2body.getWorldCenter(), true);
                }

                setPosition((b2body.getPosition().x - getWidth() / 2) - .02f, (b2body.getPosition().y - getHeight() / 2) +.06f);

                setRegion(getFrame(dt));
            }else {
                setRegion(destroyedZombi.getKeyFrame(stateTime, true));
                if(stateTime > .4f){
                    setToDestroyed = true;
                }

            }
        }
    }



    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case RUNNING:
                region = runAnimation.getKeyFrame(stateTimer, true);
                velocity = new Vector2(-.055f,0);
                break;
            case STANDING:
                region = walkAnimation.getKeyFrame(stateTimer, true);
                velocity = new Vector2(-.045f,0);
                break;
            case ATTACKING:
                if(stateTime < .5f){
                    region = attackAnimation.getKeyFrame(stateTimer, true);
                }else {
                    region = walkAnimation.getKeyFrame(stateTimer, true);
                    velocity = new Vector2(-.045f,0);
                    hit = false;
                }
                break;
            default:
                region = walkAnimation.getKeyFrame(stateTimer, true);
                velocity = new Vector2(-.045f,0);
                break;

        }

        stateTimer = currentState == previousState ? stateTimer+dt: 0;
        previousState = currentState;
        return region;
    }

    public State getState(){

        if(hit){
            return State.ATTACKING;
        }
        else if(health >= 800){
            return State.STANDING;
        }else if((health < 600) && (health >= 400)){
            return State.RUNNING;
        }else if((health < 400) && (health >= 200)){
            return State.STANDING;
        }else if((health < 200) && (health > 0)){
            return State.RUNNING;
        }else {
            return State.STANDING;
        }
    }

    public void hit(){
        stateTime = 0;
        hit = true;
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(150/ DiCaprio.PPM, 200 / DiCaprio.PPM);
        fdef.filter.categoryBits = DiCaprio.BOSS_BIT;
        fdef.filter.maskBits = DiCaprio.GROUND_BIT | DiCaprio.ENEMY_BIT | DiCaprio.LEO_BIT | DiCaprio.BULLET_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void reverseVelocity() {

    }


    private boolean repeat = true;
    @Override
    public void destroyBody() {
        if(Hud.arma == 1){
            this.health -= 26;
        }else{
            this.health -= 60;
        }
        if((health <= 0) && repeat){
            currentState = State.DEAD;
            stateTime = 0;
            repeat = false;
            DiCaprio.blood.play();
        }
    }

    public void draw(Batch batch){
        if(!destroyed){
            super.draw(batch);
        }
    }

    public void react(){
        b2body.applyLinearImpulse(new Vector2(-.1f,0), b2body.getWorldCenter(), true);
    }
}
