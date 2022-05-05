package com.mygdx.game.MyObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;

import java.awt.Rectangle;

public class Button {
    private Texture background;
    private Rectangle rectangle = new Rectangle();
    private String text;
    private MyGdxGame game;
    private Vector3 vector = new Vector3();
    private Array<Float> floats;
    private String tag;

    public void Draw(float scaleX, float scaleY) {
        Loader.GetFont().getData().setScale(scaleX, scaleY);
        floats = Helper.GetTextLenght(text, Loader.GetFont());
        game.batch.draw(background, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        Loader.GetFont().draw(game.batch,
                text,
                (int) (rectangle.getCenterX() - floats.get(0) / 2),
                (int) (rectangle.getCenterY() + floats.get(1) / 2));
    }
    public Button(int x, int y, int width, int height, String text, Texture background, MyGdxGame game) {
        this.game = game;
        this.text = text;
        this.background = background;
        rectangle.x = x;
        rectangle.y = y;
        rectangle.width = width;
        rectangle.height = height;
    }
    public boolean IsClicked() {
        //если нажата левая кнопка мыши
        if (Gdx.input.isButtonJustPressed(0)) {
            //предварительная подготовка координат
            vector.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            Loader.camera.unproject(vector);
            //возврат результата проверки клика по объекту
            return (vector.x >= rectangle.x && vector.x <= rectangle.x + rectangle.width
                    && vector.y >= rectangle.y && vector.y <= rectangle.y + rectangle.height);
        }
        return false;
    }
    public void SetText(String text) {
        this.text = text;
    }
    public void SetLocation(int x, int y) {
        rectangle.x = x;
        rectangle.y = y;
    }
    public void Dispose() {
        background.dispose();
    }

    public String getTag() {
        return tag;
    }
    public int GetX() {
        return rectangle.x;
    }
    public int GetY() {
        return rectangle.y;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
}
