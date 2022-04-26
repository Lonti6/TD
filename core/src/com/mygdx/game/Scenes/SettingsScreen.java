package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.MyObjects.Button;
import com.mygdx.game.MyObjects.Loader;

public class SettingsScreen implements Screen {

    private static MyGdxGame game;
    private Button screenButton, volumeMinusButton, volumePlusButton, backButton;
    private Button otherVolumeButtonPlus, otherVolumeButtonMinus;

    public SettingsScreen()
    {
        this.game = Loader.game;
        screenButton = Loader.CreateButton(450, 305, 30, 30, Loader.GetPreferences().getString("FullScreenMode"));
        volumeMinusButton = Loader.CreateButton(420, 245, 30, 30, "<");
        volumePlusButton = Loader.CreateButton(510, 245, 30, 30, ">");
        backButton = Loader.CreateButton(10, 10, 50, 50, "<");
        otherVolumeButtonMinus = Loader.CreateButton(420, 185, 30, 30, "<");
        otherVolumeButtonPlus = Loader.CreateButton(510, 185, 30, 30, ">");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0f, 1);
        game.batch.begin();
        game.batch.draw(Loader.GetFon(1), 0, 0, 800, 480);
        Loader.GetFont().getData().setScale(2);
        Loader.GetFont().draw(game.batch, "НАСТРОЙКИ", 220, 470);
        Loader.GetFont().getData().setScale(0.7f, 0.7f);
        Loader.GetFont().draw(game.batch, "Полноэкранный режим", 150, 330);
        Loader.GetFont().draw(game.batch, "Громкость музыки", 150, 270);
        Loader.GetFont().draw(game.batch, "Прочие звуки", 150, 210);
        Loader.GetFont().getData().setScale(0.6f);
        Loader.GetFont().draw(game.batch, (Loader.GetPreferences().getInteger("FonMusicVolume")*10)+"%", 455, 270);
        Loader.GetFont().draw(game.batch, (Loader.GetPreferences().getInteger("OtherVolume")*10)+"%", 455, 210);
        backButton.Draw(1.2f, 1.2f);
        screenButton.Draw(0.5f, 0.5f);
        volumeMinusButton.Draw(0.5f, 0.5f);
        volumePlusButton.Draw(0.5f, 0.5f);
        otherVolumeButtonMinus.Draw(0.5f, 0.5f);
        otherVolumeButtonPlus.Draw(0.5f, 0.5f);
        game.batch.end();

        if (screenButton.IsClicked())
        {
            if (!Loader.GetPreferences().getString("FullScreenMode").equals("+"))
            {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                Loader.GetPreferences().putString("FullScreenMode", "+");
                screenButton.SetText("+");
            }
            else
            {
                Gdx.graphics.setWindowedMode(800, 480);
                Loader.GetPreferences().putString("FullScreenMode", "-");
                screenButton.SetText("-");
            }
            Loader.GetPreferences().flush();
        }

        if (volumeMinusButton.IsClicked())
        {
            if (Loader.GetPreferences().getInteger("FonMusicVolume") > 0)
            {
                Loader.GetPreferences().putInteger("FonMusicVolume", Loader.GetPreferences().getInteger("FonMusicVolume")-1);
                for (int i = 0; i<2; i++)
                    Loader.GetFonMusic(i).setVolume((float)(Loader.GetPreferences().getInteger("FonMusicVolume")/10.0));
                Loader.GetPreferences().flush();
            }

        }

        if (volumePlusButton.IsClicked())
        {
            if (Loader.GetPreferences().getInteger("FonMusicVolume") < 10)
            {
                Loader.GetPreferences().putInteger("FonMusicVolume", Loader.GetPreferences().getInteger("FonMusicVolume")+1);
                for (int i = 0; i<2; i++)
                    Loader.GetFonMusic(i).setVolume((float)(Loader.GetPreferences().getInteger("FonMusicVolume")/10.0));
                Loader.GetPreferences().flush();
            }

        }

        if (otherVolumeButtonPlus.IsClicked())
        {
            if (Loader.GetPreferences().getInteger("OtherVolume") < 10)
            {
                Loader.GetPreferences().putInteger("OtherVolume", Loader.GetPreferences().getInteger("OtherVolume")+1);
                Loader.otherVolume++;
                Loader.GetPreferences().flush();
            }
        }

        if (otherVolumeButtonMinus.IsClicked())
        {
            if (Loader.GetPreferences().getInteger("OtherVolume") > 0)
            {
                Loader.GetPreferences().putInteger("OtherVolume", Loader.GetPreferences().getInteger("OtherVolume")-1);
                Loader.otherVolume--;
                Loader.GetPreferences().flush();
            }
        }

        if (backButton.IsClicked())
        {
            game.setScreen(new MainMenuScreen());
            dispose();
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
        System.gc();
    }
}
