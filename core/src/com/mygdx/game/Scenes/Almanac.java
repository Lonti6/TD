package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.MyObjects.Button;
import com.mygdx.game.MyObjects.InformWindow;
import com.mygdx.game.MyObjects.Loader;
import com.mygdx.game.MyObjects.Units.Towers.FrostTower;

public class Almanac implements Screen {
    MyGdxGame game;
    InputHelper inputHelper;
    Button backButton;
    Array<InformWindow> windows;
    public static boolean isOpen = false;
    private static int[] ordinations = {140, 350, 290, 350, 440, 350, 590, 350,
                                        140, 20, 290, 20, 440, 20,
                                        140, -330, 290, -330, 440, -330, 590, -330};
    private static String[] monsters = {"peon", "grant", "troll", "ogre", "bomber", "demon", "dragon",
                            "spearTower","boomTower","frostTower", "multiTower" };
    private static String[] names = {"Раб", "Бугай", "Тролль", "Огр", "Подрывная бригада", "Демон", "Дракон",
                            "Сторожевая башня", "Осадная башня", "Морозная башня", "Мульти-башня"};
    private static String[] descriptions = {"Рабочая сила орды. В повседневной жизни они добывают ресурсы и чинят здания, но сегодня они пришли за курсачём, чтобы избавиться от работы",
                            "Не особо умный, но бесстрашный орк-воин. В отличии от раба имеет доспехи и топор, благодаря чему, он может отлынивать от работы",
                            "Ловкий метатель топоров и собиратель голов. У него закончилась бумага, на которую он записывает имена своих жертв, из-за чего решил заполучить бумагу курсача",
                            "Огромный и толстый гигант, который посовещявшись со своей второй головой решил ПОЗАБАВИТЬСЯ",
                            "Гоблинская подрывная бригада, состоящая из безумных гоблинов, которые готовы пойти на всё",
                            "Грозный демонический воин, один вид которого, может повергнуть в ужас. Только бес знает зачем он явился из земных недр",
                            "Не известно как его смогли приручить орки, но известен факт, что после этого пропала целая деревня рабов",

                            "Атакует ближащего к себе противника одиночной стрелой",
                            "Раз в 4 секунды стреляет в определённую сторону снарядом, который моментально убивает всех врагов в радусе поражения снаряда",
                            "Башня, дизайн которой спёрли у известного чуда света под названием \"Зиккурат\", замараживает всех противников, снижая их скорость на "+((int)((1.0-FrostTower.modSpeed)*100))+"%",
                            "Выстреливается тремя магическими стрелами, каждая из которых летит в случайного противника"
};
    public Almanac(){
        this.game = Loader.game;
        backButton = Loader.CreateButton(10, 10, 50, 50, "<");
        inputHelper = new InputHelper();
        Gdx.input.setInputProcessor(inputHelper);
        windows = new Array<>();
        AlmanacSpawn();
        isOpen = true;
    }

    @Override
    public void render(float v) {
        for (InformWindow window: windows)
            window.Update();
        ScreenUtils.clear(0, 0, 0f, 1);
        game.batch.begin();
        game.batch.draw(Loader.GetFon(3), 0, 0);
        backButton.Draw(1.2f, 1.2f);
        for (InformWindow window: windows)
            window.Draw();
        game.batch.draw(Loader.GetTexture("butG.png"), 125, 395, 600, 70);
        Loader.GetFont().getData().setScale(1.0f, 1.0f);
        Loader.GetFont().draw(game.batch, "Альманах монстров и башен", 150, 450);
        game.batch.end();



        if (backButton.IsClicked())
        {
            isOpen = false;
            game.setScreen(new LevelsScreen());
            dispose();
        }
    }

    private void AlmanacSpawn()
    {
        int j = 0;
        for (int i = 1; i<ordinations.length; i+=2, j++)
        {
            Array<Sound> temp = new Array<>();
            try {
                for (int s = 1; s<6; s++)
                    temp.add(Loader.GetMonsterFinishSound(monsters[j], s));
            }
            catch (Exception e){System.out.println(monsters[j] + " Такого звука не существует");}

            windows.add(new InformWindow(Loader.GetPhotoMonster(monsters[j]), names[j], descriptions[j], ordinations[i-1], ordinations[i], temp));
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        System.gc();
    }

    private class InputHelper implements InputProcessor
    {
        @Override
        public boolean keyDown(int i) {
            return false;
        }

        @Override
        public boolean keyUp(int i) {
            return false;
        }

        @Override
        public boolean keyTyped(char c) {
            return false;
        }

        @Override
        public boolean touchDown(int i, int i1, int i2, int i3) {
            return false;
        }

        @Override
        public boolean touchUp(int i, int i1, int i2, int i3) {
            return false;
        }

        @Override
        public boolean touchDragged(int i, int i1, int i2) {
            return false;
        }

        @Override
        public boolean mouseMoved(int i, int i1) {
            return false;
        }

        @Override
        public boolean scrolled(float v, float v1) {
            if (isOpen) {
                if (windows.get(windows.size - 1).GetY() >= 350) {
                    int num = windows.get(windows.size - 1).GetY() - 349;
                    for (InformWindow window : windows)
                        window.SetY((int) (window.GetY() - num));
                }
                if (windows.get(0).GetY() <= 350) {
                    int num = windows.get(0).GetY() - 349;
                    for (InformWindow window : windows)
                        window.SetY((int) (window.GetY() - num));
                }
                for (InformWindow window : windows)
                    window.SetY((int) (window.GetY() + (24 * v1)));
            }
            return false;
        }
    }
}
