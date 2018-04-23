package com.palomino.luis.dicaprio.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.palomino.luis.dicaprio.DiCaprio;
import com.palomino.luis.dicaprio.Scenes.Hud;

import javax.xml.soap.Text;

/**
 * Created by Carlos on 4/20/2016.
 */
public class Spit extends Sprite{
    World world;
    Array<TextureRegion> frames;
    Animation spitAnimation;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;

    Body b2body;

    public Spit(World world, float x, float y, boolean fireRight){
        this.fireRight = fireRight;
        this.world = world;

        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(new Texture("SPIT1.png")));
        frames.add(new TextureRegion(new Texture("SPIT2.png")));
        frames.add(new TextureRegion(new Texture("SPIT3.png")));
        spitAnimation = new Animation(0.15f, frames);
        frames.clear();
        setRegion(spitAnimation.getKeyFrame(0));
        setBounds(x, y, 250 / DiCaprio.PPM, 200 / DiCaprio.PPM);
        defineSpit();
        setToDestroy = false;
        destroyed = false;
    }

    public void defineSpit(){
        BodyDef bdef = new BodyDef();

        bdef.position.set(fireRight ? getX() + 70 /DiCaprio.PPM : getX() - 70 /DiCaprio.PPM, getY() + 50/DiCaprio.PPM);

        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(10 / DiCaprio.PPM);
        fdef.filter.categoryBits = DiCaprio.SPIT_BIT;
        fdef.filter.maskBits =
                DiCaprio.ENEMY_BIT |
                        DiCaprio.OBJECT_BIT | DiCaprio.GROUND_BIT | DiCaprio.LEO_BIT;
        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);

        b2body.applyLinearImpulse(new Vector2(fireRight ? .4f : -.4f, 0), b2body.getWorldCenter(), true);
        b2body.setGravityScale(.03f);
    }

    public void update(float dt) {
        stateTime += dt;
        setRegion(spitAnimation.getKeyFrame(stateTime, true));
        setPosition(b2body.getPosition().x - getWidth() / 2, (b2body.getPosition().y - getHeight() /2)-15 / DiCaprio.PPM);
        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }

        //if(b2body.getLinearVelocity().y > 2f)

        if(fireRight && b2body.getLinearVelocity().x <= 2){
            b2body.applyLinearImpulse(new Vector2(fireRight ? .4f : -.4f, 0), b2body.getWorldCenter(), true);
        }else if (!fireRight && b2body.getLinearVelocity().x >= -2){
            b2body.applyLinearImpulse(new Vector2(fireRight ? .4f : -.4f, 0), b2body.getWorldCenter(), true);
        }

        if((fireRight && b2body.getLinearVelocity().x < 0) || (!fireRight && b2body.getLinearVelocity().x > 0))
            setToDestroy();

        if((b2body.getLinearVelocity().x < 0 || !fireRight) && spitAnimation.getKeyFrame(stateTime).isFlipX()){
            spitAnimation.getKeyFrame(stateTime).flip(true, false);
            fireRight = false;
        }else if ((b2body.getLinearVelocity().x > 0 || fireRight) && !spitAnimation.getKeyFrame(stateTime).isFlipX()){
            spitAnimation.getKeyFrame(stateTime).flip(true, false);
            fireRight = true;
        }
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

}
