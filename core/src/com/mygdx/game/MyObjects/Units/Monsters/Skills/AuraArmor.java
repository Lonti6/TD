package com.mygdx.game.MyObjects.Units.Monsters.Skills;

import com.mygdx.game.MyObjects.Units.Monsters.Monster;

import java.awt.Rectangle;

public class AuraArmor implements SpecialSkill {
    private Rectangle rectangle;
    private int countArmor;
    public AuraArmor(Rectangle rectangle, int countArmor)
    {
        this.rectangle =rectangle;
        this.countArmor = countArmor;
    }
    @Override
    public void DoSkill() {
        if (Monster.currentEnemys.size > 0)
        {
            for (int i = 0; i<Monster.currentEnemys.size; i++)
            {
                Monster temp = Monster.currentEnemys.get(i);
                if (50 > Math.sqrt(Math.pow(temp.GetX()-rectangle.x, 2)+Math.pow(temp.GetY()-rectangle.y, 2)))
                    temp.armor = temp.standartArmor+(countArmor/100f);
            }
        }
    }
}
