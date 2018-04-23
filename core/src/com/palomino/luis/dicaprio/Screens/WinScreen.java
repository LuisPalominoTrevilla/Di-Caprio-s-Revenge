package com.palomino.luis.dicaprio.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.palomino.luis.dicaprio.DiCaprio;

/**
 * Created by Carlos on 5/10/2016.
 */
public class WinScreen implements Screen {
    private DiCaprio game;
    private SpriteBatch batch;
    private Texture gameOver;


    public WinScreen(DiCaprio game){
        this.game = game;
        gameOver = new Texture("WinScreen.png");
        batch = new SpriteBatch();
    }


    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            game.setScreen(new MenuScreen(game));
        }

        //Clear the game screen with black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(gameOver, 0, 0);
        batch.end();

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
        gameOver.dispose();batch.dispose();
    }
}
