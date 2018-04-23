package com.palomino.luis.dicaprio.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.palomino.luis.dicaprio.DiCaprio;
import com.palomino.luis.dicaprio.Scenes.Hud;
import com.palomino.luis.dicaprio.Screens.PlayScreen;

/**
 * Created by Carlos on 4/15/2016.
 */
public class Amo extends InteractiveTileObject {

    public Amo(World world, TiledMap map, Rectangle bounds, DiCaprio game) {
        super(world, map, bounds, game);
        fixture.setUserData(this);
        setCategoryFilter(DiCaprio.AMO_BIT);
    }

    @Override
    public void checkHit() {
        Gdx.app.log("Amo", "Collision");
        setCategoryFilter(DiCaprio.DESTROYED_BIT);
        getCell().setTile(null);
    }
}
