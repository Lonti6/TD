package com.mygdx.game.Scenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.MyObjects.InterfaceManager;
import com.mygdx.game.MyObjects.Loader;

public class LoadingScreen implements Screen {

    private OrthographicCamera camera;
    private ProgressBar bar;
    private Stage stage = new Stage();
    private MyGdxGame game;

    public LoadingScreen(MyGdxGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        InterfaceManager.Init();
        bar = InterfaceManager.CreateProgressBar();
        bar.setPosition(20, 20);
        bar.setSize(290, 20);
        bar.setValue(0);
        stage.addActor(bar);

        Loader.camera = camera;
        Loader.Init();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        if (Loader.Finishing()) {
            MainMenuScreen screen = new MainMenuScreen();
            game.setScreen(screen);
            return;
        }
        ScreenUtils.clear(0, 0, 0, 1);
        bar.setValue(Loader.GetProgress());
        game.batch.begin();
        stage.act(delta);
        stage.draw();
        game.batch.end();
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
        stage.dispose();
    }
}
