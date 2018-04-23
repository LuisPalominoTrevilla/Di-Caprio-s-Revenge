package com.palomino.luis.dicaprio;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.palomino.luis.dicaprio.Screens.MenuScreen;
import com.palomino.luis.dicaprio.Screens.PlayScreen;

public class DiCaprio extends Game {
	public SpriteBatch batch;
	public static Sound reload;
	public static Sound collectible,xeno1, xeno2, bear, zombi1, zombi2, zombi3, zombi4, dog1, dog2, ape1,ape2, terminator1, terminatorGun, gun1, gun2, load2, trigger1, trigger2, blood, hit;
	public static Music levelMusic;
	public static final int V_WIDTH = 960;
	public static final int V_HEIGHT = 640;
	public static final float PPM = 300;

	public static final short GROUND_BIT = 1;
	public static final short LEO_BIT = 2;
	public static final short AMO_BIT = 4;
	public static final short HEALTH_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short BULLET_BIT = 128;
	public static final short SPIT_BIT = 256;
	public static final short META_BIT = 512;
	public static final short LASER_BIT = 1024;
	public static final short BOSS_BIT = 2048;
	public static final short COLLECTIBLE_BIT = 4096;


	@Override
	public void create () {
		batch = new SpriteBatch();

		reload = Gdx.audio.newSound(Gdx.files.local("reload.mp3"));
		xeno1 = Gdx.audio.newSound(Gdx.files.local("xenomorph1.mp3"));
		xeno2 = Gdx.audio.newSound(Gdx.files.local("xenomorph2.mp3"));
		zombi1 = Gdx.audio.newSound(Gdx.files.local("zombi1.mp3"));
		zombi2 = Gdx.audio.newSound(Gdx.files.local("zombi2.mp3"));
		zombi3 = Gdx.audio.newSound(Gdx.files.local("zombi3.mp3"));
		zombi4 = Gdx.audio.newSound(Gdx.files.local("zombi4.mp3"));
		dog1 = Gdx.audio.newSound(Gdx.files.local("dogz1.mp3"));
		dog2 = Gdx.audio.newSound(Gdx.files.local("dogz2.mp3"));
		gun1 = Gdx.audio.newSound(Gdx.files.local("gun1.mp3"));
		gun2 = Gdx.audio.newSound(Gdx.files.local("gun2.mp3"));
		load2 = Gdx.audio.newSound(Gdx.files.local("reload1.mp3"));
		trigger1 = Gdx.audio.newSound(Gdx.files.local("Trigger1.mp3"));
		trigger2 = Gdx.audio.newSound(Gdx.files.local("Trigger2.mp3"));
		levelMusic = Gdx.audio.newMusic(Gdx.files.local("Level1.mp3"));
		blood = Gdx.audio.newSound(Gdx.files.local("BloodSplatter.mp3"));
		hit = Gdx.audio.newSound(Gdx.files.local("hit.wav"));
		ape1 = Gdx.audio.newSound(Gdx.files.local("monkey1.mp3"));
		ape2 = Gdx.audio.newSound(Gdx.files.local("monkey2.mp3"));
		terminator1 = Gdx.audio.newSound(Gdx.files.local("terminator1.mp3"));
		terminatorGun = Gdx.audio.newSound(Gdx.files.local("terminatorGun.mp3"));
		bear = Gdx.audio.newSound(Gdx.files.local("bearSound.mp3"));
		collectible = Gdx.audio.newSound(Gdx.files.local("collectible.mp3"));
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
	}
}
