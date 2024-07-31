package com.rillirsimmy.bleach_and_gambling_pixel_game.states;

import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.DeleteTime;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.LoadSavesGame;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.ReturnX;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.ReturnY;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.SaveGame;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.ScaleX;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.ScaleY;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.rillirsimmy.bleach_and_gambling_pixel_game.Const;
import com.rillirsimmy.bleach_and_gambling_pixel_game.Main;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.GameStateManager;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.State;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.StateType;
import com.rillirsimmy.bleach_and_gambling_pixel_game.obj.GameSettings;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.Player;

public class MenuState extends State {

    private Texture background;
    private Texture btnPlayTexture;
    private Rectangle btnPlay;
    private Texture btnExitTexture;
    private Rectangle btnExit;
    public static Music music;
    private Texture menuPopupTexture;
    private Texture btnSettingsTexture;
    private Rectangle btnSettings;

    private BitmapFont hbmFont;
    private Texture btnMusicOnTexture;
    private Texture btnMusicOfTexture;
    private Rectangle btnMusicOn_Of;
    private Texture btnSmallTexture;
    private Texture btnNormalTexture;
    private Texture btnBigTexture;
    private Rectangle btnChangeInterfaceIndentation;
    private Texture btnOkTexture;
    private Rectangle btnOk;

    private Texture btnContinueTexture;
    private Rectangle btnContinue;
    private Texture btnNewGameTexture;
    private Rectangle btnNewGame;

    private boolean isOpenSettings = false;
    private boolean isOpenGameSelection = false;
    private boolean isLoadSavesGame = false;

    private GameSettings gameSettings = new GameSettings();

