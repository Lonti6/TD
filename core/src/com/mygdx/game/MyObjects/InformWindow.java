package com.mygdx.game.MyObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Array;

public class InformWindow {
    Texture photoUnit, frameUp, frameDown, background;
    Button button;
    String description, name;
    GlyphLayout glyphLayoutDescription, glyphLayoutName;
    int x, y, height, width;
    Array<Sound> sounds;

    public InformWindow(Texture photoUnit, String name, String description, int x, int y, Array<Sound> sounds)
    {
        this.photoUnit = photoUnit;
        this.description = description;
        this.name = name;
        this.frameDown = Loader.GetTexture("frame_Down.png");
        this.frameUp = Loader.GetTexture("frame_Up.png");
        this.x = x;
        this.y = y;
        this.height = 250;
        this.width = 120;
        this.background = Loader.GetTexture("almFon.jpg");
        button = Loader.CreateButton(x+7, y-80, width-15, 100, "", photoUnit);
        this.sounds = sounds;
    }

    public void Update(){
        button.SetLocation(x+7, y-80);
        try {
            if (button.IsClicked())
                sounds.get((int) (Math.random() * sounds.size)).play(Loader.otherVolume);
        }
        catch (Exception e){System.out.println("Нет звука");};
    }

    public void Draw()
    {
        Loader.game.batch.draw(background, x, y-height+25, width, height);
        Loader.game.batch.draw(frameUp, x, y, width, 25);
        button.Draw(1, 1);
        Loader.GetFont().getData().setScale(0.4f, 0.4f);
        glyphLayoutName = Loader.GetFont().draw(Loader.game.batch, name, (x+width/16),y-90, width-10, 1, true);
        Loader.GetFont().getData().setScale(0.25f, 0.25f);
        glyphLayoutDescription = Loader.GetFont().draw(Loader.game.batch, description, (x+width/16),y-100-glyphLayoutName.height, width-10, 1, true);
        Loader.game.batch.draw(frameDown, x, y-225, width, 25);
    }

    public void SetY(int y)
    {
        this.y = y;
    }
    public int GetY()
    {
        return this.y;
    }
    public int GetHeight()
    {
        return this.height;
    }
}
