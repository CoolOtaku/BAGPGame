package com.rillirsimmy.bleach_and_gambling_pixel_game.states;

import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.ReturnX;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.ReturnY;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.SaveGame;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.SaveTime;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.ScaleX;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.ScaleY;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.rillirsimmy.bleach_and_gambling_pixel_game.Const;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.State;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.StateType;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.Player;

public class NavigationState extends State {

    private Texture btnMenuTexture;
    private Rectangle btnMenu;
    private Texture btnGoToRightTexture;
    private Rectangle btnGoToRight;
    private Texture btnJumpTexture;
    private Rectangle btnJump;
    private Texture btnGoToLeftTexture;
    private Rectangle btnGoToLeft;
    private Texture btnAttackTexture;
    private Rectangle btnAttack;
    private Texture btnTreatTexture;
    private Rectangle btnTreat;

    private Pixmap healthBarBG;
    private Texture healthBar;
    private Texture heartTexture;
    private BitmapFont healthBarFont;

    private Pixmap manaBarBG;
    private Texture manaBar;
    private Texture manaTexture;
    private BitmapFont manaBarFont;

    private Texture menuPopupTexture;

    private Texture btnPlayTexture;
    private Rectangle btnPlay;

    private Texture btnExitTexture;
    private Rectangle btnExit;

