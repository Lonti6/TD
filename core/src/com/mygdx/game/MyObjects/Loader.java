package com.mygdx.game.MyObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.MyObjects.Units.Player;
import com.mygdx.game.MyObjects.Units.Towers.FoundationTower;
import com.mygdx.game.MyObjects.Units.Towers.FrostTower;
import com.mygdx.game.MyObjects.Units.Towers.MultiTower;
import com.mygdx.game.MyObjects.Units.Towers.SiegeTower;
import com.mygdx.game.MyObjects.Units.Towers.SpearTower;
import com.mygdx.game.MyObjects.Units.Towers.Tower;

import java.util.HashMap;
import java.util.Map;

public class Loader {
    public static Camera camera;
    public static MyGdxGame game;

    private static AssetManager manager = new AssetManager();
    private static Map<String, Array> animations = new HashMap<>();
    private static Map<Integer, Array> levelsWays = new HashMap<>();
    private static Map<String, Texture> photoMosters = new HashMap<>();
    private static Preferences preferences = Gdx.app.getPreferences("Settings");;

    public static Array<Texture> getTowerIcons() {
        return towerIcons;
    }

    private static Array<Texture> towerIcons, arrowIcons;
    public static Animation heartAnimation = Helper.TakeAnimation(new Texture("heartAnim.png"), 19, 18, 5);
    public static Animation coinAnimation = Helper.TakeAnimation(new Texture("coin.png"), 15, 15, 5);

    private static Sprite pauseBg;
    private Loader(){};
    public static int otherVolume, musicVolume;

    public static void Init()
    {
        Player.Init();
        manager.load("fonts/pix.fnt", BitmapFont.class);
        manager.load("but.png", Texture.class);
        manager.load("butG.png", Texture.class);
        manager.load("village.png", Texture.class);
        for (int i = 0; i<4; i++)
            manager.load("fon"+i+".png", Texture.class);
        pauseBg = new Sprite(new Texture("pauseBg.png"));
        pauseBg.setColor(0, 0, 0, 0.4f);

        for (int i = 0; i<2; i++)
            manager.load("sounds/levelMusic"+i+".mp3", Music.class);

        manager.load("sounds/winSound.mp3", Sound.class);
        manager.load("sounds/loseSound.mp3", Sound.class);

        manager.load("frame_Up.png", Texture.class);
        manager.load("frame_Down.png", Texture.class);
        manager.load("almFon.jpg", Texture.class);

        manager.load("towers/icons/damageUpIcon.png", Texture.class);

        UploadFirstLevel();
        UploadSecondLevel();
        UploadThirdLevel();
        UploadTowers();
        UploadPeon();
        UploadGrant();
        UploadOgre();
        UploadTroll();
        UploadBomber();
        UploadDragon();
        UploadDemon();
        UploadGryph();
    }

    private static boolean Update()
    {
        return manager.update();
    }

    private static void Finish()
    {
        manager.finishLoading();
    }

    public static boolean Finishing()
    {
        if (Update())
        {
            Finish();
            otherVolume = preferences.getInteger("OtherVolume");
            musicVolume = preferences.getInteger("FonMusicVolume");
            return true;
        }
        return false;
    }

    public static float GetProgress()
    {
        return manager.getProgress();
    }

    public static void Dispose()
    {
        manager.dispose();
    }

    public static Button CreateButton(int x, int y, int width, int height, String text)
    {
        return new Button(x, y, width, height, text, manager.<Texture>get("but.png"), game);
    }

    public static Button CreateButton(int x, int y, int width, int height, String text, Texture texture)
    {
        return new Button(x, y, width, height, text, texture, game);
    }

    public static FoundationTower CreateTowerFoundation(int x, int y, int width, int height)
    {
        return new FoundationTower(x, y, width, height,
                manager.<Texture>get("towers/foundationTower.png", Texture.class), towerIcons);
    }

