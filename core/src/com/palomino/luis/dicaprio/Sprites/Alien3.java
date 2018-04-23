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
 * Created by Carlos on 5/7/2016.
 */
public class Alien3 extends Enemy{
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;

    private int health;

    public Alien3(World world, float x, float y) {

        super(world, x, y);
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(new Texture("predator (1).png")));
        frames.add(new TextureRegion(new Texture("predator (2).png")));
        frames.add(new TextureRegion(new Texture("predator (3).png")));
        frames.add(new TextureRegion(new Texture("predator (4).png")));
        frames.add(new TextureRegion(new Texture("predator (5).png")));
        frames.add(new TextureRegion(new Texture("predator (6).png")));
        walkAnimation = new Animation(0.12f, frames);
        frames.clear();
        stateTime = 0;
        setBounds(getX(), getY(), 220 / DiCaprio.PPM, 220 / DiCaprio.PPM);
        health = 120;
        velocity = new Vector2(.06f,0);
    }

    public void update(float dt){
        stateTime+= dt;
        if(setToDestroyed && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
        }else if (!destroyed){
            if(currentState != Enemy.State.DEAD) {
                if ((b2body.getLinearVelocity().x <= 2) && b2body.isActive()) {
                    b2body.applyLinearImpulse(velocity, b2body.getWorldCenter(), true);
                }
                if (right) {
                    setPosition((b2body.getPosition().x - getWidth() / 2) - .06f, (b2body.getPosition().y - getHeight() / 2));
                } else {
                    setPosition((b2body.getPosition().x - getWidth() / 2) + .06f, (b2body.getPosition().y - getHeight() / 2));
                }

                if(b2body.getLinearVelocity().x > 0){
                    right = true;
                }else if (b2body.getLinearVelocity().x < 0){
                    right = false;
                }

                setRegion(getFrame(dt));

            }else{
                setRegion(destroyedAlien.getKeyFrame(stateTime, true));
                if(stateTime > .4f){
                    setToDestroyed = true;
                }
            }
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
            b2body.applyLinearImpulse(new Vector2(0, 5f), b2body.getWorldCenter(), true);
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
