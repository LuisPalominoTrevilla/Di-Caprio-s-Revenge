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
 * Created by Carlos on 4/21/2016.
 */
public class Alien2 extends Enemy{
    private float stateTimer;
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private Array<Spit> spits;
    int seg, soundType;

    private int health;

    public Alien2(World world, float x, float y) {

        super(world, x, y);
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(new Texture("ALIEN 1.png")));
        frames.add(new TextureRegion(new Texture("ALIEN 2.png")));
        frames.add(new TextureRegion(new Texture("ALIEN 3.png")));
        frames.add(new TextureRegion(new Texture("ALIEN 4.png")));
        frames.add(new TextureRegion(new Texture("ALIEN 5.png")));
        frames.add(new TextureRegion(new Texture("ALIEN 6.png")));
        frames.add(new TextureRegion(new Texture("ALIEN 7.png")));
        frames.add(new TextureRegion(new Texture("ALIEN 8.png")));
        walkAnimation = new Animation(0.15f, frames);
        frames.clear();
        stateTime = 0;
        setBounds(getX(), getY(), 170 / DiCaprio.PPM, 200 / DiCaprio.PPM);
        seg = random.nextInt(20) +1;
        soundType = random.nextInt(2) +1;
        health = 80;
        velocity = new Vector2(.06f,0);
        spits = new Array<Spit>();
    }

    public void update(float dt){
        stateTime+= dt;
        if(setToDestroyed && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
        }else if (!destroyed){
            if(currentState != State.DEAD) {
                if ((b2body.getLinearVelocity().x <= 2) && b2body.isActive()) {
                    b2body.applyLinearImpulse(velocity, b2body.getWorldCenter(), true);
                }
                if (right) {
                    setPosition((b2body.getPosition().x - getWidth() / 2) - .06f, (b2body.getPosition().y - getHeight() / 2) - .06f);
                } else {
                    setPosition((b2body.getPosition().x - getWidth() / 2) + .06f, (b2body.getPosition().y - getHeight() / 2) - .06f);
                }

                if(b2body.getLinearVelocity().x > 0){
                    right = true;
                }else if (b2body.getLinearVelocity().x < 0){
                    right = false;
                }

                setRegion(getFrame(dt));
                playSounds(dt);

                for (Spit spit : spits) {
                    spit.update(dt);
                    if (spit.isDestroyed())
                        spits.removeValue(spit, true);
                }
            }else{
                setRegion(destroyedAlien.getKeyFrame(stateTime, true));
                if(stateTime > .4f){
                    setToDestroyed = true;
                }
            }
        }
    }

    public void playSounds(float dt){
        if(stateTimer > seg){
            fire();
            switch (soundType){
                case 1:
                    DiCaprio.xeno1.play();
                    break;
                case 2:
                    DiCaprio.xeno2.play();
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
        shape.setAsBox(25 / DiCaprio.PPM, 90 / DiCaprio.PPM);
        fdef.filter.categoryBits = DiCaprio.ENEMY_BIT;
        fdef.filter.maskBits = DiCaprio.GROUND_BIT | DiCaprio.OBJECT_BIT | DiCaprio.ENEMY_BIT | DiCaprio.LEO_BIT | DiCaprio.BULLET_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void reverseVelocity() {
        int num = random.nextInt(10) + 1;

        if(num > 7) {
            if (right) {
                velocity = new Vector2(-.04f, 0);
                right = false;
            } else {
                velocity = new Vector2(.04f, 0);
                right = true;
            }
        }else {
            if(right) {
                b2body.applyLinearImpulse(new Vector2(-.1f, 5f), b2body.getWorldCenter(), true);
                right = false;
            }else {
                b2body.applyLinearImpulse(new Vector2(.1f, 5f), b2body.getWorldCenter(), true);
                right = true;
            }
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
            for(Spit spit : spits)
                spit.draw(batch);
        }
    }

    public void fire(){
        spits.add(new Spit(world, b2body.getPosition().x, b2body.getPosition().y -10/DiCaprio.PPM, right ? true : false));
    }

}