    public static Tower CreateTower(FoundationTower foundation, String type)
    {
        if (type.equals("Spear"))
            return new SpearTower(foundation, manager.get("towers/firstTower/firstTower.png", Texture.class),
                    manager.<Sound>get("towers/firstTower/AttackSpear.mp3"));
        else if (type.equals("Frost"))
            return new FrostTower(foundation, manager.get("towers/thirdTower/thirdTower.png", Texture.class),
                    manager.<Texture>get("towers/thirdTower/frostEffect.png"),
                    manager.<Sound>get("towers/thirdTower/FrostSound.mp3"));
        else if (type.equals("Multi"))
            return new MultiTower(foundation, manager.get("towers/fourthTower/fourthTower.png", Texture.class),
                    manager.<Sound>get("towers/fourthTower/MultiBullet.mp3"));
        else
            return new SiegeTower(foundation, manager.get("towers/secondTower/secondTower.png", Texture.class),
                    manager.<Sound>get("towers/secondTower/AttackSoundSiege.mp3"));
    }

    public static Animation CreateBoomEffect()
    {
        return Helper.TakeAnimation(manager.<Texture>get("towers/secondTower/boomEffect.png"), 62, 54, 3);
    }

    private static void UploadTowers()
    {
        towerIcons = new Array<>();
        for (int i = 0; i<4; i++)
        {
            towerIcons.add(new Texture("towers/icons/icon"+(i+1)+".png"));
        }
        manager.load("towers/foundationTower.png", Texture.class);
        manager.load("towers/firstTower/firstTower.png", Texture.class);
        manager.load("towers/firstTower/AttackSpear.mp3", Sound.class);
        manager.load("towers/secondTower/secondTower.png", Texture.class);
        manager.load("towers/bullets/spear.png", Texture.class);
        manager.load("towers/bullets/bomb.png", Texture.class);
        manager.load("towers/bullets/lightning.png", Texture.class);
        manager.load("towers/secondTower/boomEffect.png", Texture.class);
        manager.load("towers/secondTower/AttackSoundSiege.mp3", Sound.class);
        manager.load("towers/thirdTower/thirdTower.png", Texture.class);
        manager.load("towers/thirdTower/frostEffect.png", Texture.class);
        manager.load("towers/thirdTower/FrostSound.mp3", Sound.class);
        manager.load("towers/fourthTower/fourthTower.png", Texture.class);
        manager.load("towers/fourthTower/MultiBullet.mp3", Sound.class);

        photoMosters.put("spearTower", new Texture("towers/firstTower/firstTower.png"));
        photoMosters.put("boomTower", new Texture("towers/secondTower/secondTower.png"));
        photoMosters.put("frostTower", new Texture("towers/thirdTower/thirdTower.png"));
        photoMosters.put("multiTower", new Texture("towers/fourthTower/fourthTower.png"));

        arrowIcons = new Array<>();
        for (int i = 0; i<4; i++)
        {
            arrowIcons.add(new Texture("towers/icons/arrow"+i+".png"));
        }
    }

    private static void UploadFirstLevel()
    {
        manager.load("levelsMaps/level1.png", Texture.class);

        Array<int[]> levelOneWay = new Array<>();
        levelOneWay.add(new int[]{ 0, 420, 1, 0 });
        levelOneWay.add(new int[]{ 505, 420, 0, -1 });
        levelOneWay.add(new int[]{ 505, 290, -1, 0  });
        levelOneWay.add(new int[]{ 380, 290, 0, -1 });
        levelOneWay.add(new int[]{ 380, 220, -1, 0 });
        levelOneWay.add(new int[]{ 300, 220, 0, -1 });
        levelOneWay.add(new int[]{ 300, 73, 1, 0 });
        levelOneWay.add(new int[]{ 625, 73, 0, 0 });

        levelsWays.put(1, levelOneWay);
    }

