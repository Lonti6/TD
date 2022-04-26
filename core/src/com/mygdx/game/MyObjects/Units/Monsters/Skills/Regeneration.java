package com.mygdx.game.MyObjects.Units.Monsters.Skills;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyObjects.Units.Monsters.Monster;

public class Regeneration implements SpecialSkill{
    private float cooldown, currentTimer;
    private int regeneration;
    private Monster monster;
    public Regeneration(long cooldown, Monster monster, int regeneration)
    {
        this.cooldown = cooldown;
        this.monster = monster;
        this.regeneration = regeneration;
        currentTimer = 0;
    }

    @Override
    public void DoSkill() {
        currentTimer += Gdx.graphics.getDeltaTime();
        //System.out.println(currentTimer + " " + cooldown);
        if (currentTimer >= cooldown)
        {
            monster.SetHealth(monster.GetHealth() + regeneration);
            currentTimer = 0;
        }
    }
}
