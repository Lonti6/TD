package com.mygdx.game.MyObjects.Units;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyObjects.Units.Towers.FoundationTower;
import com.mygdx.game.MyObjects.Units.Towers.Tower;

import java.util.HashMap;

public class Player {
    public static void Init()
    {
        costs.put("Spear", 7);
        costs.put("Siege", 13);
        costs.put("Frost", 20);
        costs.put("Multi", 22);
    }
    public static int lifes;
    public static int gold;
    public static boolean pause;
    public static HashMap<String, Integer> costs= new HashMap<>();
    public static Array<FoundationTower> foundationTowers;
    public static Array<Tower> towers;
    public static int currentLevel;
    public static boolean canUpdateMulti;
}
