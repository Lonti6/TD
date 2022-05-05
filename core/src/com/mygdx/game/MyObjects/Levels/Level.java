package com.mygdx.game.MyObjects.Levels;

public interface Level {
    void InsertWaves();
    void MonstersSpawn();
    void TowersSpawn();
    void DoSomethingTowers();
    void InitPlayer(int countLifes);
}
