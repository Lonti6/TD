package com.mygdx.game.MyObjects.Units.Towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.MyObjects.Button;
import com.mygdx.game.MyObjects.Loader;
import com.mygdx.game.MyObjects.Units.Player;

import java.awt.Rectangle;

public class    FoundationTower {
    public Rectangle rectangle;
    public Texture foundation;

    private Vector3 vector;
    private static MyGdxGame game;
    private Array<Button> icons;
    private boolean buildMenu;
    private String type;

    public FoundationTower(int x, int y, int width, int height, Texture texture, Array<Texture> icons) {
        this.game = Loader.game;
        foundation = texture;
        buildMenu = false;
        vector = new Vector3();

        rectangle = new Rectangle();
        rectangle.width = width;
        rectangle.height = height;
        rectangle.x = x;
        rectangle.y = y;

        SpawnIcons(icons);
    }

    public void Draw() {
        game.batch.draw(foundation, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        if (buildMenu) {
            for (Button but : icons) {
                but.Draw(1, 1);
                Loader.GetFont().getData().setScale(0.3f, 0.3f);
                Loader.GetFont().draw(Loader.game.batch, String.valueOf(Player.costs.get(but.getTag())), but.GetX() + 8, but.GetY());
            }
        }
    }

    public boolean IsClicked() {
        if (Gdx.input.isButtonJustPressed(0)) {
            vector.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            Loader.camera.unproject(vector);

            if (vector.x >= rectangle.x && vector.x <= rectangle.x + rectangle.width
                    && vector.y >= rectangle.y && vector.y <= rectangle.y + rectangle.height) {
                if (buildMenu)
                    buildMenu = false;
                else
                    buildMenu = true;
            }
            boolean r = false;
            for (Button but : icons) {
                if (but.IsClicked() && buildMenu) {
                    r = true;
                    type = but.getTag();
                    break;
                }
            }
            return (r);
        }
        return false;
    }

    public String getType() {
        return type;
    }

    private void SpawnIcons(Array<Texture> icons) {
        this.icons = new Array<>();
        this.icons.add(Loader.CreateButton(rectangle.x + (rectangle.width / 2) - 15, rectangle.y + rectangle.height + 5,
                30, 30, "", icons.get(0)));
        this.icons.get(this.icons.size - 1).setTag("Spear");
        this.icons.add(Loader.CreateButton(rectangle.x + (rectangle.width / 2) - 15, rectangle.y - 30,
                30, 30, "", icons.get(1)));
        this.icons.get(this.icons.size - 1).setTag("Siege");
        this.icons.add(Loader.CreateButton(rectangle.x - 35, rectangle.y + (rectangle.height / 2) - 15,
                30, 30, "", icons.get(2)));
        this.icons.get(this.icons.size - 1).setTag("Frost");
        this.icons.add(Loader.CreateButton(rectangle.x + rectangle.width + 5, rectangle.y + (rectangle.height / 2) - 15,
                30, 30, "", icons.get(3)));
        this.icons.get(this.icons.size - 1).setTag("Multi");
    }
}