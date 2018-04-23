package com.palomino.luis.dicaprio.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.palomino.luis.dicaprio.DiCaprio;

/**
 * Created by Carlos on 5/9/2016.
 */
public class Laser extends Sprite {
    World world;
    TextureRegion bullet;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;

    private Leonardo leo;
    Body b2body;

    public Laser(World world, float x, float y, boolean fireRight){
        this.fireRight = fireRight;
        this.world = world;

        bullet = new TextureRegion(new Texture("laserBullet.png"));
        setBounds(x, y, 80 / DiCaprio.PPM, 20 / DiCaprio.PPM);

        setRegion(bullet);

        defineBullet();
    }

    public void defineBullet(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ? getX() + 130 /DiCaprio.PPM : getX() - 130 /DiCaprio.PPM, getY() + 42/DiCaprio.PPM);

        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(10 /DiCaprio.PPM, 5 / DiCaprio.PPM);
        fdef.filter.categoryBits = DiCaprio.LASER_BIT;
        fdef.filter.maskBits = DiCaprio.OBJECT_BIT | DiCaprio.GROUND_BIT | DiCaprio.LEO_BIT;
        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);
        b2body.applyLinearImpulse(new Vector2(new Vector2(fireRight ? 1 : -1, 0)), b2body.getWorldCenter(), true);
        b2body.setGravityScale(0);
        b2body.setBullet(true);
    }

    public void update(float dt) {

        setBounds(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2, 80 / DiCaprio.PPM, 20 / DiCaprio.PPM);

        setRegion(bullet);
        if ((stateTime > 3 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }

        //if(b2body.getLinearVelocity().y > 2f)
        b2body.applyLinearImpulse(new Vector2(new Vector2(fireRight ? 1 : -1, 0)), b2body.getWorldCenter(), true);
        if((fireRight && b2body.getLinearVelocity().x < 0) || (!fireRight && b2body.getLinearVelocity().x > 0))
            setToDestroy();

        if((b2body.getLinearVelocity().x < 0 || !fireRight) && !bullet.isFlipX()){
            bullet.flip(true, false);
            fireRight = false;
        }else if ((b2body.getLinearVelocity().x > 0 || fireRight) && bullet.isFlipX()){
            bullet.flip(true, false);
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
