package com.palomino.luis.dicaprio.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.palomino.luis.dicaprio.DiCaprio;
import com.palomino.luis.dicaprio.Sprites.Leonardo;

/**
 * Created by Carlos on 4/15/2016.
 */
public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    public static int arma;

    Leonardo player;

    private static Integer level;
    private Integer time;
    private float worldTimer;

    public Label scoreLabel;
    public Label levelLabel;
    public Label amoLabel;
    public Label healthLabel;
    static Label timeLabel;

    Label Score;
    Label Amo;
    Label Level;
    Label Health;
    Label Time;

    public static boolean isAlive;
    public boolean Player2;


    //Constructor
    public Hud(SpriteBatch sb, Leonardo player, boolean Player2, int level){
        arma = 1;
        this.Player2 = Player2;
        //Set the character alive
        isAlive = true;
        this.player = player;

        //Set current weapon
        this.level = level;
        time = 400;


        viewport = new FitViewport(DiCaprio.V_WIDTH, DiCaprio.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        if(!Player2) {
            Table table = new Table();
            table.top();
            table.setFillParent(true);

            timeLabel = new Label(String.format("%03d", time), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            scoreLabel = new Label(String.format("%08d", player.score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            levelLabel = new Label(String.format("%d", level), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            amoLabel = new Label(String.format("%02d/%d", player.chargerAmo, player.Totalamo), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            healthLabel = new Label(String.format("%d", player.health), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

            Score = new Label("DI CAPRIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            Amo = new Label("AMO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            Level = new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            Health = new Label("HEALTH", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            Time = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

            table.add(Score).expandX().padTop(15 / DiCaprio.PPM);
            table.add(Amo).expandX().padTop(15 / DiCaprio.PPM);
            table.add(Health).expandX().padTop(15 / DiCaprio.PPM);
            table.add(Level).expandX().padTop(15 / DiCaprio.PPM);
            table.add(Time).expandX().padTop(15 / DiCaprio.PPM);
            table.row();
            table.add(scoreLabel).expandX();
            table.add(amoLabel).expandX();
            table.add(healthLabel).expandX();
            table.add(levelLabel).expandX();
            table.add(timeLabel).expandX();

            stage.addActor(table);
        }else{
            Table table = new Table();
            table.setFillParent(true);

            scoreLabel = new Label(String.format("%08d", player.score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            amoLabel = new Label(String.format("%02d/%d", player.chargerAmo, player.Totalamo), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            healthLabel = new Label(String.format("%d", player.health), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

            Score = new Label("DI KAPRIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            Amo = new Label("AMO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            Health = new Label("HEALTH", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

            table.add(Score).expandX().padTop(15 / DiCaprio.PPM);
            table.add(Amo).expandX().padTop(15 / DiCaprio.PPM);
            table.add(Health).expandX().padTop(15 / DiCaprio.PPM);
            table.row();
            table.add(scoreLabel).expandX();
            table.add(amoLabel).expandX();
            table.add(healthLabel).expandX();
            table.add(new Label("          ", new Label.LabelStyle(new BitmapFont(), Color.WHITE))).expandX();
            table.add(new Label("          ", new Label.LabelStyle(new BitmapFont(), Color.WHITE))).expandX();

            table.setPosition(0, 235);
            stage.addActor(table);
        }


    }

    public void update (float dt){
        worldTimer += dt;
        if (worldTimer >= 1){
            time--;
            timeLabel.setText(String.format("%03d", time));
            worldTimer = 0;
        }
        if(player.health == 0){
            isAlive = false;
        }

        amoLabel.setText(String.format("%02d/%d", player.chargerAmo, player.Totalamo));
        healthLabel.setText(String.format("%d", player.health));
        scoreLabel.setText(String.format("%08d", player.score));

        if(player.arma == 1){
            arma = 1;
        }else{
            arma = 2;
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
