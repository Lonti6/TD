package com.mygdx.game.MyObjects.Units.Towers.Bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyObjects.Loader;
import com.mygdx.game.MyObjects.Units.Monsters.Monster;

import java.awt.Rectangle;

public class BombBullet implements Bullet{
    private TextureRegion texture;
    private Rectangle rectangle;
    private float x, y, rotation, speed;
    private int[] target;
    private String vector;
    boolean finish = false;
    private Animation boomEffect;
    private float elapsedTime;
    private Array<Monster> enemys;
    private boolean canAttack = true;

    public BombBullet(int x, int y, int width, int height, float speed,
                      TextureRegion texture, int[] target, String vector, Array<Monster> enemys)
    {
        this.texture = texture;
        this.speed = speed;
        this.target = target;
        this.vector = vector;
        this.enemys = enemys;
        boomEffect = Loader.CreateBoomEffect();
        elapsedTime = 0;

        rotation = 0;
        rectangle = new Rectangle();
        rectangle.width = width;
        rectangle.height = height;
        rectangle.x = x;
        rectangle.y = y;
        this.x = x;
        this.y = y;
        CheckRotation();
    }

    @Override
    public void Draw() {
        if (!finish) {
            Loader.game.batch.draw(texture,
                    rectangle.x,
                    rectangle.y,
                    rectangle.width / 2,
                    rectangle.height / 2,
                    rectangle.width,
                    rectangle.height,
                    1,
                    1,
                    rotation);
        }
        else
        {
            elapsedTime += Gdx.graphics.getDeltaTime();
            Loader.game.batch.draw((TextureRegion) boomEffect.getKeyFrame(elapsedTime, false),
                    rectangle.x,
                    rectangle.y,
                    rectangle.width,
                    rectangle.height);
        }
    }

    @Override
    public void UpdateLocation() {
        if (!finish) {
            rectangle.x = (int) x;
            rectangle.y = (int) y;

            if (vector.equals("Up"))
                y += speed;
            else if (vector.equals("Down"))
                y -= speed;
            else if (vector.equals("Left"))
                x -= speed;
            else if (vector.equals("Right"))
                x += speed;

            if (vector.equals("Up") && y >= target[1]) {
                finish = true;
                rectangle.width = 70;
                rectangle.height = 65;
                rectangle.x -= rectangle.width/2;
                rectangle.y -= rectangle.height / 2;
            }
            else if (vector.equals("Down") && y <= target[1]) {
                finish = true;
                rectangle.width = 70;
                rectangle.height = 65;
                rectangle.x -= rectangle.width/2;
                rectangle.y -= rectangle.height / 2;
            }
            else if (vector.equals("Left") && x <= target[0]) {
                rectangle.width = 70;
                rectangle.height = 65;
                rectangle.x -= rectangle.width/2;
                finish = true;
            }
            else if (vector.equals("Right") && x >= target[0]) {
                rectangle.width = 70;
                rectangle.height = 65;
                rectangle.x -= rectangle.width/2;
                finish = true;
            }
        }
        if (finish && canAttack) {
            FindEnemys();
            canAttack = false;
        }
    }
    @Override
    public boolean Finish() {
        return finish && boomEffect.isAnimationFinished(elapsedTime);
    }

    private void CheckRotation()
    {
        if (vector.equals("Up"))
            rotation = 0;
        else if (vector.equals("Down"))
            rotation = 180;
        else if (vector.equals("Left"))
            rotation = 90;
        else
            rotation = -90;
    }

    @Override
    public Monster GetTarget() {
        return null;
    }

    public void FindEnemys() {
        if (enemys.size > 0)
        {
            for (int i = 0; i<enemys.size; i++)
            {
                Monster currenEnemy = enemys.get(i);
                if (50 > Math.sqrt(Math.pow(currenEnemy.GetX()-rectangle.x, 2)+Math.pow(currenEnemy.GetY()-rectangle.y, 2)))
                {
                    if (!currenEnemy.getFly())
                        currenEnemy.SetHealth(currenEnemy.GetHealth()/2);
                }
            }
        }
    }
}
