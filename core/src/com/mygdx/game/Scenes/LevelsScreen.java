package com.mygdx.game.Scenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.MyObjects.Button;
import com.mygdx.game.MyObjects.Levels.Level1;
import com.mygdx.game.MyObjects.Levels.Level2;
import com.mygdx.game.MyObjects.Levels.Level3;
import com.mygdx.game.MyObjects.Loader;
import com.mygdx.game.MyObjects.Units.Player;

public class LevelsScreen implements Screen {
    private static MyGdxGame game;

    private Button levelOneButton, levelSecondButton, levelThirdButton, backButton;
    private Button almanacButton;

    public LevelsScreen()
    {
        this.game = Loader.game;
        almanacButton = Loader.CreateButton(350, 10, 100, 50, "Альманах");
        levelOneButton = Loader.CreateButton(45, 230, 200, 100, "1");
        levelSecondButton = Loader.CreateButton(295, 230, 200, 100, "2");
        levelThirdButton = Loader.CreateButton(545, 230, 200, 100, "3");
        backButton = Loader.CreateButton(10, 10, 50, 50, "<");
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0f, 1);
        game.batch.begin();
        game.batch.draw(Loader.GetFon(2), 0, 0, 800, 480);
        levelOneButton.Draw(1.2f, 1.2f);
        levelSecondButton.Draw(1.2f, 1.2f);
        levelThirdButton.Draw(1.2f, 1.2f);
        backButton.Draw(1.2f, 1.2f);
        almanacButton.Draw(0.5f, 0.5f);
        game.batch.end();

        if (levelOneButton.IsClicked())
        {
            Loader.GetFonMusic(0).stop();
            game.setScreen(new Level1(10));
            Player.currentLevel = 1;
            dispose();
        }
        if (levelSecondButton.IsClicked())
        {
            Loader.GetFonMusic(0).stop();
            game.setScreen(new Level2(10));
            Player.currentLevel = 2;
            dispose();
        }
        if (levelThirdButton.IsClicked())
        {
            Loader.GetFonMusic(0).stop();
            game.setScreen(new Level3(10));
            Player.currentLevel = 3;
            dispose();
        }
        if (backButton.IsClicked())
        {
            game.setScreen(new MainMenuScreen());
            dispose();
        }
        if(almanacButton.IsClicked())
        {
            game.setScreen(new Almanac());
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        System.gc();
    }
}
