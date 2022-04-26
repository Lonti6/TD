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
import com.mygdx.game.MyObjects.Units.Towers.Bullets.BombBullet;
import com.mygdx.game.MyObjects.Units.Towers.Bullets.Bullet;

import java.awt.Rectangle;

public class SiegeTower extends Tower {
    private Rectangle rectangle;
    private Texture foundation;
    private float reload = 0;
    private int c;
    private Array<Button> icons;
    private boolean arrowMenu;
    private Vector3 vector;
    private String type = "Siege";
    private String attackVector = "Left";
    private int rangeAttack = 100;
    private Sound attackSound;

    public SiegeTower(FoundationTower tower, Texture texture, Sound attackSound)
    {
        this.rectangle = tower.rectangle;
        this.rectangle.width = (int)(tower.rectangle.width*1);
        this.rectangle.height =  (int)(tower.rectangle.height*1.2);
        foundation = texture;
        vector = new Vector3();
        arrowMenu = false;
        this.attackSound = attackSound;
        this.cost = Player.costs.get(type);
        SpawnIcons();
    }

    @Override
    public void Attack(Array<Bullet> bullets, Array<Monster> enemys) {
        c = FindEnemy(enemys);
        if (reload >= 4)
        {
            if (c >-1 && enemys.get(c).GetHealth()>0) {
                int[] vect;
                if (attackVector.equals("Up"))
                    vect = new int[]{rectangle.x+rectangle.width/2, (rectangle.y+rectangle.height/2)+rangeAttack};
                else if (attackVector.equals("Down"))
                    vect = new int[]{rectangle.x+rectangle.width/2, (rectangle.y+rectangle.height/2)-rangeAttack};
                else if (attackVector.equals("Left"))
                    vect = new int[]{(rectangle.x+rectangle.width/2)-rangeAttack, rectangle.y+rectangle.height/2};
                else
                    vect = new int[]{(rectangle.x+rectangle.width/2)+rangeAttack, rectangle.y+rectangle.height/2};
                bullets.add(new BombBullet(rectangle.x+rectangle.width/2, rectangle.y+rectangle.height/2,
                        21, 30, 3f,
                        Loader.GetBulletTexture("bomb"),
                        vect, attackVector, enemys));
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
            enemyIndex = 0;
        }
        return enemyIndex;
    }

    @Override
    public Array<Integer> FindEnemy(Array<Monster> enemys, int count) {
        return null;
    }

    @Override
    public void Draw() {
        game.batch.draw(foundation, rectangle.x, rectangle.y+20, rectangle.width, rectangle.height);
        if (arrowMenu)
        {
            for (Button but: icons)
                but.Draw(1, 1);
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


    private void IsClicked()
    {
        if (Gdx.input.isButtonJustPressed(0))
        {
            vector.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            Loader.camera.unproject(vector);

            if (vector.x >= rectangle.x && vector.x<=rectangle.x+rectangle.width
                    && vector.y >= rectangle.y && vector.y <= rectangle.y+rectangle.height)
            {
                if (arrowMenu)
                    arrowMenu = false;
                else
                    arrowMenu = true;
            }
            for (Button but: icons) {
                if (but.IsClicked() && arrowMenu)
                {
                    arrowMenu = false;
                    attackVector = but.getTag();
                    break;
                }
            }
        }
    }

    private void SpawnIcons() {
        this.icons = new Array<>();
        this.icons.add(Loader.CreateButton(rectangle.x + (rectangle.width / 2) - 15, rectangle.y + rectangle.height + 5,
                30, 30, "", Loader.GetArrowIcons().get(0)));
        this.icons.get(this.icons.size-1).setTag("Up");
        this.icons.add(Loader.CreateButton(rectangle.x+(rectangle.width/2)-15, rectangle.y-30,
                30, 30, "", Loader.GetArrowIcons().get(1)));
        this.icons.get(this.icons.size-1).setTag("Down");
        this.icons.add(Loader.CreateButton(rectangle.x -35, rectangle.y+(rectangle.height/2)-15,
                30, 30, "",Loader.GetArrowIcons().get(2)));
        this.icons.get(this.icons.size-1).setTag("Left");
        this.icons.add(Loader.CreateButton(rectangle.x+rectangle.width+5, rectangle.y+(rectangle.height/2)-15,
                30, 30, "", Loader.GetArrowIcons().get(3)));
        this.icons.get(this.icons.size-1).setTag("Right");
    }
}
