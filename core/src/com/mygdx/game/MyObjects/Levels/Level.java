package com.mygdx.game.MyObjects.Levels;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyObjects.Units.Monsters.Monster;

public interface Level {
    void InsertWaves();
    void MonstersSpawn();
    void TowersSpawn();
    void DoSomethingTowers();
    void InitPlayer(int countLifes);
}
