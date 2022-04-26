package com.mygdx.game.MyObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;

import java.awt.Rectangle;

public class Slider {
    private Rectangle slideVector, lineVector;
    private Texture line, slide;
    Vector3 vector = new Vector3();

    Camera camera;

    public void SetLocation(int x, int y, int widthSlide, int heightSlide, int widthLine, int heightLine, MyGdxGame game, Camera camera)
    {
        this.camera = camera;

        slideVector = new Rectangle();
        slideVector.width = widthSlide;
        slideVector.height = heightSlide;
        slideVector.x = x;
        slideVector.y = y;

        lineVector = new Rectangle();
        lineVector.width = widthLine;
        lineVector.height = heightLine;
        lineVector.x = x;
        lineVector.y = y;

        game.batch.draw(line, lineVector.x, lineVector.y, lineVector.width, lineVector.height);
        game.batch.draw(slide, slideVector.x, slideVector.y-slideVector.height+5, slideVector.width, slideVector.height);
    }


    public Slider(Texture line, Texture slide) {
        this.line = line;
        this.slide = slide;
    }
}