    public NavigationState(){
        super(Level.gsm);
        btnMenuTexture = new Texture(Gdx.files.internal("img/btnMenu.png"));
        btnMenu = new Rectangle(ScaleX(5), ScaleY(5), ScaleX(btnMenuTexture.getWidth()) / 3 * 2,ScaleY(btnMenuTexture.getHeight()) / 3 * 2);
        if(Gdx.app.getType() != Application.ApplicationType.Desktop) {
            btnGoToRightTexture = new Texture(Gdx.files.internal("img/btnGoToRight.png"));
            btnGoToRight = new Rectangle((float) (Const.R_WIDTH - ScaleX(btnGoToRightTexture.getWidth()) * 1.3 - ScaleX(Const.interfaceIndentation)), (float) (Const.R_HEIGHT - ScaleY(btnGoToRightTexture.getHeight()) * 1.3 - ScaleY(Const.interfaceIndentation)), (float) (ScaleX(btnGoToRightTexture.getWidth()) * 1.3), (float) (ScaleY(btnGoToRightTexture.getHeight()) * 1.3));
            btnJumpTexture = new Texture(Gdx.files.internal("img/btnJump.png"));
            btnJump = new Rectangle((float) (Const.R_WIDTH - ScaleX(btnGoToRightTexture.getWidth()) * 1.3 - ScaleX(btnJumpTexture.getWidth()) * 1.3 - ScaleX(Const.interfaceIndentation)), (float) (Const.R_HEIGHT - ScaleY(btnJumpTexture.getHeight()) * 1.3 - ScaleY(Const.interfaceIndentation * 3)), (float) (ScaleX(btnJumpTexture.getWidth()) * 1.3), (float) (ScaleY(btnJumpTexture.getHeight()) * 1.3));
            btnGoToLeftTexture = new Texture(Gdx.files.internal("img/btnGoToLeft.png"));
            btnGoToLeft = new Rectangle((float) (Const.R_WIDTH - ScaleX(btnGoToRightTexture.getWidth()) * 1.3 - ScaleX(btnGoToLeftTexture.getWidth()) * 1.3 - ScaleX(btnJumpTexture.getWidth()) * 1.3 - ScaleX(Const.interfaceIndentation)), (float) (Const.R_HEIGHT - ScaleY(btnGoToLeftTexture.getHeight()) * 1.3 - ScaleY(Const.interfaceIndentation)), (float) (ScaleX(btnGoToLeftTexture.getWidth()) * 1.3), (float) (ScaleY(btnGoToLeftTexture.getHeight()) * 1.3));
            btnAttackTexture = new Texture(Gdx.files.internal("img/btnAttack.png"));
            btnAttack = new Rectangle(ScaleX(Const.interfaceIndentation), (float) (Const.R_HEIGHT - ScaleY(btnAttackTexture.getHeight()) / 3.6 - ScaleY(Const.interfaceIndentation)), (float) (ScaleX(btnAttackTexture.getWidth()) / 3.6), (float) (ScaleY(btnAttackTexture.getHeight()) / 3.6));
            btnTreatTexture = new Texture(Gdx.files.internal("img/btnTreat.png"));
            btnTreat = new Rectangle(ScaleX(Const.interfaceIndentation), (float) (Const.R_HEIGHT - ScaleY(btnAttackTexture.getHeight()) / 3.6 - ScaleY(btnTreatTexture.getHeight()) / 1.8 - ScaleY(Const.interfaceIndentation * 2)), (float) (ScaleX(btnTreatTexture.getWidth()) / 1.8), (float) (ScaleY(btnTreatTexture.getHeight()) / 1.8));
        }
        menuPopupTexture = new Texture(Gdx.files.internal("img/menuPopupTexture.png"));
        btnPlayTexture = new Texture(Gdx.files.internal("img/btn_play.png"));
        btnPlay = new Rectangle((Const.R_WIDTH / 2) - (ScaleX(btnPlayTexture.getWidth()) / 2),Const.R_HEIGHT / 4, ScaleX(btnPlayTexture.getWidth()), ScaleY(btnPlayTexture.getHeight()));
        btnExitTexture = new Texture(Gdx.files.internal("img/btn_exit.png"));
        btnExit = new Rectangle((Const.R_WIDTH / 2) - (ScaleX(btnExitTexture.getWidth()) / 2),Const.R_HEIGHT / 2, ScaleX(btnExitTexture.getWidth()), ScaleY(btnExitTexture.getHeight()));

        heartTexture = new Texture(Gdx.files.internal("img/heart.png"));
        healthBarBG = new Pixmap(Player.HP, 15, Pixmap.Format.RGBA8888);
        healthBarBG.setColor(Color.RED);
        healthBarBG.fill();
        healthBar = new Texture(healthBarBG);
        healthBarFont = new BitmapFont(Gdx.files.internal("fonts/hbf.fnt"), Gdx.files.internal("fonts/hbf.png"), false);

        manaTexture = new Texture(Gdx.files.internal("img/mana.png"));
        manaBarBG = new Pixmap(Player.MANA, 15, Pixmap.Format.RGBA8888);
        manaBarBG.setColor(Color.BLUE);
        manaBarBG.fill();
        manaBar = new Texture(manaBarBG);
        manaBarFont = new BitmapFont(Gdx.files.internal("fonts/mbf.fnt"), Gdx.files.internal("fonts/mbf.png"), false);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            if(Level.isPaused){
                if(btnPlay.contains(mouse.x, mouse.y)){
                    Level.isPaused = false;
                    if(Const.isMusicOn) {
                        Level.music.play();
                    }
                }
                if(btnExit.contains(mouse.x, mouse.y)){
                    SaveGame(Level.gsm.currentStateType, Level.countTheDeadMonsters);
                    SaveTime(Level.timeSeconds);
                    Level.isPaused = false;
                    gsm.set(StateType.MENU);
                }
                return;
            }
            if(Gdx.app.getType() != Application.ApplicationType.Desktop) {
                if (btnTreat.contains(mouse.x, mouse.y)) {
                    Player.Treat();
                    return;
                }
                if (btnAttack.contains(mouse.x, mouse.y)) {
                    Player.Attack(15);
                }
                if (btnGoToRight.contains(mouse.x, mouse.y)) {
                    Player.goToRight();
                } else if (btnGoToLeft.contains(mouse.x, mouse.y)) {
                    Player.goToLeft();
                }
                if (btnJump.contains(mouse.x, mouse.y)) {
                    Player.Jump();
                }
            }
            if(btnMenu.contains(mouse.x, mouse.y) || Gdx.input.isKeyPressed(Input.Keys.MENU)){
                Level.isPaused = true;
                Level.music.pause();
                return;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Level.isPaused = true;
            Level.music.pause();
            return;
        }
    }

