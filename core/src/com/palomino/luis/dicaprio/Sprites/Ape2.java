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
 * Created by Carlos on 5/9/2016.
 */
public class Ape2 extends Enemy{
    private float stateTimer;
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    int seg, soundType;

    private int health;
    private boolean isAlive;

    public Ape2(World world, float x, float y) {
        super(world, x, y);
        currentState = Enemy.State.ALIVE;
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(new Texture("Ape 2.1.png")));
        frames.add(new TextureRegion(new Texture("Ape 2.2.png")));
        frames.add(new TextureRegion(new Texture("Ape 2.3.png")));
        frames.add(new TextureRegion(new Texture("Ape 2.4.png")));
        frames.add(new TextureRegion(new Texture("Ape 2.5.png")));
        frames.add(new TextureRegion(new Texture("Ape 2.6.png")));
        frames.add(new TextureRegion(new Texture("Ape 2.7.png")));
        frames.add(new TextureRegion(new Texture("Ape 2.8.png")));
        frames.add(new TextureRegion(new Texture("Ape 2.9.png")));
        frames.add(new TextureRegion(new Texture("Ape 2.10.png")));
        frames.add(new TextureRegion(new Texture("Ape 2.11.png")));
        frames.add(new TextureRegion(new Texture("Ape 2.12.png")));
        walkAnimation = new Animation(0.1f, frames);
        frames.clear();
        stateTime = 0;
        setBounds(getX(), getY(), 160 / DiCaprio.PPM, 220 / DiCaprio.PPM);
        seg = random.nextInt(10) +1;
        soundType = random.nextInt(2) +1;
        health = 80;
        isAlive = true;
        velocity = new Vector2(.06f,0);
        right = true;
    }

    public void update(float dt){
        stateTime+= dt;
        if(setToDestroyed && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
        }
        else if(!destroyed){
            if(currentState != Enemy.State.DEAD) {
                if(b2body.isActive()) {
                    b2body.applyLinearImpulse(velocity, b2body.getWorldCenter(), true);
                }
                if (right) {
                    setPosition((b2body.getPosition().x - getWidth() / 2) + .02f, (b2body.getPosition().y - getHeight() / 2) -.02f);
                } else {
                    setPosition((b2body.getPosition().x - getWidth() / 2) - .02f, (b2body.getPosition().y - getHeight() / 2) -.02f);
                }
                setRegion(getFrame(dt));
                playSounds(dt);
            }else {
                setRegion(destroyedZombi.getKeyFrame(stateTime, true));
                if(stateTime > .4f){
                    setToDestroyed = true;
                }

            }
        }
    }

    public void playSounds(float dt){
        if(stateTimer > seg){
            if(right)
                b2body.applyLinearImpulse(new Vector2(.01f,5f), b2body.getWorldCenter(), true);
            else
                b2body.applyLinearImpulse(new Vector2(-.01f,5f), b2body.getWorldCenter(), true);

            switch (soundType){
                case 1:
                    DiCaprio.ape2.play();
                    break;
            }
            soundType = random.nextInt(2) +1;
            stateTimer = 0;
            seg = random.nextInt(10) + 1;
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
        shape.setAsBox(25 / DiCaprio.PPM, 100 / DiCaprio.PPM);
        fdef.filter.categoryBits = DiCaprio.ENEMY_BIT;
        fdef.filter.maskBits = DiCaprio.GROUND_BIT | DiCaprio.OBJECT_BIT | DiCaprio.ENEMY_BIT | DiCaprio.LEO_BIT | DiCaprio.BULLET_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void reverseVelocity() {
        if (right) {
            velocity = new Vector2(-.06f, 0);
            right = false;
        } else {
            velocity = new Vector2(.06f, 0);
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
            currentState = Enemy.State.DEAD;
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
