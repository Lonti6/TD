package com.mygdx.game.MyObjects.Units.Towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyObjects.Button;
import com.mygdx.game.MyObjects.Loader;
import com.mygdx.game.MyObjects.Units.Monsters.Monster;
import com.mygdx.game.MyObjects.Units.Player;
import com.mygdx.game.MyObjects.Units.Towers.Bullets.Bullet;
import com.mygdx.game.MyObjects.Units.Towers.Bullets.Spear;

import java.awt.Rectangle;

public class SpearTower extends Tower {
    private Rectangle rectangle;
    private Texture foundation;
    private float reload = 0;
    private int c;
    private String type = "Spear";
    private Sound attackSound;
    private Button icon;
    private boolean buildMenu = false;
    private Vector3 vector;
    private int modDamage;

    public SpearTower(FoundationTower tower, Texture texture, Sound attackSound) {
        this.rectangle = tower.rectangle;
        this.rectangle.width = (int)(tower.rectangle.width*0.9);
        this.rectangle.height =  (int)(tower.rectangle.height*1.2);
        foundation = texture;
        this.attackSound = attackSound;
        this.cost = Player.costs.get(type);
        SpawnIcons();
        this.modDamage = 0;

        vector = new Vector3();
        buildMenu = false;
    }

    @Override
    public void Attack(Array<Bullet> bullets, Array<Monster> enemys) {
        c = FindEnemy(enemys);
        if (reload >= 1.5)
        {
            if (c >-1 && enemys.get(c).GetHealth()>0) {
                bullets.add(new Spear(rectangle.x+rectangle.width/2, rectangle.y+rectangle.height/2, 7, 30, 3f,
                        Loader.GetBulletTexture("spear"), enemys.get(c), modDamage));
                attackSound.play((float) ((Loader.otherVolume)/10.0));
            }
            reload = 0;
        }
        reload += Gdx.graphics.getDeltaTime();
    }

    @Override
    public int FindEnemy(Array<Monster> enemys) {
        IsClicked();
        int enemyIndex = -1;
        if (enemys.size > 0)
        {
            //вычисление стартового расстояния до противника
            double length = Math.sqrt(Math.pow(enemys.get(0).GetX()-rectangle.x, 2)+Math.pow(enemys.get(0).GetY()-rectangle.y, 2));
            enemyIndex = 0;
            for (int i = 1; i<enemys.size; i++)
            {
                //если расстояние до текущего противника ближе, то меняем ближайшее расстояние до противника и его индекс
                if (length > Math.sqrt(Math.pow(enemys.get(i).GetX()-rectangle.x, 2)+Math.pow(enemys.get(i).GetY()-rectangle.y, 2)))
                {
                    length = Math.sqrt(Math.pow(enemys.get(i).GetX()-rectangle.x, 2)+Math.pow(enemys.get(i).GetY()-rectangle.y, 2));
                    enemyIndex = i;
                }
            }
        }
        //возвращаем индекс противника
        return enemyIndex;
    }

    private void SpawnIcons() {
        icon = Loader.CreateButton(rectangle.x + (rectangle.width / 2) - 15, rectangle.y+(rectangle.height/2)-55,
                30, 30, "", Loader.getTowerIcons().get(3));
        icon.setTag("Multi");
    }

    private void IsClicked()
    {
        if (Gdx.input.isButtonJustPressed(0))
        {
            vector.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            Loader.camera.unproject(vector);

            if (vector.x >= rectangle.x && vector.x<=rectangle.x+rectangle.width
                    && vector.y >= rectangle.y && vector.y <= rectangle.y+rectangle.height)
            {
                if (buildMenu)
                    buildMenu = false;
                else
                    buildMenu = true;
            }
            if (icon.IsClicked() && !buildMenu)
            {
                if (Player.gold - Player.costs.get(icon.getTag()) >-1) {
                    type = icon.getTag();
                    Player.towers.set(Player.towers.indexOf(this, true),
                            Loader.CreateTower(Loader.CreateTowerFoundation(rectangle.x, rectangle.y, 90, 90), type));
                    Player.gold -= Player.costs.get(icon.getTag());
                }
            }
        }
    }

    @Override
    public Array<Integer> FindEnemy(Array<Monster> enemys, int count) {
        return null;
    }

    @Override
    public void Draw() {
        game.batch.draw(foundation, rectangle.x, rectangle.y+20, rectangle.width, rectangle.height);
        if (buildMenu)
        {
                icon.Draw(1, 1);
                Loader.GetFont().getData().setScale(0.3f, 0.3f);
                Loader.GetFont().draw(Loader.game.batch, String.valueOf(Player.costs.get(icon.getTag())), icon.GetX()+8, icon.GetY());
        }
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
    public String GetType()
    {
        return type;
    }
}
