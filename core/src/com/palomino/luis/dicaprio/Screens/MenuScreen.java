package com.palomino.luis.dicaprio.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.palomino.luis.dicaprio.DiCaprio;

/**
 * Created by Carlos on 4/13/2016.
 */
public class MenuScreen implements Screen {
    private DiCaprio game;
    private Texture menu, menu1, menu2;
    private Music musicIntro;
    private float stateTime;

    private boolean Player1;

    public MenuScreen(DiCaprio game){
        this.game = game;
        menu1 = new Texture("Menu1P.png");
        menu2 = new Texture("Menu2P.png");
        menu = menu1;
        this.musicIntro = Gdx.audio.newMusic(Gdx.files.local("Main.mp3"));
        stateTime = 0;
        Player1 = true;
    }


    @Override
    public void show() {
        this.musicIntro.play();
    }

    private boolean enter = false;

    @Override
    public void render(float delta) {

        //Clear the game screen with black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(menu, 0, 0);
        game.batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && Player1){
            Player1 = false;
            menu = menu2;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && !Player1){
            Player1 = true;
            menu = menu1;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || enter){
            enter = true;
            menu = new Texture("loading.png");
            this.musicIntro.stop();

            if(stateTime > .5f){
                game.setScreen(new PlayScreen(game, !Player1));
            }
            stateTime += delta;
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        menu.dispose();
        menu1.dispose();
        menu2.dispose();
    }
}
