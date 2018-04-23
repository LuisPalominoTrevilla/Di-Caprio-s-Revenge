package com.palomino.luis.dicaprio.Sprites;

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
 * Created by Carlos on 4/19/2016.
 */
public class Zombi2 extends Enemy{
    private float stateTimer, jump;
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    int seg, soundType, jumpTimer;

    private int health;
    private boolean isAlive;

    public Zombi2(World world, float x, float y) {
        super(world, x, y);
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(new Texture("ZOMBIE 21.png")));
        frames.add(new TextureRegion(new Texture("ZOMBIE 22.png")));
        frames.add(new TextureRegion(new Texture("ZOMBIE 23.png")));
        frames.add(new TextureRegion(new Texture("ZOMBIE 24.png")));
        frames.add(new TextureRegion(new Texture("ZOMBIE 25.png")));
        frames.add(new TextureRegion(new Texture("ZOMBIE 26.png")));
        frames.add(new TextureRegion(new Texture("ZOMBIE 27.png")));
        frames.add(new TextureRegion(new Texture("ZOMBIE 28.png")));
        walkAnimation = new Animation(0.2f, frames);
        frames.clear();
        stateTime = 0;
        jump = 0;
        jumpTimer = random.nextInt(8) +1;
        setBounds(getX(), getY(), 210 / DiCaprio.PPM, 240 / DiCaprio.PPM);
        seg = random.nextInt(20) +1;
        soundType = random.nextInt(2) +1;
        health = 120;
        isAlive = true;
        velocity = new Vector2(.05f,0);
        right = true;
    }

    public void update(float dt){
        stateTime+= dt;
        if(setToDestroyed && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
        }else if (!destroyed){
            if(currentState != State.DEAD) {
                jump += dt;
                if ((right && b2body.getLinearVelocity().x <= 2) && b2body.isActive()) {
                    b2body.applyLinearImpulse(velocity, b2body.getWorldCenter(), true);
                } else if ((!right && b2body.getLinearVelocity().x >= -2) && b2body.isActive()) {
                    b2body.applyLinearImpulse(velocity, b2body.getWorldCenter(), true);
                }

                if (right) {
                    setPosition((b2body.getPosition().x - getWidth() / 2) + .06f, (b2body.getPosition().y - getHeight() / 2) + .12f);
                } else {
                    setPosition((b2body.getPosition().x - getWidth() / 2) - .06f, (b2body.getPosition().y - getHeight() / 2) + .12f);
                }

                setRegion(getFrame(dt));
                playSounds(dt);
            }else{
                setRegion(destroyedZombi.getKeyFrame(stateTime, true));
                if(stateTime > .4f){
                    setToDestroyed = true;
                }
            }
        }

    }

    public void playSounds(float dt){
        if(jump >= jumpTimer){
            b2body.applyLinearImpulse(new Vector2(0,5f), b2body.getWorldCenter(), true);
            jump = 0;
            jumpTimer = random.nextInt(8) +1;
        }
        if(stateTimer > seg){
            switch (soundType){
                case 1:
                    DiCaprio.zombi3.play();
                    break;
                case 2:
                    DiCaprio.zombi4.play();
                    break;
            }
            soundType = random.nextInt(2) +1;
            stateTimer = 0;
            seg = random.nextInt(20) + 1;
        }else{
            stateTimer+= dt;
        }
    }

    public TextureRegion getFrame(float dt){
        TextureRegion region;
        region = walkAnimation.getKeyFrame(stateTime, true);
        if((b2body.getLinearVelocity().x < 0) && region.isFlipX()){
            region.flip(true, false);
            right = false;
        }
        if((b2body.getLinearVelocity().x > 0) && !region.isFlipX()){
            region.flip(true, false);
            right = true;
        }
        return region;
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(30 / DiCaprio.PPM, 70 / DiCaprio.PPM);
        fdef.filter.categoryBits = DiCaprio.ENEMY_BIT;
        fdef.filter.maskBits = DiCaprio.GROUND_BIT | DiCaprio.OBJECT_BIT | DiCaprio.ENEMY_BIT | DiCaprio.LEO_BIT | DiCaprio.BULLET_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void reverseVelocity() {
        if (right) {
            velocity = new Vector2(-.05f, 0);
            right = false;
        } else {
            velocity = new Vector2(.05f, 0);
            right = true;
        }
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

}
