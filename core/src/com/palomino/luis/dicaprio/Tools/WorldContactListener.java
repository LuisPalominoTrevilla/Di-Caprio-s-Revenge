package com.palomino.luis.dicaprio.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.palomino.luis.dicaprio.DiCaprio;
import com.palomino.luis.dicaprio.Scenes.Hud;
import com.palomino.luis.dicaprio.Sprites.BearBoss;
import com.palomino.luis.dicaprio.Sprites.Bullet;
import com.palomino.luis.dicaprio.Sprites.Enemy;
import com.palomino.luis.dicaprio.Sprites.InteractiveTileObject;
import com.palomino.luis.dicaprio.Sprites.Laser;
import com.palomino.luis.dicaprio.Sprites.Leonardo;
import com.palomino.luis.dicaprio.Sprites.Spit;

/**
 * Created by Carlos on 4/15/2016.
 */
public class WorldContactListener implements ContactListener {

    public WorldContactListener(){

    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;


        switch (cDef){
            case DiCaprio.LEO_BIT | DiCaprio.AMO_BIT:
                if(fixA.getFilterData().categoryBits == DiCaprio.LEO_BIT){
                    ((InteractiveTileObject)fixB.getUserData()).checkHit();
                    if(((Leonardo)fixA.getUserData()).arma == 1){
                        ((Leonardo)fixA.getUserData()).addAmo(10);
                    }else {
                        ((Leonardo)fixA.getUserData()).addAmo(6);
                    }
                    ((Leonardo)fixA.getUserData()).addScore(150);

                }else{
                    ((InteractiveTileObject)fixA.getUserData()).checkHit();
                    if(((Leonardo)fixB.getUserData()).arma == 1){
                        ((Leonardo)fixB.getUserData()).addAmo(10);
                    }else {
                        ((Leonardo)fixB.getUserData()).addAmo(6);
                    }
                    ((Leonardo)fixB.getUserData()).addScore(150);
                }
                break;
            case DiCaprio.LEO_BIT | DiCaprio.HEALTH_BIT:
                if(fixA.getFilterData().categoryBits == DiCaprio.LEO_BIT){
                    ((InteractiveTileObject)fixB.getUserData()).checkHit();
                    ((Leonardo)fixA.getUserData()).addHealth(15);
                    ((Leonardo)fixA.getUserData()).addScore(200);
                }else{
                    ((InteractiveTileObject)fixA.getUserData()).checkHit();
                    ((Leonardo)fixB.getUserData()).addHealth(15);
                    ((Leonardo)fixB.getUserData()).addScore(200);
                }
                break;
            case DiCaprio.ENEMY_BIT | DiCaprio.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == DiCaprio.ENEMY_BIT){
                    ((Enemy)fixA.getUserData()).reverseVelocity();
                }else {
                    ((Enemy)fixB.getUserData()).reverseVelocity();
                }
                break;
            case DiCaprio.ENEMY_BIT | DiCaprio.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity();
                ((Enemy)fixB.getUserData()).reverseVelocity();

                break;
            case DiCaprio.LEO_BIT | DiCaprio.ENEMY_BIT:
                DiCaprio.hit.play();
                if(fixA.getFilterData().categoryBits == DiCaprio.LEO_BIT){
                    ((Leonardo)fixA.getUserData()).subtractHealth(10);
                }else {
                    ((Leonardo)fixB.getUserData()).subtractHealth(10);
                }
                break;
            case DiCaprio.BULLET_BIT | DiCaprio.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == DiCaprio.BULLET_BIT){
                    ((Bullet)fixA.getUserData()).setToDestroy();
                }else {
                    ((Bullet)fixB.getUserData()).setToDestroy();
                }
                break;
            case DiCaprio.BULLET_BIT | DiCaprio.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == DiCaprio.BULLET_BIT){
                    ((Bullet)fixA.getUserData()).addScore(350);
                    ((Bullet)fixA.getUserData()).setToDestroy();
                    ((Enemy)fixB.getUserData()).destroyBody();
                    Gdx.app.log("Collision", "bb");
                }else{
                    ((Bullet)fixB.getUserData()).addScore(350);
                    ((Bullet)fixB.getUserData()).setToDestroy();
                    ((Enemy)fixA.getUserData()).destroyBody();
                    Gdx.app.log("Collision", "bb");
                }
                break;
            case DiCaprio.LEO_BIT | DiCaprio.SPIT_BIT:
                DiCaprio.hit.play();
                if(fixA.getFilterData().categoryBits == DiCaprio.SPIT_BIT){
                    ((Spit)fixA.getUserData()).setToDestroy();
                    ((Leonardo)fixB.getUserData()).subtractHealth(8);
                }else {
                    ((Spit)fixB.getUserData()).setToDestroy();
                    ((Leonardo)fixA.getUserData()).subtractHealth(8);
                }
                break;
            case DiCaprio.LEO_BIT | DiCaprio.META_BIT:
                if(fixA.getFilterData().categoryBits == DiCaprio.LEO_BIT){
                    ((Leonardo)fixA.getUserData()).nextLevel = true;
                    Gdx.app.log("Nxt", "lvl");
                }else {
                    ((Leonardo)fixB.getUserData()).nextLevel = true;
                    Gdx.app.log("Nxt", "lvl");
                }
                break;
            case DiCaprio.GROUND_BIT | DiCaprio.SPIT_BIT:
                if(fixA.getFilterData().categoryBits == DiCaprio.SPIT_BIT){
                    ((Spit)fixA.getUserData()).setToDestroy();
                }else {
                    ((Spit)fixB.getUserData()).setToDestroy();
                }
                break;
            case DiCaprio.LEO_BIT | DiCaprio.LASER_BIT:
                DiCaprio.hit.play();
                if(fixA.getFilterData().categoryBits == DiCaprio.SPIT_BIT){
                    ((Laser)fixA.getUserData()).setToDestroy();
                    ((Leonardo)fixB.getUserData()).subtractHealth(6);
                }else {
                    ((Laser)fixB.getUserData()).setToDestroy();
                    ((Leonardo)fixA.getUserData()).subtractHealth(6);
                }
                break;
            case DiCaprio.LASER_BIT | DiCaprio.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == DiCaprio.LASER_BIT){
                    ((Laser)fixA.getUserData()).setToDestroy();
                }else {
                    ((Laser)fixB.getUserData()).setToDestroy();
                }
                break;
            case DiCaprio.LEO_BIT | DiCaprio.BOSS_BIT:
                DiCaprio.hit.play();
                if(fixA.getFilterData().categoryBits == DiCaprio.LEO_BIT){
                    ((Leonardo)fixA.getUserData()).subtractHealth(30);
                    ((BearBoss)fixB.getUserData()).hit();
                    Gdx.app.log("fdsf", "worked");
                }else {
                    ((Leonardo)fixB.getUserData()).subtractHealth(30);
                    ((BearBoss)fixA.getUserData()).hit();
                    Gdx.app.log("fdsf", "worked");
                }
                break;
            case DiCaprio.BULLET_BIT | DiCaprio.BOSS_BIT:
                if(fixA.getFilterData().categoryBits == DiCaprio.BULLET_BIT){
                    ((Bullet)fixA.getUserData()).addScore(35000);
                    ((Bullet)fixA.getUserData()).setToDestroy();
                    ((BearBoss)fixB.getUserData()).destroyBody();
                    ((BearBoss)fixB.getUserData()).react();
                }else{
                    ((Bullet)fixB.getUserData()).addScore(35000);
                    ((Bullet)fixB.getUserData()).setToDestroy();
                    ((BearBoss)fixA.getUserData()).destroyBody();
                    ((BearBoss)fixA.getUserData()).react();
                }
                break;
            case DiCaprio.LEO_BIT | DiCaprio.COLLECTIBLE_BIT:
                DiCaprio.collectible.play();
                if(fixA.getFilterData().categoryBits == DiCaprio.LEO_BIT){
                    ((InteractiveTileObject)fixB.getUserData()).checkHit();
                    ((Leonardo)fixA.getUserData()).addScore(200000);
                }else{
                    ((InteractiveTileObject)fixA.getUserData()).checkHit();
                    ((Leonardo)fixB.getUserData()).addScore(200000);
                }
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