    private static void UploadSecondLevel()
    {
        manager.load("levelsMaps/level2.png", Texture.class);

        Array<int[]> levelTwoWay = new Array<>();
        levelTwoWay.add(new int[]{ 0, 105, 1, 0 });
        levelTwoWay.add(new int[]{ 165, 105, 0, 1 });
        levelTwoWay.add(new int[]{ 165, 400, 1, 0  });
        levelTwoWay.add(new int[]{ 380, 400, 0, -1 });
        levelTwoWay.add(new int[]{ 380, 240, -1, 0 });
        levelTwoWay.add(new int[]{ 305, 240, 0, -1 });
        levelTwoWay.add(new int[]{ 305, 100, 1, 0 });
        levelTwoWay.add(new int[]{ 605, 100, 0, 1 });
        levelTwoWay.add(new int[]{ 605, 240, -1, 0 });
        levelTwoWay.add(new int[]{ 505, 240, 0, 1 });
        levelTwoWay.add(new int[]{ 505, 400, 1, 0 });
        levelTwoWay.add(new int[]{ 680, 400, 0, 0 });

        levelsWays.put(2, levelTwoWay);
    }

    private static void UploadThirdLevel()
    {
        manager.load("levelsMaps/level3.png", Texture.class);

        Array<int[]> levelThreeWay = new Array<>();
        levelThreeWay.add(new int[]{ 0, 80, 1, 0 });
        levelThreeWay.add(new int[]{ 680, 80, 0, 1 });
        levelThreeWay.add(new int[]{ 680, 80, 0, 1 });
        levelThreeWay.add(new int[]{ 680, 310, -1, 0 });
        levelThreeWay.add(new int[]{ 45, 310, 0, 1 });
        levelThreeWay.add(new int[]{ 45, 430, 1, 0 });
        levelThreeWay.add(new int[]{ 220, 430, 0, 0 });

        levelsWays.put(3, levelThreeWay);
    }

    private static void UploadPeon()
    {
        Array<Animation> tempAnimationsPeon = new Array<Animation>();
        tempAnimationsPeon.add(Helper.TakeAnimation(new Texture("monsters/peon/peon_Idle.png"), 32, 32, 6));
        tempAnimationsPeon.add(Helper.TakeAnimation(new Texture("monsters/peon/peon_Up.png"), 32, 33, 6));
        tempAnimationsPeon.add(Helper.TakeAnimation(new Texture("monsters/peon/peon_Down.png"), 32, 32, 6));
        tempAnimationsPeon.add(Helper.TakeAnimation(new Texture("monsters/peon/peon_Left.png"), 32, 32, 6));
        tempAnimationsPeon.add(Helper.TakeAnimation(new Texture("monsters/peon/peon_Right.png"), 32, 32, 6));
        tempAnimationsPeon.add(Helper.TakeAnimation(new Texture("monsters/peon/peon_Death.png"), 34, 32, 1.5));

        animations.put("peon", tempAnimationsPeon);
        photoMosters.put("peon", new Texture("monsters/peon/peon_Idle.png"));
        for (int i = 1; i<6; i++) {
            manager.load("monsters/peon/finishSound"+i+".mp3", Sound.class);
        }
        manager.load("monsters/peon/death.mp3", Sound.class);
    }
    private static void UploadGrant()
    {
        Array<Animation> tempAnimationsGrant = new Array<Animation>();
        tempAnimationsGrant.add(Helper.TakeAnimation(new Texture("monsters/grant/grant_Idle.png"), 39, 36, 6));
        tempAnimationsGrant.add(Helper.TakeAnimation(new Texture("monsters/grant/grant_Up.png"), 38, 49, 6));
        tempAnimationsGrant.add(Helper.TakeAnimation(new Texture("monsters/grant/grant_Down.png"), 39, 40, 6));
        tempAnimationsGrant.add(Helper.TakeAnimation(new Texture("monsters/grant/grant_Left.png"), 43, 42, 6));
        tempAnimationsGrant.add(Helper.TakeAnimation(new Texture("monsters/grant/grant_Right.png"), 43, 42, 6));
        tempAnimationsGrant.add(Helper.TakeAnimation(new Texture("monsters/grant/grant_Death.png"), 55, 51, 1.5));

        animations.put("grant", tempAnimationsGrant);
        photoMosters.put("grant", new Texture("monsters/grant/grant_Idle.png"));
        for (int i = 1; i<6; i++) {
            manager.load("monsters/grant/finishSound"+i+".mp3", Sound.class);
        }
        manager.load("monsters/grant/death.mp3", Sound.class);
    }
    private static void UploadOgre()
    {
        //огр
        Array<Animation> tempAnimationsOgre = new Array<Animation>();
        tempAnimationsOgre.add(Helper.TakeAnimation(new Texture("monsters/ogre/ogre_Idle.png"), 51, 46, 6));
        tempAnimationsOgre.add(Helper.TakeAnimation(new Texture("monsters/ogre/ogre_Up.png"), 50, 53, 6));
        tempAnimationsOgre.add(Helper.TakeAnimation(new Texture("monsters/ogre/ogre_Down.png"), 51, 56, 6));
        tempAnimationsOgre.add(Helper.TakeAnimation(new Texture("monsters/ogre/ogre_Left.png"), 53, 49, 6));
        tempAnimationsOgre.add(Helper.TakeAnimation(new Texture("monsters/ogre/ogre_Right.png"), 53, 49, 6));
        tempAnimationsOgre.add(Helper.TakeAnimation(new Texture("monsters/ogre/ogre_Death.png"), 50, 47, 1.5));

        animations.put("ogre", tempAnimationsOgre);
        photoMosters.put("ogre", new Texture("monsters/ogre/ogre_Idle.png"));
        for (int i = 1; i<6; i++) {
            manager.load("monsters/ogre/finishSound"+i+".mp3", Sound.class);
        }
        manager.load("monsters/ogre/death.mp3", Sound.class);
    }

