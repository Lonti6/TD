package com.mygdx.game.MyObjects.Units.Towers.Bullets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyObjects.Loader;
import com.mygdx.game.MyObjects.Units.Monsters.Monster;

import java.awt.Rectangle;

public class Spear implements Bullet{
    private TextureRegion texture;
    private Monster target;
    Rectangle rectangle;
    private float x, y, rotation, speed;
    float modDamage;
    public Spear(int x, int y, int width, int height, float speed, TextureRegion texture, Monster target, float modDamage)
    {
        this.target = target;
        this.texture = texture;
        this.speed = speed;
        this.modDamage = modDamage;

        rotation = 0;
        rectangle = new Rectangle();
        rectangle.width = width;
        rectangle.height = height;
        rectangle.x = x;
        rectangle.y = y;
        this.x = x;
        this.y = y;
    }

    public void Draw()
    {
        Loader.game.batch.draw(texture,
                rectangle.x,
                rectangle.y,
                rectangle.width/2,
                rectangle.height/2,
                rectangle.width,
                rectangle.height,
                1,
                1,
                rotation);
    }

    public void UpdateLocation()
    {
        if ((int)(x+rectangle.width/2)<(int)target.GetCenterX())
            x+= speed;
        else if ((int)(x+rectangle.width/2) > (int)target.GetCenterX())
            x-= speed;
        if ((int)(y+rectangle.height/2)<target.GetCenterY())
            y+= speed;
        else if ((int)(y+rectangle.height/2) > target.GetCenterY())
            y-= speed;
        rectangle.x = (int) x;
        rectangle.y = (int) y;

        CheckRotation();

        if (Finish()) {
            float damage = (int) ((Math.random()*3)+1);
            System.out.println("Damage: " +damage + " Armor: " + target.armor +" Do: " + (damage*(1-target.armor)));
            target.SetHealth((target.GetHealth() - modDamage - ( damage )* (1-target.armor)));
        }
    }

    public boolean Finish()
    {
        return (Math.abs((int)(x+rectangle.width/2) - target.GetCenterX())<=5
                && Math.abs((int)(y+rectangle.height/2) - target.GetCenterY())<=5);

    }

    private void CheckRotation()
    {
        int centerX = (int)(x+rectangle.width/2);
        int centerY = (int)(y+rectangle.height/2);
        if (target.GetCenterX()<centerX && target.GetCenterY()==centerY)
            rotation = 90;
        else if (target.GetCenterX()<centerX && target.GetCenterY()<centerY)
            rotation = 135;
        else if (target.GetCenterX()==centerX && target.GetCenterY()<centerY)
            rotation = 180;
        else if (target.GetCenterX()>centerX && target.GetCenterY()<centerY)
            rotation = -135;
        else if (target.GetCenterX()>centerX && target.GetCenterY()==centerY)
            rotation = -90;
        else if (target.GetCenterX()>centerX && target.GetCenterY()>centerY)
            rotation = -45;
        else if (target.GetCenterX()==centerX && target.GetCenterY()>centerY)
            rotation = 0;
        else
            rotation = 45;
    }

    public Monster GetTarget()
    {
        return target;
    }
}
