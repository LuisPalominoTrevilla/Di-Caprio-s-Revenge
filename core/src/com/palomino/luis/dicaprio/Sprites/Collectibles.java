package com.palomino.luis.dicaprio.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.palomino.luis.dicaprio.DiCaprio;

/**
 * Created by Carlos on 5/10/2016.
 */
public class Collectibles extends InteractiveTileObject {
    public Collectibles(World world, TiledMap map, Rectangle bounds, DiCaprio game) {
        super(world, map, bounds, game);
        fixture.setUserData(this);
        setCategoryFilter(DiCaprio.COLLECTIBLE_BIT);
    }

    @Override
    public void checkHit() {
        setCategoryFilter(DiCaprio.DESTROYED_BIT);
        getCell().setTile(null);

    }
}