    private static void UploadTroll()
    {
        //тролль
        Array<Animation> tempAnimationsTroll = new Array<Animation>();
        tempAnimationsTroll.add(Helper.TakeAnimation(new Texture("monsters/troll/troll_Idle.png"), 34, 43, 6));
        tempAnimationsTroll.add(Helper.TakeAnimation(new Texture("monsters/troll/troll_Up.png"), 38, 48, 6));
        tempAnimationsTroll.add(Helper.TakeAnimation(new Texture("monsters/troll/troll_Down.png"), 39, 44, 6));
        tempAnimationsTroll.add(Helper.TakeAnimation(new Texture("monsters/troll/troll_Left.png"), 39, 43, 6));
        tempAnimationsTroll.add(Helper.TakeAnimation(new Texture("monsters/troll/troll_Right.png"), 39, 43, 6));
        tempAnimationsTroll.add(Helper.TakeAnimation(new Texture("monsters/troll/troll_Death.png"), 45, 56, 2));

        animations.put("troll", tempAnimationsTroll);
        photoMosters.put("troll", new Texture("monsters/troll/troll_Idle.png"));
        for (int i = 1; i<6; i++) {
            manager.load("monsters/troll/finishSound"+i+".mp3", Sound.class);
        }
        manager.load("monsters/troll/death.mp3", Sound.class);
    }

    private static void UploadBomber()
    {
        //подрывник
        Array<Animation> tempAnimationsBomber = new Array<Animation>();
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/bomber/bomber_Idle.png"), 37, 34, 6));
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/bomber/bomber_Up.png"), 40, 41, 6));
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/bomber/bomber_Down.png"), 41, 39, 6));
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/bomber/bomber_Left.png"), 36, 43, 6));
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/bomber/bomber_Right.png"), 36, 43, 6));
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/bomber/bomber_Death.png"), 57, 49, 2.5));

        animations.put("bomber", tempAnimationsBomber);
        photoMosters.put("bomber", new Texture("monsters/bomber/bomber_Idle.png"));
        for (int i = 1; i<6; i++) {
            manager.load("monsters/bomber/finishSound"+i+".mp3", Sound.class);
        }
        manager.load("monsters/bomber/death.mp3", Sound.class);
    }

    private static void UploadDragon()
    {
        //дракон
        Array<Animation> tempAnimationsBomber = new Array<Animation>();
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/dragon/dragon_Idle.png"), 80, 53, 6));
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/dragon/dragon_Up.png"), 80, 79, 6));
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/dragon/dragon_Down.png"), 80, 79, 6));
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/dragon/dragon_Left.png"), 88, 77, 6));
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/dragon/dragon_Right.png"), 88, 77, 6));
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/dragon/dragon_Death.png"), 82, 80, 2.5));

        animations.put("dragon", tempAnimationsBomber);
        photoMosters.put("dragon", new Texture("monsters/dragon/dragon_Idle.png"));
        for (int i = 1; i<6; i++) {
            manager.load("monsters/dragon/finishSound"+i+".mp3", Sound.class);
        }
        manager.load("monsters/dragon/death.mp3", Sound.class);
    }

