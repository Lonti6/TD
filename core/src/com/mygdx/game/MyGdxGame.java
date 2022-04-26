package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyObjects.Helper;
import com.mygdx.game.MyObjects.Loader;
import com.mygdx.game.Scenes.LoadingScreen;
import com.mygdx.game.Scenes.MainMenuScreen;

public class MyGdxGame extends Game {

	public static SpriteBatch batch;
	private static Pixmap cursor;
	private static OrthographicCamera camera;

	public void create () {

		batch = new SpriteBatch();

		camera = new OrthographicCamera();

		cursor = new Pixmap(Gdx.files.internal("cursor.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursor, 0, 0));

		LoadingScreen screen = new LoadingScreen(this);
		setScreen(screen);

		Preferences prefs = Gdx.app.getPreferences("Settings");
		if (prefs.getString("FullScreenMode").equals("+")) Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		else Gdx.graphics.setWindowedMode(800, 480);
		Loader.game = this;
	}

	public void render () {
		super.render();
	}

	public void dispose () {
		batch.dispose();
		cursor.dispose();
		Loader.Dispose();
	}
}