    public MenuState(final GameStateManager gsm) {
        super(gsm);
        background = new Texture(Gdx.files.internal("img/bg.jpg"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music/menuMusic.mp3"));
        music.setLooping(true);

        btnPlayTexture = new Texture(Gdx.files.internal("img/btn_play.png"));
        btnPlay = new Rectangle((Const.R_WIDTH / 2) - (ScaleX(btnPlayTexture.getWidth()) / 2),Const.R_HEIGHT / 4, ScaleX(btnPlayTexture.getWidth()), ScaleY(btnPlayTexture.getHeight()));

        btnExitTexture = new Texture(Gdx.files.internal("img/btn_exit.png"));
        btnExit = new Rectangle((Const.R_WIDTH / 2) - (ScaleX(btnExitTexture.getWidth()) / 2),Const.R_HEIGHT / 2, ScaleX(btnExitTexture.getWidth()), ScaleY(btnExitTexture.getHeight()));

        menuPopupTexture = new Texture(Gdx.files.internal("img/menuPopupTexture.png"));

        btnSettingsTexture = new Texture(Gdx.files.internal("img/btnSettings.png"));
        btnSettings = new Rectangle((float) (Const.R_WIDTH - (ScaleX(btnSettingsTexture.getWidth()) / 2.5 - ScaleX(Const.interfaceIndentation))),(float) (Const.R_HEIGHT - (ScaleY(btnSettingsTexture.getHeight()) / 2.5 - ScaleY(Const.interfaceIndentation))),(float) (ScaleX(btnSettingsTexture.getWidth()) / 2.5),(float) (ScaleY(btnSettingsTexture.getHeight()) / 2.5));

        hbmFont = new BitmapFont(Gdx.files.internal("fonts/hbm.fnt"), Gdx.files.internal("fonts/hbm.png"), false);
        btnMusicOnTexture = new Texture(Gdx.files.internal("img/volume_on.png"));
        btnMusicOfTexture = new Texture(Gdx.files.internal("img/volume_of.png"));
        btnMusicOn_Of = new Rectangle((Const.R_WIDTH / 2) - ((ScaleX(btnMusicOnTexture.getWidth()) / 3) / 2),Const.R_HEIGHT / 8, ScaleX(btnMusicOnTexture.getWidth()) / 3, ScaleY(btnMusicOnTexture.getHeight()) / 3);

        btnSmallTexture = new Texture(Gdx.files.internal("img/btnSmall.png"));
        btnNormalTexture = new Texture(Gdx.files.internal("img/btnNormal.png"));
        btnBigTexture = new Texture(Gdx.files.internal("img/btnBig.png"));
        btnChangeInterfaceIndentation = new Rectangle((Const.R_WIDTH / 2) - (ScaleX(btnSmallTexture.getWidth()) / 2), (float) (Const.R_HEIGHT / 2.7), ScaleX(btnSmallTexture.getWidth()), ScaleY(btnSmallTexture.getHeight()));

        btnOkTexture = new Texture(Gdx.files.internal("img/btnOk.png"));
        btnOk = new Rectangle((Const.R_WIDTH / 2) - (ScaleX(btnOkTexture.getWidth()) / 2), (float) (Const.R_HEIGHT / 1.4), ScaleX(btnOkTexture.getWidth()), ScaleY(btnOkTexture.getHeight()));

        btnContinueTexture = new Texture(Gdx.files.internal("img/btnContinue.png"));
        btnContinue = new Rectangle((Const.R_WIDTH / 2) - (ScaleX(btnContinueTexture.getWidth()) / 2),Const.R_HEIGHT / 4, ScaleX(btnContinueTexture.getWidth()), ScaleY(btnContinueTexture.getHeight()));
        btnNewGameTexture = new Texture(Gdx.files.internal("img/btnNewGame.png"));
        btnNewGame = new Rectangle((Const.R_WIDTH / 2) - (ScaleX(btnNewGameTexture.getWidth()) / 2),Const.R_HEIGHT / 2, ScaleX(btnNewGameTexture.getWidth()), ScaleY(btnNewGameTexture.getHeight()));

        if(Const.isMusicOn) {
            music.play();
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            if(isOpenSettings) {
                if (btnMusicOn_Of.contains(mouse.x, mouse.y)) {
                    if (Const.isMusicOn) {
                        Const.isMusicOn = false;
                    } else {
                        Const.isMusicOn = true;
                    }
                }
                if (btnChangeInterfaceIndentation.contains(mouse.x, mouse.y)) {
                    switch (Const.interfaceIndentation) {
                        case 5:
                            Const.interfaceIndentation = 15;
                            break;
                        case 15:
                            Const.interfaceIndentation = 25;
                            break;
                        case 25:
                            Const.interfaceIndentation = 5;
                            break;
                    }
                }
                if (btnOk.contains(mouse.x, mouse.y)) {
                    isOpenSettings = false;
                    gameSettings.setInterfaceIndentation(Const.interfaceIndentation);
                    gameSettings.setMusicOn(Const.isMusicOn);
                    Helper.SaveSettings(gameSettings);
                }
                return;
            }
            if(isOpenGameSelection){
                if (btnContinue.contains(mouse.x, mouse.y)) {
                    isOpenGameSelection = false;
                    Level.countTheDeadMonsters = Helper.saves.getProgress();
                    gsm.set(Helper.saves.getLevelType());
                    Player.HP = Helper.saves.getHp();
                    Player.MANA = Helper.saves.getMana();
                    music.pause();
                }
                if (btnNewGame.contains(mouse.x, mouse.y)) {
                    isOpenGameSelection = false;
                    SaveGame(StateType.MENU, 0);
                    Level.countTheDeadMonsters = 0;
                    gsm.set(StateType.LEVEL_1);
                    music.pause();
                    DeleteTime();
                }
                if (btnOk.contains(mouse.x, mouse.y)) {
                    isOpenGameSelection = false;
                }
                return;
            }
            if(btnPlay.contains(mouse.x, mouse.y)){
                isLoadSavesGame = LoadSavesGame();
                if(isLoadSavesGame){
                    isOpenGameSelection = true;
                }else{
                    Level.countTheDeadMonsters = 0;
                    DeleteTime();
                    gsm.set(StateType.LEVEL_1);
                    music.pause();
                }
            }
            if(btnSettings.contains(mouse.x, mouse.y)){
                isOpenSettings = true;
            }
            if(btnExit.contains(mouse.x, mouse.y)){
                Main.StopCommandThread();
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        if(!Const.isMusicOn && music.isPlaying()){
            music.pause();
        }else if(Const.isMusicOn && !music.isPlaying()){
            music.play();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Const.WIDTH, Const.HEIGHT);
        sb.draw(btnPlayTexture, ReturnX(btnPlay.x),Const.HEIGHT - ReturnY(btnPlay.y) - ReturnY(btnPlay.height));
        sb.draw(btnExitTexture, ReturnX(btnExit.x),Const.HEIGHT - ReturnY(btnExit.y) - ReturnY(btnExit.height));
        sb.draw(btnSettingsTexture, ReturnX(btnSettings.x),Const.HEIGHT - ReturnY(btnSettings.y) - ReturnY(btnSettings.height), ReturnX(btnSettings.width), ReturnY(btnSettings.height));

        if(isOpenSettings || isOpenGameSelection){
            sb.draw(menuPopupTexture,0,0, Const.WIDTH, Const.HEIGHT);
            sb.draw(btnOkTexture, ReturnX(btnOk.x),Const.HEIGHT - ReturnY(btnOk.y) - ReturnY(btnOk.height));
        }
        if(isOpenSettings){
            hbmFont.draw(sb, "Turn the music on or off", ReturnX(btnPlay.x), Const.HEIGHT - ReturnY(Const.R_HEIGHT / 9));
            if(Const.isMusicOn){
                sb.draw(btnMusicOnTexture, ReturnX(btnMusicOn_Of.x),Const.HEIGHT - ReturnY(btnMusicOn_Of.y) - ReturnY(btnMusicOn_Of.height), ReturnX(btnMusicOn_Of.width), ReturnY(btnMusicOn_Of.height));
            }else{
                sb.draw(btnMusicOfTexture, ReturnX(btnMusicOn_Of.x),Const.HEIGHT - ReturnY(btnMusicOn_Of.y) - ReturnY(btnMusicOn_Of.height), ReturnX(btnMusicOn_Of.width), ReturnY(btnMusicOn_Of.height));
            }
            hbmFont.draw(sb, "Interface Indentation", ReturnX(btnPlay.x), Const.HEIGHT - ReturnY(Const.R_HEIGHT / 3));
            switch(Const.interfaceIndentation){
                case 5:
                    sb.draw(btnSmallTexture, ReturnX(btnChangeInterfaceIndentation.x),Const.HEIGHT - ReturnY(btnChangeInterfaceIndentation.y) - ReturnY(btnChangeInterfaceIndentation.height));
                    break;
                case 15:
                    sb.draw(btnNormalTexture, ReturnX(btnChangeInterfaceIndentation.x),Const.HEIGHT - ReturnY(btnChangeInterfaceIndentation.y) - ReturnY(btnChangeInterfaceIndentation.height));
                    break;
                case 25:
                    sb.draw(btnBigTexture, ReturnX(btnChangeInterfaceIndentation.x),Const.HEIGHT - ReturnY(btnChangeInterfaceIndentation.y) - ReturnY(btnChangeInterfaceIndentation.height));
                    break;
            }
        }
        if(isOpenGameSelection){
            sb.draw(btnContinueTexture, ReturnX(btnContinue.x),Const.HEIGHT - ReturnY(btnContinue.y) - ReturnY(btnContinue.height), ReturnX(btnContinue.width), ReturnY(btnContinue.height));
            sb.draw(btnNewGameTexture, ReturnX(btnNewGame.x),Const.HEIGHT - ReturnY(btnNewGame.y) - ReturnY(btnNewGame.height), ReturnX(btnNewGame.width), ReturnY(btnNewGame.height));
        }
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        btnPlayTexture.dispose();
        btnExitTexture.dispose();
        btnSettingsTexture.dispose();
        music.dispose();
        menuPopupTexture.dispose();
        hbmFont.dispose();
        btnMusicOnTexture.dispose();
        btnMusicOfTexture.dispose();
        btnSmallTexture.dispose();
        btnNormalTexture.dispose();
        btnBigTexture.dispose();
        btnOkTexture.dispose();
        btnContinueTexture.dispose();
        btnNewGameTexture.dispose();
    }

    @Override
    public void resize(int width, int height) {
        btnPlay.set((Const.R_WIDTH / 2) - (ScaleX(btnPlayTexture.getWidth()) / 2), Const.R_HEIGHT / 4, ScaleX(btnPlayTexture.getWidth()), ScaleY(btnPlayTexture.getHeight()));
        btnExit.set((Const.R_WIDTH / 2) - (ScaleX(btnExitTexture.getWidth()) / 2), Const.R_HEIGHT / 2, ScaleX(btnExitTexture.getWidth()), ScaleY(btnExitTexture.getHeight()));
        btnSettings.set((float) (Const.R_WIDTH - (ScaleX(btnSettingsTexture.getWidth()) / 2.5 - ScaleX(Const.interfaceIndentation))),(float) (Const.R_HEIGHT - (ScaleY(btnSettingsTexture.getHeight()) / 2.5 - ScaleY(Const.interfaceIndentation))),(float) (ScaleX(btnSettingsTexture.getWidth()) / 2.5),(float) (ScaleY(btnSettingsTexture.getHeight()) / 2.5));
        btnMusicOn_Of.set((Const.R_WIDTH / 2) - ((ScaleX(btnMusicOnTexture.getWidth()) / 3) / 2),Const.R_HEIGHT / 8, ScaleX(btnMusicOnTexture.getWidth()) / 3, ScaleY(btnMusicOnTexture.getHeight()) / 3);
        btnChangeInterfaceIndentation.set((Const.R_WIDTH / 2) - (ScaleX(btnSmallTexture.getWidth()) / 2), (float) (Const.R_HEIGHT / 2.7), ScaleX(btnSmallTexture.getWidth()), ScaleY(btnSmallTexture.getHeight()));
        btnOk.set((Const.R_WIDTH / 2) - (ScaleX(btnOkTexture.getWidth()) / 2), (float) (Const.R_HEIGHT / 1.4), ScaleX(btnOkTexture.getWidth()), ScaleY(btnOkTexture.getHeight()));
        btnContinue.set((Const.R_WIDTH / 2) - (ScaleX(btnContinueTexture.getWidth()) / 2),Const.R_HEIGHT / 4, ScaleX(btnContinueTexture.getWidth()), ScaleY(btnContinueTexture.getHeight()));
        btnNewGame.set((Const.R_WIDTH / 2) - (ScaleX(btnNewGameTexture.getWidth()) / 2),Const.R_HEIGHT / 2, ScaleX(btnNewGameTexture.getWidth()), ScaleY(btnNewGameTexture.getHeight()));
    }
}
