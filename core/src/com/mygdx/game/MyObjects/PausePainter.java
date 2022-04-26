package com.mygdx.game.MyObjects;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.MyObjects.Units.Monsters.Monster;

public class PausePainter {
    public static Button backBut = Loader.CreateButton(250, 80, 300, 100, "Главное меню");
    public static Monster monsterLose =
            new Monster(350, 200, Loader.GetAnimation("grant"), Loader.game);
    public static Monster monsterWin =
            new Monster(350, 200, Loader.GetAnimation("gryph"), Loader.game);
    public static String type = "None";
    private static boolean finishSoundPlay = false;
    private static Button otherVolumeButtonMinus = Loader.CreateButton(420, 235, 30, 30, "<");
    private static Button otherVolumeButtonPlus = Loader.CreateButton(510, 235, 30, 30, ">");
    private static Button volumeMinusButton = Loader.CreateButton(420, 295, 30, 30, "<");
    private static Button volumePlusButton = Loader.CreateButton(510, 295, 30, 30, ">");

    public static void DrawPause(boolean loseState)
    {
        if (!loseState && type.equals("Win"))
        {
            backBut.SetLocation(250, 70);
            Loader.GetPauseBg().draw(MyGdxGame.batch);
            Loader.GetFont().getData().setScale(1, 1);
            Loader.GetFont().draw(MyGdxGame.batch, "НА СЕЙ РАЗ, КУРСАЧ ОСТАЛСЯ У ВАС \n               :}", 50, 420);
            if (!finishSoundPlay)
            {
                Loader.GetFinishSound(true).play((float) (Loader.otherVolume/10.0));
                finishSoundPlay = true;
            }
            backBut.Draw(1, 1);
            monsterWin.Draw(100, 100);
        }
        else if (!loseState) {
            Loader.GetPauseBg().draw(MyGdxGame.batch);
            Loader.GetFont().getData().setScale(2, 2);
            Loader.GetFont().draw(MyGdxGame.batch, "ПАУЗА", 290, 470);
            Loader.GetFont().getData().setScale(0.7f, 0.7f);
            Loader.GetFont().draw(MyGdxGame.batch, "Громкость музыки", 150, 320);
            Loader.GetFont().draw(MyGdxGame.batch, "Прочие звуки", 150, 260);
            Loader.GetFont().draw(MyGdxGame.batch, (Loader.GetPreferences().getInteger("FonMusicVolume")*10)+"%", 455, 320);
            Loader.GetFont().draw(MyGdxGame.batch, (Loader.GetPreferences().getInteger("OtherVolume")*10)+"%", 455, 260);
            volumeMinusButton.Draw(0.5f, 0.5f);
            volumePlusButton.Draw(0.5f, 0.5f);
            otherVolumeButtonMinus.Draw(0.5f, 0.5f);
            otherVolumeButtonPlus.Draw(0.5f, 0.5f);
            backBut.Draw(1, 1);
        }
        else
        {
            backBut.SetLocation(250, 70);
            Loader.GetPauseBg().draw(MyGdxGame.batch);
            Loader.GetFont().getData().setScale(1, 1);
            Loader.GetFont().draw(MyGdxGame.batch, "ОРКИ СПЁРЛИ ВАШ КУРСАЧ \n          :{", 170, 420);
            if (!finishSoundPlay)
            {
                Loader.GetFinishSound(false).play((float) (Loader.otherVolume/10.0));
                finishSoundPlay = true;
            }
            backBut.Draw(1, 1);
            monsterLose.Draw(100, 100);
        }


        if (volumeMinusButton.IsClicked())
        {
            if (Loader.GetPreferences().getInteger("FonMusicVolume") > 0)
            {
                Loader.GetPreferences().putInteger("FonMusicVolume", Loader.GetPreferences().getInteger("FonMusicVolume")-1);
                for (int i = 0; i<2; i++)
                    Loader.GetFonMusic(i).setVolume((float)(Loader.GetPreferences().getInteger("FonMusicVolume")/10.0));
                Loader.GetPreferences().flush();
            }

        }

        if (volumePlusButton.IsClicked())
        {
            if (Loader.GetPreferences().getInteger("FonMusicVolume") < 10)
            {
                Loader.GetPreferences().putInteger("FonMusicVolume", Loader.GetPreferences().getInteger("FonMusicVolume")+1);
                for (int i = 0; i<2; i++)
                    Loader.GetFonMusic(i).setVolume((float)(Loader.GetPreferences().getInteger("FonMusicVolume")/10.0));
                Loader.GetPreferences().flush();
            }

        }

        if (otherVolumeButtonPlus.IsClicked())
        {
            if (Loader.GetPreferences().getInteger("OtherVolume") < 10)
            {
                Loader.GetPreferences().putInteger("OtherVolume", Loader.GetPreferences().getInteger("OtherVolume")+1);
                Loader.otherVolume++;
                Loader.GetPreferences().flush();
            }
        }

        if (otherVolumeButtonMinus.IsClicked())
        {
            if (Loader.GetPreferences().getInteger("OtherVolume") > 0)
            {
                Loader.GetPreferences().putInteger("OtherVolume", Loader.GetPreferences().getInteger("OtherVolume")-1);
                Loader.otherVolume--;
                Loader.GetPreferences().flush();
            }
        }

        if (backBut.IsClicked())
            finishSoundPlay = false;
    }
}
