package com.mygdx.game.MyObjects.Units.Towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyObjects.Loader;
import com.mygdx.game.MyObjects.Units.Monsters.Monster;
import com.mygdx.game.MyObjects.Units.Player;
import com.mygdx.game.MyObjects.Units.Towers.Bullets.Bullet;

import java.awt.Rectangle;

public class FrostTower extends Tower {

    private Rectangle rectangle;
    private Texture foundation, attackTexture;
    private float timer;
    private String type = "Frost";
    private boolean canAttack;
    private int num;
    private Sound attackSound;
    public static float modSpeed = 0.7f;

    public FrostTower(FoundationTower tower, Texture texture, Texture attackTexture, Sound attackSound) {
        timer = 0;
        num = 0;
        canAttack = false;
        this.rectangle = tower.rectangle;
        this.rectangle.width = (int)(tower.rectangle.width*1.2);
        this.rectangle.height = (int)(tower.rectangle.height*1.2);
        foundation = texture;
        this.attackTexture = attackTexture;
        this.attackSound = attackSound;
        this.cost = Player.costs.get(type);
        }

    @Override
    public void Attack(Array<Bullet> bullets, Array<Monster> enemys) {
        if (timer > 8) {
            for (Monster enemy : enemys) {
                if (!enemy.GetIsFrost()) {
                    enemy.setCurrentSpeed(enemy.getCurrentSpeed() * modSpeed);
                    enemy.SetIsFrost(true);
                }
            }
            timer = 0;
            canAttack = false;
            num = 0;
        }
        else if (timer > 5 && num ==0)
        {
            for (Monster enemy : enemys) {
                if (enemy.GetIsFrost())
                    if (enemy.getCurrentSpeed() < enemy.getStandartSpeed())
                    {
                        enemy.setCurrentSpeed(enemy.getStandartSpeed());
                        enemy.SetIsFrost(false);
                    }
            }
            num = 1;
        }
        if (timer>7.5 && !canAttack) {
            canAttack = true;
            attackSound.play((float) ((Loader.otherVolume)/10.0));
        }

        timer += Gdx.graphics.getDeltaTime();
    }

    @Override
    public int FindEnemy(Array<Monster> enemys) {
        return 0;
    }

    @Override
    public Array<Integer> FindEnemy(Array<Monster> enemys, int count) {
        return null;
    }

    @Override
    public void Draw() {
        game.batch.draw(foundation, rectangle.x, rectangle.y+20, rectangle.width, rectangle.height);
        if (canAttack)
            game.batch.draw(attackTexture, rectangle.x+10, (int)rectangle.getCenterY()+10);
    }

    @Override
    public int GetCost() {
        return this.cost;
    }

    @Override
    public void SetCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String GetType() {
        return type;
    }
}