    @Override
    public void update(float dt) {
        if(Player.HP != healthBarBG.getWidth() && Player.HP > -1){
            if(!healthBarBG.isDisposed()) {
                healthBarBG.dispose();
            }
            healthBarBG = new Pixmap(Player.HP, 15, Pixmap.Format.RGBA8888);
            healthBarBG.setColor(Color.RED);
            healthBarBG.fill();
            if(!healthBarBG.isDisposed()) {
                healthBar.dispose();
            }
            healthBar = new Texture(healthBarBG);
        }
        if(Player.MANA < 0){
            Player.MANA = 0;
        }
        if(Player.MANA != manaBarBG.getWidth() && Player.MANA > -1){
            if(!manaBarBG.isDisposed()) {
                manaBarBG.dispose();
            }
            manaBarBG = new Pixmap(Player.MANA, 15, Pixmap.Format.RGBA8888);
            manaBarBG.setColor(Color.BLUE);
            manaBarBG.fill();
            if(!manaBarBG.isDisposed()) {
                manaBar.dispose();
            }
            manaBar = new Texture(manaBarBG);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(btnMenuTexture,ReturnX(btnMenu.x),Const.HEIGHT - ReturnY(btnMenu.y) - ReturnY(btnMenu.height), ReturnX(btnMenu.width), ReturnY(btnMenu.height));
        if(Gdx.app.getType() != Application.ApplicationType.Desktop) {
            sb.draw(btnGoToRightTexture, ReturnX(btnGoToRight.x), Const.HEIGHT - ReturnY(btnGoToRight.y) - ReturnY(btnGoToRight.height), ReturnX(btnGoToRight.width), ReturnY(btnGoToRight.height));
            sb.draw(btnJumpTexture, ReturnX(btnJump.x), Const.HEIGHT - ReturnY(btnJump.y) - ReturnY(btnJump.height), ReturnX(btnJump.width), ReturnY(btnJump.height));
            sb.draw(btnGoToLeftTexture, ReturnX(btnGoToLeft.x), Const.HEIGHT - ReturnY(btnGoToLeft.y) - ReturnY(btnGoToLeft.height), ReturnX(btnGoToLeft.width), ReturnY(btnGoToLeft.height));
            sb.draw(btnAttackTexture, ReturnX(btnAttack.x), ReturnY(Const.interfaceIndentation), ReturnX(btnAttack.width), ReturnY(btnAttack.height));
            sb.draw(btnTreatTexture, ReturnX(btnTreat.x), ReturnY(btnAttack.height) + ReturnY(Const.interfaceIndentation * 2), ReturnX(btnTreat.width), ReturnY(btnTreat.height));
        }
        sb.draw(heartTexture,Const.WIDTH - 30 - 5,Const.HEIGHT - 5 - 25, 30, 25);
        sb.draw(healthBar,Const.WIDTH - healthBarBG.getWidth() - 40,Const.HEIGHT - 10 - healthBarBG.getHeight(), healthBarBG.getWidth(), healthBarBG.getHeight());
        healthBarFont.draw(sb, String.valueOf(Player.HP), Const.WIDTH - healthBarBG.getWidth() - 75, Const.HEIGHT - (healthBarBG.getHeight() /2) - 5);

        sb.draw(manaTexture,Const.WIDTH - 30 - 5,Const.HEIGHT - 30 - 30, 30, 30);
        sb.draw(manaBar,Const.WIDTH - manaBarBG.getWidth() - 40,Const.HEIGHT - 35 - manaBarBG.getHeight(), manaBarBG.getWidth(), manaBarBG.getHeight());
        manaBarFont.draw(sb, String.valueOf(Player.MANA), Const.WIDTH - manaBarBG.getWidth() - 75, Const.HEIGHT - (manaBarBG.getHeight() /2) - 30);

        if(Level.isPaused) {
            sb.draw(menuPopupTexture,0, 0, Const.WIDTH, Const.HEIGHT);
            sb.draw(btnPlayTexture, ReturnX(btnPlay.x),Const.HEIGHT - ReturnY(btnPlay.y) - ReturnY(btnPlay.height));
            sb.draw(btnExitTexture, ReturnX(btnExit.x),Const.HEIGHT - ReturnY(btnExit.y) - ReturnY(btnExit.height));
        }
    }

    @Override
    public void dispose() {
        /*btnMenuTexture.dispose();
        if(Gdx.app.getType() != Application.ApplicationType.Desktop) {
            btnGoToRightTexture.dispose();
            btnJumpTexture.dispose();
            btnGoToLeftTexture.dispose();
            btnAttackTexture.dispose();
            btnTreatTexture.dispose();
        }
        healthBarBG.dispose();
        heartTexture.dispose();
        healthBar.dispose();
        healthBarFont.dispose();
        manaBarBG.dispose();
        manaTexture.dispose();
        manaBar.dispose();
        manaBarFont.dispose();
        menuPopupTexture.dispose();
        btnPlayTexture.dispose();
        btnExitTexture.dispose();*/
    }

    @Override
    public void resize(int width, int height) {
        btnMenu.set(ScaleX(5), ScaleY(5), ScaleX(btnMenuTexture.getWidth()) / 3 * 2, ScaleY(btnMenuTexture.getHeight()) / 3 * 2);
        if(Gdx.app.getType() != Application.ApplicationType.Desktop) {
            btnGoToRight.set((float) (Const.R_WIDTH - ScaleX(btnGoToRightTexture.getWidth()) * 1.3 - ScaleX(Const.interfaceIndentation)), (float) (Const.R_HEIGHT - ScaleY(btnGoToRightTexture.getHeight()) * 1.3 - ScaleY(Const.interfaceIndentation)), (float) (ScaleX(btnGoToRightTexture.getWidth()) * 1.3), (float) (ScaleY(btnGoToRightTexture.getHeight()) * 1.3));
            btnJump.set((float) (Const.R_WIDTH - ScaleX(btnGoToRightTexture.getWidth()) * 1.3 - ScaleX(btnJumpTexture.getWidth()) * 1.3 - ScaleX(Const.interfaceIndentation)), (float) (Const.R_HEIGHT - ScaleY(btnJumpTexture.getHeight()) * 1.3 - ScaleY(Const.interfaceIndentation * 3)), (float) (ScaleX(btnJumpTexture.getWidth()) * 1.3), (float) (ScaleY(btnJumpTexture.getHeight()) * 1.3));
            btnGoToLeft.set((float) (Const.R_WIDTH - ScaleX(btnGoToRightTexture.getWidth()) * 1.3 - ScaleX(btnGoToLeftTexture.getWidth()) * 1.3 - ScaleX(btnJumpTexture.getWidth()) * 1.3 - ScaleX(Const.interfaceIndentation)), (float) (Const.R_HEIGHT - ScaleY(btnGoToLeftTexture.getHeight()) * 1.3 - ScaleY(Const.interfaceIndentation)), (float) (ScaleX(btnGoToLeftTexture.getWidth()) * 1.3), (float) (ScaleY(btnGoToLeftTexture.getHeight()) * 1.3));
            btnAttack.set(ScaleX(Const.interfaceIndentation), (float) (Const.R_HEIGHT - ScaleY(btnAttackTexture.getHeight()) / 3.6 - ScaleY(Const.interfaceIndentation)), (float) (ScaleX(btnAttackTexture.getWidth()) / 3.6), (float) (ScaleY(btnAttackTexture.getHeight()) / 3.6));
            btnTreat.set(ScaleX(Const.interfaceIndentation), (float) (Const.R_HEIGHT - ScaleY(btnAttackTexture.getHeight()) / 3.6 - ScaleY(btnTreatTexture.getHeight()) / 1.8 - ScaleY(Const.interfaceIndentation * 2)), (float) (ScaleX(btnTreatTexture.getWidth()) / 1.8), (float) (ScaleY(btnTreatTexture.getHeight()) / 1.8));
        }
        btnPlay.set((Const.R_WIDTH / 2) - (ScaleX(btnPlayTexture.getWidth()) / 2), Const.R_HEIGHT / 4, ScaleX(btnPlayTexture.getWidth()), ScaleY(btnPlayTexture.getHeight()));
        btnExit.set((Const.R_WIDTH / 2) - (ScaleX(btnExitTexture.getWidth()) / 2), Const.R_HEIGHT / 2, ScaleX(btnExitTexture.getWidth()), ScaleY(btnExitTexture.getHeight()));
    }

}
