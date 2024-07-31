package com.rillirsimmy.bleach_and_gambling_pixel_game.states;

import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.DeleteTime;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.ReturnX;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.ReturnY;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.SaveGame;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.ScaleX;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.ScaleY;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.rillirsimmy.bleach_and_gambling_pixel_game.Const;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.GameStateManager;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.State;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.StateType;

public class GameOverState extends State {

    private Texture gameOverTexture;
    private Music music;

    private Texture btnPlayTexture;
    private Rectangle btnPlay;

    private Texture btnExitTexture;
    private Rectangle btnExit;

    public GameOverState(GameStateManager gsm) {
        super(gsm);
        gameOverTexture = new Texture(Gdx.files.internal("img/gameOverTexture.png"));

        btnPlayTexture = new Texture(Gdx.files.internal("img/btn_play.png"));
        btnPlay = new Rectangle((Const.R_WIDTH / 2) - (ScaleX(btnPlayTexture.getWidth()) / 2),Const.R_HEIGHT / 4, ScaleX(btnPlayTexture.getWidth()), ScaleY(btnPlayTexture.getHeight()));

        btnExitTexture = new Texture(Gdx.files.internal("img/btn_exit.png"));
        btnExit = new Rectangle((Const.R_WIDTH / 2) - (ScaleX(btnExitTexture.getWidth()) / 2),Const.R_HEIGHT / 2, ScaleX(btnExitTexture.getWidth()), ScaleY(btnExitTexture.getHeight()));

        music = Gdx.audio.newMusic(Gdx.files.internal("music/gameOver.mp3"));
        music.setLooping(false);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            if(btnPlay.contains(mouse.x, mouse.y)){
                DeleteTime();
                SaveGame(StateType.MENU, 0);
                Level.countTheDeadMonsters = 0;
                gsm.set(StateType.LEVEL_1);
            }
            if(btnExit.contains(mouse.x, mouse.y)){
                DeleteTime();
                SaveGame(StateType.MENU, 0);
                Level.countTheDeadMonsters = 0;
                gsm.set(StateType.MENU);
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        if(Const.isMusicOn && !music.isPlaying()) {
            music.play();
        }
        if(music.isPlaying() && !(gsm.states.peek() instanceof GameOverState)){
            music.stop();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(225, 0, 0, 0.5F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        sb.draw(gameOverTexture, 0, 0, Const.WIDTH, Const.HEIGHT);
        sb.draw(btnPlayTexture, ReturnX(btnPlay.x),Const.HEIGHT - ReturnY(btnPlay.y) - ReturnY(btnPlay.height));
        sb.draw(btnExitTexture, ReturnX(btnExit.x),Const.HEIGHT - ReturnY(btnExit.y) - ReturnY(btnExit.height));
        sb.end();
    }

    @Override
    public void dispose() {
        gameOverTexture.dispose();
        btnPlayTexture.dispose();
        btnExitTexture.dispose();
        music.dispose();
    }

    @Override
    public void resize(int width, int height) {
        btnPlay.set((Const.R_WIDTH / 2) - (ScaleX(btnPlayTexture.getWidth()) / 2), Const.R_HEIGHT / 4, ScaleX(btnPlayTexture.getWidth()), ScaleY(btnPlayTexture.getHeight()));
        btnExit.set((Const.R_WIDTH / 2) - (ScaleX(btnExitTexture.getWidth()) / 2), Const.R_HEIGHT / 2, ScaleX(btnExitTexture.getWidth()), ScaleY(btnExitTexture.getHeight()));
    }

}
