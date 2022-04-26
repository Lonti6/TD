package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.MyObjects.Button;
import com.mygdx.game.MyObjects.Loader;
//Жильцов Никита ИВТ-20-1
public class MainMenuScreen implements Screen {

    MyGdxGame game;

    private static Button levelButton, settingsButton, exitButton;

    public MainMenuScreen() {
        this.game = Loader.game;

        levelButton = Loader.CreateButton(300, 300, 200, 100,"НАЧАТЬ");
        settingsButton = Loader.CreateButton(300, 180, 200, 100, "НАСТРОЙКИ");
        exitButton = Loader.CreateButton(300, 60, 200, 100,"ВЫХОД");
        Loader.GetFonMusic(0).setVolume((float)(Loader.GetPreferences().getInteger("FonMusicVolume")/10.0));
        Loader.GetFonMusic(0).setLooping(true);

        Loader.GetFonMusic(0).play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        game.batch.begin();

        game.batch.draw(Loader.GetFon(0), 0, 0);
        levelButton.Draw(1.2f, 1.3f);
        settingsButton.Draw(0.9f, 1.3f);
        exitButton.Draw(1.3f, 1.3f);
        Loader.GetFont().setColor(Color.WHITE);
        Loader.GetFont().getData().setScale(2, 2);
        Loader.GetFont().draw(game.batch, "КУРСАЧ", 280, 470);
        game.batch.end();

        if (levelButton.IsClicked())
        {
            game.setScreen(new LevelsScreen());
            dispose();
        }

        if (settingsButton.IsClicked())
        {
            game.setScreen(new SettingsScreen());
            dispose();
        }

        if (exitButton.IsClicked())
        {
            dispose();
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        System.gc();
    }

}