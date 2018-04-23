package com.palomino.luis.dicaprio.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.palomino.luis.dicaprio.DiCaprio;

/**
 * Created by Carlos on 5/8/2016.
 */
public class LoadingScreen implements Screen{

    private int previousLevel;
    private DiCaprio game;
    private boolean TwoPlayers;
    private Texture loading;
    private float stateTime;

    public LoadingScreen(int previousLevel, DiCaprio game, boolean TwoPlayers){
        loading = new Texture("levelCompleted.png");
        this.previousLevel = previousLevel;
        this.game = game;
        this.TwoPlayers = TwoPlayers;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Clear the game screen with black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(loading, 0, 0);
        game.batch.end();

        if(stateTime > .5f){
            switch (previousLevel){
                case 1:
                    game.setScreen(new Level2(game, TwoPlayers));
                    break;
                case 2:
                    game.setScreen(new Level3(game, TwoPlayers));
                    break;
                case 3:
                    game.setScreen(new Level4(game, TwoPlayers));
                    break;
                case 4:
                    game.setScreen(new Level5(game, TwoPlayers));
                    break;
                case 5:
                    game.setScreen(new FinalLevel(game, TwoPlayers));
                    break;
                case 6:
                    game.setScreen(new WinScreen(game));
                    break;
            }
        }
        stateTime += delta;
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
        loading.dispose();
    }
}
