package com.mygdx.game.MyObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyObjects.Units.Monsters.Monster;
import com.mygdx.game.MyObjects.Units.Monsters.Skills.AuraArmor;
import com.mygdx.game.MyObjects.Units.Monsters.Skills.Regeneration;
import com.mygdx.game.MyObjects.Units.Monsters.Skills.SpecialSkill;
import com.mygdx.game.MyObjects.Units.Player;

public class Helper {
    private static Array<Float> floats = new Array<Float>();
    private static GlyphLayout glyphLayout = new GlyphLayout();
    private static TextureRegion[][] tmpFrames;
    private static Array<TextureRegion> animationFrames = new Array<TextureRegion>();

    public static Animation TakeAnimation(Texture img, int sizeX, int sizeY, double FPS)
    {
        tmpFrames = TextureRegion.split(img, sizeX, sizeY);

        animationFrames = new Array<TextureRegion>();

        for (int i = 0; i<tmpFrames.length; i++)
        {
            for (int j = 0; j<tmpFrames[i].length; j++)
            {
                animationFrames.add(tmpFrames[i][j]);
            }
        }

        tmpFrames = null;
        return (new Animation((float)(1f/FPS), animationFrames));
    }

    public static Animation TakeAnimation(Texture img, int sizeX, int sizeY, boolean rotationX, boolean rotationY, double FPS)
    {
        tmpFrames = TextureRegion.split(img, sizeX, sizeY);

        animationFrames = new Array<TextureRegion>();

        for (int i = 0; i<tmpFrames.length; i++)
        {
            for (int j = 0; j<tmpFrames[i].length; j++)
            {
                tmpFrames[i][j].flip(rotationX, rotationY);
                animationFrames.add(tmpFrames[i][j]);
            }
        }
        tmpFrames = null;
        return (new Animation((float)(1f/FPS), animationFrames));
    }

    public static Array<Float> GetTextLenght(String text, BitmapFont font)
    {
        floats.clear();
        glyphLayout.setText(font, text);
        floats.add(glyphLayout.width);
        floats.add(glyphLayout.height);
        return floats;
    }

    public static Monster SpawnMonster(String monster)
    {
        float heath = 0;
        int armor = 0;
        float speed = 0;
        int loot = 0;
        if (monster.equals("peon"))
        {
            speed = 1.5f;
            heath = 6;
            armor = 0;
            loot = 2;
        }
        if (monster.equals("grant"))
        {
            speed = 1;
            heath = 8;
            armor = 15;
            loot = 3;
        }
        if (monster.equals("ogre"))
        {
            speed = 1;
            heath = 22;
            armor = 0;
            loot = 3;
        }
        if (monster.equals("troll"))
        {
            speed = 2;
            heath = 14;
            armor = 0;
            loot = 3;
        }
        if (monster.equals("dragon"))
        {
            speed = 2.5f;
            heath = 50;
            armor = 50;
            loot = 5;
        }
        if (monster.equals("dragonLow"))
        {
            monster = "dragon";
            speed = 2f;
            heath = 20;
            armor = 25;
            loot = 5;
        }
        if (monster.equals("demon"))
        {
            speed = 1.5f;
            heath = 24;
            armor = 34;
            loot = 3;
        }
        if (monster.equals("bomber"))
        {
            speed = 2;
            heath = 15;
            armor = 0;
            loot = 2;
        }
        Monster tempMonster = new Monster(Loader.GetLevelOneWay(Player.currentLevel).get(0)[0], Loader.GetLevelOneWay(Player.currentLevel).get(0)[1], 32, 33,
                speed, Loader.GetAnimation(monster),
                Loader.GetLevelOneWay(Player.currentLevel),
                Loader.GetMonsterFinishSound(monster, ((int)(Math.random()*5)+1)),
                Loader.GetDeathSound(monster),
                heath, armor, loot, Loader.game);
        Array<SpecialSkill> skills = new Array<>();
        if (monster.equals("troll"))
        {
            skills.add(new Regeneration(2, tempMonster, 1));
        }
        if (monster.equals("dragon"))
        {
            tempMonster.isFLy(true);
        }
        if (monster.equals("demon"))
        {
            skills.add(new AuraArmor(tempMonster.GetRectangle(), 10));
        }
        tempMonster.SetSkills(skills);
        return tempMonster;
    }
}
