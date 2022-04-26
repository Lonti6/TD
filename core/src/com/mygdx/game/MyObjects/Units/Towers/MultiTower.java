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

public class MultiTower extends Tower {
    String type = "Multi";
    private Sound attackSound;
    private Rectangle rectangle;
    private Texture foundation;
    private float reload = 0;
    private int countEnemys = 3;
    private Array<Integer> c;
    private float modDamage;
    private Button icon;
    private boolean buildMenu;
    private Vector3 vector;
    private int costUp;

    public MultiTower(FoundationTower tower, Texture texture, Sound attackSound) {
        buildMenu = false;
        this.rectangle = tower.rectangle;
        this.rectangle.width = (int)(tower.rectangle.width*1.5);
        this.rectangle.height =  (int)(tower.rectangle.height*1.2);
        foundation = texture;
        this.modDamage = 0;
        c = new Array<Integer>();
        this.attackSound = attackSound;
        this.cost = Player.costs.get(type);
        SpawnIcons();
        this.vector = new Vector3();
        costUp = 3;

    }

    @Override
    public void Attack(Array<Bullet> bullets, Array<Monster> enemys) {
        IsClicked();
        c.clear();
        c = FindEnemy(enemys, countEnemys);
        if (reload >= 1.5)
        {
            if (c.size > 0)
            {
                attackSound.play((float) ((Loader.otherVolume)/10.0));
                for (int i = 0; i <c.size; i++) {
                    if (c.get(i) > -1 && enemys.get(c.get(i)).GetHealth() > 0) {
                        bullets.add(new Spear(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height / 2, 7, 30, 3f,
                                Loader.GetBulletTexture("lightning"), enemys.get(c.get(i)), modDamage));
                    }
                }
            }
            reload = 0;
        }
        c.clear();
        reload += Gdx.graphics.getDeltaTime();
    }

    @Override
    public int FindEnemy(Array<Monster> enemys) {
        return 0;
    }

    @Override
    public Array<Integer> FindEnemy(Array<Monster> enemys, int count) {
        Array<Integer> enemysIndex = new Array<>();
        if (enemys.size > 0) {
            for (int i = 0; i < count; i++)
                enemysIndex.add((int) (Math.random() * enemys.size));
        }
        return enemysIndex;
    }

    @Override
    public void Draw() {
        game.batch.draw(foundation, rectangle.x, rectangle.y+20, rectangle.width, rectangle.height);
        if (buildMenu)
        {
            icon.Draw(1, 1);
            Loader.GetFont().getData().setScale(0.3f, 0.3f);
            Loader.GetFont().draw(Loader.game.batch, String.valueOf(costUp), icon.GetX()+8, icon.GetY());
        }
    }

    private void IsClicked()
    {
        if (Gdx.input.isButtonJustPressed(0) && Player.canUpdateMulti)
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
            if (icon.IsClicked()) System.out.println(buildMenu);
            if (icon.IsClicked() && !buildMenu && costUp < 13)
            {
                if (Player.gold - costUp >-1) {
                    Player.gold -= costUp;
                    costUp *= 2;
                    modDamage += 0.5f;
                    System.out.println(modDamage);
                }
            }
        }
    }

    private void SpawnIcons() {
        icon = Loader.CreateButton(rectangle.x + (rectangle.width / 2) - 15, rectangle.y+(rectangle.height/2)-55,
                30, 30, "", Loader.GetTexture("towers/icons/damageUpIcon.png"));
        icon.setTag("DamageUp");
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
