package com.palomino.luis.dicaprio.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.palomino.luis.dicaprio.DiCaprio;
import com.palomino.luis.dicaprio.Scenes.Hud;

/**
 * Created by Carlos on 4/20/2016.
 */
public class Bullet extends Sprite {

    World world;
    TextureRegion bullet;
    TextureRegion bullet1;
    TextureRegion bullet2;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;

    private Leonardo leo;
    Body b2body;

    public Bullet(World world, float x, float y, boolean fireRight, Leonardo leo){
        this.fireRight = fireRight;
        this.world = world;
        this.leo = leo;

        bullet1 = new TextureRegion(new Texture("bullet.png"));
        bullet2 = new TextureRegion(new Texture("shotgunBullet.png"));
        if(leo.arma == 1){
            bullet = bullet1;
            setBounds(x, y, 20 / DiCaprio.PPM, 20 / DiCaprio.PPM);
        }else{
            bullet = bullet2;
            setBounds(x, y, 40 / DiCaprio.PPM, 40 / DiCaprio.PPM);
        }
        setRegion(bullet);

        defineBullet();
    }

    public void defineBullet(){
        BodyDef bdef = new BodyDef();
        if(leo.getState() != Leonardo.State.DUCKING){
            if(leo.arma == 1){
                bdef.position.set(fireRight ? getX() + 60 /DiCaprio.PPM : getX() - 60 /DiCaprio.PPM, getY() + 35/DiCaprio.PPM);
            }else {
                bdef.position.set(fireRight ? getX() + 95 /DiCaprio.PPM : getX() - 95 /DiCaprio.PPM, getY() + 40/DiCaprio.PPM);
            }
        }else {
            if(leo.arma == 1){
                bdef.position.set(fireRight ? getX() + 60 /DiCaprio.PPM : getX() - 60 /DiCaprio.PPM, getY() +4/DiCaprio.PPM);
            }else {
                bdef.position.set(fireRight ? getX() + 95 /DiCaprio.PPM : getX() - 95 /DiCaprio.PPM, getY() +4/DiCaprio.PPM);
            }
        }


        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(.1f / DiCaprio.PPM);
        fdef.filter.categoryBits = DiCaprio.BULLET_BIT;
        fdef.filter.maskBits =
                DiCaprio.ENEMY_BIT |
                DiCaprio.OBJECT_BIT | DiCaprio.GROUND_BIT | DiCaprio.BOSS_BIT;
        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        fdef.density = 0;


        b2body.createFixture(fdef).setUserData(this);
        b2body.applyLinearImpulse(new Vector2(new Vector2(fireRight ? 1 : -1, 0)), b2body.getWorldCenter(), true);
        b2body.setGravityScale(0);
        b2body.setBullet(true);
    }

    public void update(float dt) {
        if(leo.arma == 1){
            bullet = bullet1;
            setBounds(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2, 20 / DiCaprio.PPM, 20 / DiCaprio.PPM);
        }else{
            bullet = bullet2;
            setBounds(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2, 30 / DiCaprio.PPM, 30 / DiCaprio.PPM);
        }

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

    public void addScore(int value){
        leo.addScore(value);
    }
}