    private static void UploadDemon()
    {
        //демон
        Array<Animation> tempAnimationsBomber = new Array<Animation>();
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/demon/demon_Idle.png"), 43, 54, 6));
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/demon/demon_Up.png"), 57, 56, 6));
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/demon/demon_Down.png"), 49, 55, 6));
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/demon/demon_Left.png"), 59, 56, 6));
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/demon/demon_Right.png"), 59, 56, 6));
        tempAnimationsBomber.add(Helper.TakeAnimation(new Texture("monsters/demon/demon_Death.png"), 52, 54, 2.5));

        animations.put("demon", tempAnimationsBomber);
        photoMosters.put("demon", new Texture("monsters/demon/demon_Idle.png"));
        for (int i = 1; i<6; i++) {
            manager.load("monsters/demon/finishSound"+i+".mp3", Sound.class);
        }
        manager.load("monsters/demon/death.mp3", Sound.class);
    }

    private static void UploadGryph()
    {
        //грифон
        Array<Animation> tempAnimationsGryph = new Array<Animation>();
        tempAnimationsGryph.add(null);
        tempAnimationsGryph.add(null);
        tempAnimationsGryph.add(Helper.TakeAnimation(new Texture("monsters/gryphon rider/gryphon_rider_Down.png"), 80, 80, 3));
        tempAnimationsGryph.add(null);
        tempAnimationsGryph.add(null);
        tempAnimationsGryph.add(null);

        animations.put("gryph", tempAnimationsGryph);
    }

    public static Sound GetDeathSound(String monsterType)
    {
        return manager.get("monsters/"+monsterType+"/death.mp3", Sound.class);
    }
    public static BitmapFont GetFont()
    {
        return manager.<BitmapFont>get("fonts/pix.fnt");
    }

    public static Array GetAnimation(String nameAnimation)
    {
        return animations.get(nameAnimation);
    }

    public static Texture GetFon(int fonNum)
    {
        return manager.get("fon"+fonNum+".png", Texture.class);
    }

    public static Texture GetLevelFon(int levelNumber)
    {
        return manager.get("levelsMaps/level"+levelNumber+".png", Texture.class);
    }
    public static Array<int[]>  GetLevelOneWay(int levelNum){
        return new Array<int[]>(levelsWays.get(levelNum));
    }
    public static Sprite GetPauseBg()
    {
        return pauseBg;
    }

    public static Sound GetMonsterFinishSound(String monsterName, int numSound)
    {
        return manager.get("monsters/"+monsterName+"/finishSound"+numSound+".mp3", Sound.class);
    }

    public static Music GetFonMusic(int musicNum)
    {
        return manager.get("sounds/levelMusic"+musicNum+".mp3", Music.class);
    }
    public static Preferences GetPreferences()
    {
        return preferences;
    }

    public static TextureRegion GetBulletTexture(String type)
    {
        return new TextureRegion(manager.get("towers/bullets/"+type+".png", Texture.class));
    }

    public static Array<Texture> GetArrowIcons()
    {
        return arrowIcons;
    }

    public static Texture GetBoomTexture()
    {
        return manager.<Texture>get("towers/secondTower/boomEffect.png");
    }
    public static Texture GetVillageTexture()
    {
        return manager.<Texture>get("village.png");
    }

    public static Sound GetFinishSound(boolean type)
    {
        if (type)
            return manager.get("sounds/winSound.mp3", Sound.class);
        else
            return manager.get("sounds/loseSound.mp3", Sound.class);
    }

    public static Texture GetTexture(String texture)
    {
        return manager.get(texture, Texture.class);
    }
    public static Texture GetPhotoMonster(String name)
    {
        return photoMosters.get(name);
    }
}