package com.mygdx.game.MyObjects.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.MyObjects.Helper;
import com.mygdx.game.MyObjects.Loader;
import com.mygdx.game.MyObjects.PausePainter;
import com.mygdx.game.MyObjects.Units.Player;
import com.mygdx.game.MyObjects.Units.Towers.Bullets.Bullet;
import com.mygdx.game.MyObjects.Units.Towers.FoundationTower;
import com.mygdx.game.MyObjects.Units.Monsters.Monster;
import com.mygdx.game.MyObjects.Units.Towers.Tower;
import com.mygdx.game.Scenes.MainMenuScreen;

import java.util.HashMap;

public final class Level1 implements Screen, Level {

    private final MyGdxGame game;
    private static OrthographicCamera camera;
    private float Timer, totalTime, waveCD;
    private Music music;
    private Array<Bullet> bullets;
    private Array<Array<String>> waves = new Array<>();
    private int currentWave, waveTime;
    private Texture base;
    private HashMap<Integer, String[]> wavesMonsterTypes = new HashMap<>();
    private int[] countMonsters = {9, 10, 7, 12, 7, 7, 20, 1};

    public void InsertWaves()
    {
        wavesMonsterTypes.put(0, new String[]{"peon"});
        wavesMonsterTypes.put(1, new String[]{"peon", "grant"});
        wavesMonsterTypes.put(2, new String[]{"grant"});
        wavesMonsterTypes.put(3, new String[]{"grant", "ogre"});
        wavesMonsterTypes.put(4, new String[]{"peon", "ogre"});
        wavesMonsterTypes.put(5, new String[]{"bomber"});
        wavesMonsterTypes.put(6, new String[]{"peon", "grant", "ogre"});
        wavesMonsterTypes.put(7, new String[]{"dragon"});

        for (int i = 0; i<countMonsters.length; i++)
        {
            Array<String> temp = new Array<>();
            String[] currentTypes = wavesMonsterTypes.get(i);
            for (int q = 0; q<countMonsters[i]; q++)
                temp.add(currentTypes[(int) (Math.random() * currentTypes.length)]);
            waves.add(temp);
        }
    }

    public Level1(int countLifes) {
        InitPlayer(countLifes);
        this.game = Loader.game;
        Timer = 0;
        totalTime = 0;
        currentWave = 0;
        waveTime = 7;
        waveCD = 0;
        Monster.currentEnemys = new Array<>();
        bullets = new Array<>();
        TowersSpawn();
        InsertWaves();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        music = Loader.GetFonMusic(1);
        this.base = Loader.GetVillageTexture();
        music.setVolume((float) ((Loader.musicVolume)/10.0));
        music.play();
    }

    @Override
    public void render(float delta) {
        totalTime += delta;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && Player.lifes>0 && !PausePainter.type.equals("Win"))
        {
            if (Player.pause)
                Player.pause = false;
            else
                Player.pause = true;
        }
        if (!Player.pause)
        {
            for (Monster enemy: Monster.currentEnemys) {
                enemy.UpdateLocation();
                if (enemy.Finish())
                {
                    Monster.currentEnemys.removeIndex(Monster.currentEnemys.indexOf(enemy, false));
                }
            }
            DoSomethingTowers();
            MonstersSpawn();
            for (int i = 0; i<bullets.size; i++)
            {
                bullets.get(i).UpdateLocation();
                if (bullets.get(i).Finish() ||
                        (bullets.get(i).GetTarget() != null && (bullets.get(i).GetTarget().GetHealth()<1 || bullets.get(i).GetTarget().Finish())) )
                {
                    bullets.removeIndex(i);
                }
            }
        }
        else
            ScreenUtils.clear(0, 0, 0f, 1);

        game.batch.begin();
        game.batch.draw(Loader.GetLevelFon(1), 0, 0, 800, 480);
        for (Monster enemy: Monster.currentEnemys)
            enemy.Draw();
        for (FoundationTower foundationTower: Player.foundationTowers)
            foundationTower.Draw();
        for (Tower tower: Player.towers)
            tower.Draw();
        for (Bullet bullet: bullets)
            bullet.Draw();

        game.batch.draw(base, 680, 50, 100, 100);
        game.batch.draw((TextureRegion)Loader.heartAnimation.getKeyFrame(totalTime, true), 700,440, 30, 30);
        game.batch.draw((TextureRegion)Loader.coinAnimation.getKeyFrame(totalTime, true), 700,400, 30, 30);
        Loader.GetFont().getData().setScale(0.7f, 0.7f);
        Loader.GetFont().draw(game.batch, String.valueOf(Player.lifes), 740, 465);
        Loader.GetFont().draw(game.batch, String.valueOf(Player.gold), 740, 425);

        if (Player.pause) PausePainter.DrawPause(!(Player.lifes>0));
        game.batch.end();

        if (PausePainter.backBut.IsClicked() && Player.pause)
        {
            game.setScreen(new MainMenuScreen());
            music.stop();
            dispose();
        }


        if (Monster.currentEnemys.size == 0 && currentWave >= waves.size)
        {
            PausePainter.type = "Win";
            Player.pause = true;
        }

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        System.gc();
    }

    public void MonstersSpawn()
    {
        if (totalTime > waveTime && Timer > 1.5 && currentWave < waves.size && waveCD <=0) {
            String monster = waves.get(currentWave).get(0);
            Monster.currentEnemys.add(Helper.SpawnMonster(monster));
            Timer = 0;
            waves.get(currentWave).removeIndex(0);
            if (waves.get(currentWave).size==0 && currentWave !=waves.size)
            {
                currentWave++;
                waveCD = 0;
                waveTime = (int)(totalTime+7);
            }
        }
        Timer += Gdx.graphics.getDeltaTime();
        waveCD -= Gdx.graphics.getDeltaTime();

        for (int i = 0; i < Monster.currentEnemys.size; i++){
            if (Monster.currentEnemys.get(i).GetHealth()<1)
            {
                Monster.currentEnemys.get(i).Death();
                if (Monster.currentEnemys.get(i).AnimationIsFinished())
                {
                    Monster.currentEnemys.removeIndex(i);
                }
            }
        }
    }
    public void TowersSpawn()
    {
        Player.towers = new Array<>();
        Player.foundationTowers = new Array<>();
        int[] ordinations = new int[]{260, 280, 450, 150, 570, 380, 150, 50, 150, 170, 570, 260};
        for (int i = 1; i <ordinations.length; i+=2)
        {
            Player.foundationTowers.add(Loader.CreateTowerFoundation(ordinations[i-1], ordinations[i], 90, 90));
        }
    }

    public void DoSomethingTowers()
    {
        for (int i = 0; i <Player.foundationTowers.size; i++)
        {
            if (Player.foundationTowers.get(i).IsClicked())
            {
                if (Player.gold - Player.costs.get(Player.foundationTowers.get(i).getType()) >-1) {
                    Tower tower = Loader.CreateTower(Player.foundationTowers.get(i), Player.foundationTowers.get(i).getType());
                    Player.towers.add(tower);
                    Player.foundationTowers.removeIndex(i);
                    Player.gold -= tower.GetCost();
                }
            }
        }

        for(Tower tower: Player.towers)
        {
            tower.Attack(bullets, Monster.currentEnemys);
        }
    }

    @Override
    public void InitPlayer(int countLifes) {
        PausePainter.type = "None";
        Player.gold = 16;
        Player.pause = false;
        Player.lifes = countLifes;
        Player.canUpdateMulti = false;
    }
}