package com.palomino.luis.dicaprio.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.palomino.luis.dicaprio.DiCaprio;
import com.palomino.luis.dicaprio.Screens.PlayScreen;
import com.palomino.luis.dicaprio.Sprites.Alien1;
import com.palomino.luis.dicaprio.Sprites.Alien2;
import com.palomino.luis.dicaprio.Sprites.Alien3;
import com.palomino.luis.dicaprio.Sprites.Amo;
import com.palomino.luis.dicaprio.Sprites.Ape1;
import com.palomino.luis.dicaprio.Sprites.Ape2;
import com.palomino.luis.dicaprio.Sprites.Ape3;
import com.palomino.luis.dicaprio.Sprites.Collectibles;
import com.palomino.luis.dicaprio.Sprites.Enemy;
import com.palomino.luis.dicaprio.Sprites.Health;
import com.palomino.luis.dicaprio.Sprites.Leonardo;
import com.palomino.luis.dicaprio.Sprites.Terminator1;
import com.palomino.luis.dicaprio.Sprites.Terminator2;
import com.palomino.luis.dicaprio.Sprites.Terminator3;
import com.palomino.luis.dicaprio.Sprites.Zombi2;
import com.palomino.luis.dicaprio.Sprites.Zombie1;
import com.palomino.luis.dicaprio.Sprites.Zombie3;

import java.util.Random;

/**
 * Created by Carlos on 4/15/2016.
 */
public class B2WorldCreator {
    private Array<Enemy> enemies;
    //Clase 1 de enemigos
    private Array<Zombie1> zombie1;
    private Array<Zombi2> zombie2;
    private Array<Zombie3> zombie3;
    //Clase 2 de enemigos
    private Array<Alien1> alien1;
    private Array<Alien2> alien2;
    private Array<Alien3> alien3;
    //Clase 3 de enemigos
    private Array<Ape1> ape1;
    private Array<Ape2> ape2;
    private Array<Ape3> ape3;
    //Clase 4 de enemigos
    private Array<Terminator1> terminator1;
    private Array<Terminator2> terminator2;
    private Array<Terminator3> terminator3;

    int enemigo;

    public B2WorldCreator(World world,TiledMap map, DiCaprio game, int enemy){

        this.enemigo = enemy;
        enemies = new Array<Enemy>();
        //Create Body and Fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Create ground bodies/fixtures
        for(MapObject object: map.getLayers().get(0).getObjects().getByType(RectangleMapObject.class) ){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/ game.PPM, (rect.getY() + rect.getHeight()/2)/ game.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/game.PPM, rect.getHeight()/2/game.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = DiCaprio.GROUND_BIT;
            body.createFixture(fdef);
        }

        //Create crates bodies/fixtures
        for(MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class) ){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/ game.PPM, (rect.getY() + rect.getHeight()/2)/ game.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/game.PPM, rect.getHeight()/2/game.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = DiCaprio.OBJECT_BIT;
            body.createFixture(fdef);
        }

        //Create Amo bodies/fixtures
        for(MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class) ){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Amo(world, map, rect, game);
        }

        //Create health bodies/fixtures
        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class) ){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Health( world, map, rect, game);
        }

        //Create COLLECTIBLES bodies/fixtures
        for(MapObject object: map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class) ){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Collectibles( world, map, rect, game);
        }

        //Create goal bodies/fixtures
        for(MapObject object: map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class) ){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/ game.PPM, (rect.getY() + rect.getHeight()/2)/ game.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/game.PPM, rect.getHeight()/2/game.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = DiCaprio.META_BIT;
            body.createFixture(fdef);
        }

        switch (enemigo){
            case 0:
                zombie1 = new Array<Zombie1>();
                zombie2 = new Array<Zombi2>();
                zombie3 = new Array<Zombie3>();

                for(MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class) ){
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    zombie1.add(new Zombie1(world, rect.getX() / DiCaprio.PPM, rect.getY() / DiCaprio.PPM));
                }
                for(MapObject object: map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class) ){
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    zombie2.add(new Zombi2(world, rect.getX() / DiCaprio.PPM, rect.getY() / DiCaprio.PPM));
                }
                for(MapObject object: map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class) ){
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    zombie3.add(new Zombie3(world, rect.getX() / DiCaprio.PPM, rect.getY() / DiCaprio.PPM));
                }
                enemies.addAll(zombie1);
                enemies.addAll(zombie2);
                enemies.addAll(zombie3);
                break;
            case 1:
                alien1 = new Array<Alien1>();
                alien2 = new Array<Alien2>();
                alien3 = new Array<Alien3>();
                for(MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class) ){
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    alien1.add(new Alien1(world, rect.getX() / DiCaprio.PPM, rect.getY() / DiCaprio.PPM));
                }
                for(MapObject object: map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class) ){
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    alien2.add(new Alien2(world, rect.getX() / DiCaprio.PPM, rect.getY() / DiCaprio.PPM));
                }
                for(MapObject object: map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class) ){
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    alien3.add(new Alien3(world, rect.getX() / DiCaprio.PPM, rect.getY() / DiCaprio.PPM));
                }
                enemies.addAll(alien1);
                enemies.addAll(alien2);
                enemies.addAll(alien3);
                break;
            case 2:
                ape1 = new Array<Ape1>();
                ape2 = new Array<Ape2>();
                ape3 = new Array<Ape3>();

                for(MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class) ){
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    ape1.add(new Ape1(world, rect.getX() / DiCaprio.PPM, rect.getY() / DiCaprio.PPM));
                }
                for(MapObject object: map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class) ){
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    ape2.add(new Ape2(world, rect.getX() / DiCaprio.PPM, rect.getY() / DiCaprio.PPM));
                }
                for(MapObject object: map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class) ){
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    ape3.add(new Ape3(world, rect.getX() / DiCaprio.PPM, rect.getY() / DiCaprio.PPM));
                }

                enemies.addAll(ape1);
                enemies.addAll(ape2);
                enemies.addAll(ape3);
                break;
            case 3:
                terminator1 = new Array<Terminator1>();
                terminator2 = new Array<Terminator2>();
                terminator3 = new Array<Terminator3>();

                for(MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class) ){
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    terminator1.add(new Terminator1(world, rect.getX() / DiCaprio.PPM, rect.getY() / DiCaprio.PPM));
                }
                for(MapObject object: map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class) ){
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    terminator2.add(new Terminator2(world, rect.getX() / DiCaprio.PPM, rect.getY() / DiCaprio.PPM));
                }
                for(MapObject object: map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class) ){
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    terminator3.add(new Terminator3(world, rect.getX() / DiCaprio.PPM, rect.getY() / DiCaprio.PPM));
                }



                enemies.addAll(terminator1);
                enemies.addAll(terminator2);
                enemies.addAll(terminator3);
                break;
        }

    }

    public Array<Enemy> getEnemies(){
        return enemies;
    }

}
