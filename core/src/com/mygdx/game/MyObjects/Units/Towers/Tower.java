package com.mygdx.game.MyObjects.Units.Towers;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.MyObjects.Loader;
import com.mygdx.game.MyObjects.Units.Monsters.Monster;
import com.mygdx.game.MyObjects.Units.Towers.Bullets.Bullet;

public abstract class Tower {

    protected MyGdxGame game = Loader.game;

    public static int cost = 0;

    abstract public void Attack(Array<Bullet> bullets, Array<Monster> enemys);
    abstract public int FindEnemy(Array<Monster> enemys);
    abstract public Array<Integer> FindEnemy(Array<Monster> enemys, int count);
    abstract public void Draw();
    abstract public int GetCost();
    abstract public void SetCost(int cost);
    abstract public String GetType();
}