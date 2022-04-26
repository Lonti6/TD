package com.mygdx.game.MyObjects.Units.Monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.MyObjects.Loader;
import com.mygdx.game.MyObjects.Units.Monsters.Skills.SpecialSkill;
import com.mygdx.game.MyObjects.Units.Player;

import java.awt.Rectangle;

public class Monster {
    protected static MyGdxGame game;
    public static Array<Monster> currentEnemys;

    protected int orientationX, orientationY;

    protected Rectangle rectangle;
    protected Animation currentAnimation, animationUp, animationDown, animationLeft, animationRight, animationIdle, animationDeath;
    protected Array<int[]> way;
    protected float currentSpeed, standartSpeed, x, y, elapsedTime;
    protected Sound finishSound, deathSound;
    protected float health;
    protected boolean alive = true;
    protected boolean isFrost = false;
    protected Array<SpecialSkill> skills;
    public float armor, standartArmor;
    protected boolean fly;
    protected boolean canDamage = true;
    protected int loot;

    public Monster(int x, int y, int width, int height, float speed,
                   Array<Animation> animations, Array<int[]> way,
                   Sound finishSound, Sound deathSound, float health, int armor, int loot, MyGdxGame game)
    {
        this.game = game;
        this.currentSpeed = speed;
        this.standartSpeed = speed;
        this.way = way;
        this.loot = loot;
        this.finishSound = finishSound;
        this.health = health;
        this.deathSound = deathSound;
        this.armor = armor/100.0f;
        this.standartArmor = armor/100.0f;
        this.fly = false;
        elapsedTime = 0;

        rectangle = new Rectangle();
        rectangle.width = width;
        rectangle.height = height;
        rectangle.x = x;
        rectangle.y = y;
        this.x = x;
        this.y = y;

        animationIdle =animations.get(0);
        animationUp =animations.get(1);
        animationDown =animations.get(2);
        animationLeft =animations.get(3);
        animationRight =animations.get(4);
        this.animationDeath = animations.get(5);;

        currentAnimation = animationIdle;

        orientationX = way.get(0)[2];
        orientationY = way.get(0)[3];
        way.removeIndex(0);
    }

    public Monster(int x, int y,
                   Array<Animation> animations, MyGdxGame game)
    {
        this.game = game;
        elapsedTime = 0;

        rectangle = new Rectangle();
        rectangle.x = x;
        rectangle.y = y;
        this.x = x;
        this.y = y;

        animationDown =animations.get(2);

        currentAnimation = animationDown;
    }

    public void Draw()
    {
        elapsedTime+= Gdx.graphics.getDeltaTime();
        game.batch.draw((TextureRegion) currentAnimation.getKeyFrame(elapsedTime, true), rectangle.x, rectangle.y);
    }

    public void Draw(int width, int height)
    {
        elapsedTime+= Gdx.graphics.getDeltaTime();
        game.batch.draw((TextureRegion) currentAnimation.getKeyFrame(elapsedTime, true), rectangle.x, rectangle.y, width, height);
    }

    public void UpdateLocation()
    {
        for (SpecialSkill skill: skills) {
            skill.DoSkill();
        }
        if (way.size > 0 &&
                ((rectangle.x >= way.get(0)[0] && orientationX == 1) ||
                (rectangle.x <= way.get(0)[0] && orientationX == -1) ||
                (rectangle.y >= way.get(0)[1] && orientationY == 1) ||
                (rectangle.y <= way.get(0)[1] && orientationY == -1)))
        {
            orientationX = way.get(0)[2];
            orientationY = way.get(0)[3];
            rectangle.x = way.get(0)[0];
            rectangle.y = way.get(0)[1];
            way.removeIndex(0);
        }

        if (orientationX == 1)
        {
            x += currentSpeed;
            rectangle.x = (int)(x);
            currentAnimation = animationRight;
        }

        else if (orientationX == -1)
        {
            x -= currentSpeed;
            rectangle.x = (int)(x);
            currentAnimation = animationLeft;
        }
        else if(orientationY == 1)
        {
            y += currentSpeed;
            rectangle.y = (int)y;
            currentAnimation = animationUp;
        }
        else if (orientationY == -1)
        {
            y -= currentSpeed;
            rectangle.y = (int)y;
            currentAnimation = animationDown;
        }
        else if (orientationX == 0 && orientationY == 0)
            currentAnimation = animationIdle;
    }

    public boolean Finish()
    {
        if (way.size == 0 && canDamage) {
            finishSound.play((float) (Loader.otherVolume/10.0));
            Player.lifes--;
            canDamage = false;
            Player.pause = Player.lifes<1;
        }
        return (way.size == 0);
    }

    public int GetX()
    {
        return rectangle.x;
    }
    public int GetY()
    {
        return rectangle.y;
    }
    public float GetHealth()
    {
        return health;
    }
    public void SetHealth(float value)
    {
        health = value;
    }

    public boolean AnimationIsFinished()
    {
        return (currentAnimation.getKeyFrameIndex(elapsedTime) == 3);
    }

    public void Death()
    {
        orientationX = -2;
        orientationY = -2;
        currentAnimation = animationDeath;

        if (alive) {
            deathSound.play((float) ((Loader.otherVolume)/10.0));
            elapsedTime = 0;
            Player.gold += loot;
        }
        alive = false;
    }

    public int GetCenterX()
    {
        return (rectangle.x+(rectangle.width/2));
    }
    public int GetCenterY()
    {
        return (rectangle.y+(rectangle.height/2));
    }
    public float getCurrentSpeed()
    {
        return currentSpeed;
    }
    public void setCurrentSpeed(float speed)
    {
        currentSpeed = speed;
    }
    public float getStandartSpeed() {
        return standartSpeed;
    }
    public boolean GetIsFrost()
    {
        return isFrost;
    }
    public void SetIsFrost(boolean isFrost)
    {
        this.isFrost = isFrost;
    }

    public void SetSkills(Array<SpecialSkill> skills)
    {
        this.skills = skills;
    }
    public Rectangle GetRectangle() {
        return rectangle;
    }
    public void isFLy(boolean canFly){ this.fly = canFly; }
    public boolean getFly(){return this.fly;}
}