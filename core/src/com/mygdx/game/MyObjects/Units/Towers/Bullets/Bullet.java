package com.mygdx.game.MyObjects.Units.Towers.Bullets;

import com.mygdx.game.MyObjects.Units.Monsters.Monster;

public interface Bullet {
    void Draw();
    void UpdateLocation();
    boolean Finish();
    Monster GetTarget();
}
