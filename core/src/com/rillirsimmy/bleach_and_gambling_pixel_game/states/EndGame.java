package com.rillirsimmy.bleach_and_gambling_pixel_game.states;

import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.AnimDispose;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.DeleteTime;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.LoadTime;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.ReturnX;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.ReturnY;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.SaveGame;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.ScaleX;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.ScaleY;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import com.rillirsimmy.bleach_and_gambling_pixel_game.Const;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.GameStateManager;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.GifDecoder;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.State;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.StateType;

public class EndGame extends State {

    private Texture background;
    private Animation<TextureRegion> backgroundFrond;

    private Texture btnMenuTexture;
    private Rectangle btnMenu;

    private Texture strawberries;
    private Texture pers2;

    private BitmapFont authorFont;

    private float elapsed;

    public EndGame(GameStateManager gsm) {
        super(gsm);
        background = new Texture(Gdx.files.internal("img/bg.jpg"));
        backgroundFrond = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/bgFrond.gif").read());

        authorFont = new BitmapFont(Gdx.files.internal("fonts/af.fnt"), Gdx.files.internal("fonts/af.png"), false);

        btnMenuTexture = new Texture(Gdx.files.internal("img/btnMenu.png"));
        btnMenu = new Rectangle(ScaleX(5), ScaleY(5), ScaleX(btnMenuTexture.getWidth()) / 3 * 2,ScaleY(btnMenuTexture.getHeight()) / 3 * 2);

        strawberries = new Texture(Gdx.files.internal("img/strawberries.png"));
        pers2 = new Texture(Gdx.files.internal("img/pers2.png"));
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            if (btnMenu.contains(mouse.x, mouse.y)) {
                try {
                    Level.music.stop();
                }catch(Exception e){}
                SaveGame(StateType.MENU, 0);
                Level.countTheDeadMonsters = 0;
                DeleteTime();
                gsm.set(StateType.MENU);
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        elapsed += Gdx.graphics.getDeltaTime();
        sb.draw(background, 0, 0, Const.WIDTH, Const.HEIGHT);
        sb.draw(backgroundFrond.getKeyFrame(elapsed), 0, 0, Const.WIDTH, Const.HEIGHT);
        sb.draw(btnMenuTexture,ReturnX(btnMenu.x),Const.HEIGHT - ReturnY(btnMenu.y) - ReturnY(btnMenu.height), ReturnX(btnMenu.width), ReturnY(btnMenu.height));
        sb.draw(strawberries, ReturnX(5), ReturnY(5));
        sb.draw(pers2, Const.WIDTH - pers2.getWidth() - ReturnX(5), ReturnY(5));
        authorFont.draw(sb, "Game Win!!!", Const.WIDTH / 2 - ReturnX(80), ReturnY(300));
        authorFont.draw(sb, "Passed in such a time:\n"+LoadTime()+" minutes and millisecond", ReturnX(5), ReturnY(200));
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        AnimDispose(backgroundFrond);
        authorFont.dispose();
        btnMenuTexture.dispose();
        strawberries.dispose();
        pers2.dispose();
    }

    @Override
    public void resize(int width, int height) {
        btnMenu.set(ScaleX(5), ScaleY(5), ScaleX(btnMenuTexture.getWidth()) / 3 * 2, ScaleY(btnMenuTexture.getHeight()) / 3 * 2);
    }
}
