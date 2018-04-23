package com.palomino.luis.dicaprio.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Created by Carlos on 4/18/2016.
 */
public abstract class Enemy extends Sprite {
    protected World world;
    public Body b2body;
    protected Random random;
    protected Vector2 velocity;
    public boolean right;
    protected boolean destroyed;
    protected boolean setToDestroyed;
    protected Animation destroyedZombi;
    protected Animation destroyedAlien;
    protected Animation destroyedTerminator;
    protected Array<TextureRegion> frames;
    protected enum State {ALIVE, DEAD};
    protected State currentState;

    public Enemy(World world, float x, float y){
        right = true;
        destroyed = false;
        setToDestroyed = false;
        random = new Random();
        this.world = world;
        setPosition(x,y);
        defineEnemy();
        b2body.setActive(false);
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(new Texture("blood_split1.png")));
        frames.add(new TextureRegion(new Texture("blood_split2.png")));
        frames.add(new TextureRegion(new Texture("blood_split3.png")));
        frames.add(new TextureRegion(new Texture("blood_split4.png")));
        frames.add(new TextureRegion(new Texture("blood_split5.png")));
        frames.add(new TextureRegion(new Texture("blood_split6.png")));
        destroyedZombi = new Animation(.1f, frames);
        frames.clear();
        frames.add(new TextureRegion(new Texture("blood_alien1.png")));
        frames.add(new TextureRegion(new Texture("blood_alien2.png")));
        frames.add(new TextureRegion(new Texture("blood_alien3.png")));
        frames.add(new TextureRegion(new Texture("blood_alien4.png")));
        frames.add(new TextureRegion(new Texture("blood_alien5.png")));
        frames.add(new TextureRegion(new Texture("blood_alien6.png")));
        destroyedAlien = new Animation(.1f, frames);
        frames.clear();
        frames.add(new TextureRegion(new Texture("termi_split1.png")));
        frames.add(new TextureRegion(new Texture("termi_split2.png")));
        frames.add(new TextureRegion(new Texture("termi_split3.png")));
        frames.add(new TextureRegion(new Texture("termi_split4.png")));
        frames.add(new TextureRegion(new Texture("termi_split5.png")));
        frames.add(new TextureRegion(new Texture("termi_split6.png")));
        destroyedTerminator = new Animation(.1f, frames);
        frames.clear();
    }

    protected abstract void defineEnemy();
    public abstract void update(float dt);
    public abstract void reverseVelocity();
    public abstract void destroyBody();
    public boolean isDestroyed(){
        return destroyed;
    }
    public void setSetToDestroyed(){
        setToDestroyed = true;
    }
}
