package com.rillirsimmy.bleach_and_gambling_pixel_game.sprites;

import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.AnimDispose;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import com.rillirsimmy.bleach_and_gambling_pixel_game.core.GifDecoder;

public class Monster {

    public static float GO_TO_SPEED = (float) 0.6;
    public static float ATTACK_TIME_OUT = (float) 1;
    public static int HP = 100;
    public static int ATTACK_RADIUS = 80;
    public static int DAMAGE = 5;
    public static Vector3 position;

    int looksIn = 1;
    float elapsed;
    float attackTime = 0;
    boolean isAttacked = false;

    public static Animation<TextureRegion> texture;
    public Animation<TextureRegion> animation_to_go;
    public Animation<TextureRegion> animation_attack;
    public Animation<TextureRegion> animation_blood;

    public Animation<TextureRegion> animation_to_go_left;
    public Animation<TextureRegion> animation_to_go_right;
    public Animation<TextureRegion> animation_attack_right;
    public Animation<TextureRegion> animation_attack_left;

    private Pixmap healthBarBG;
    private Texture healthBar;
    private BitmapFont healthBarFont;

    public Sound soundSpawn;
    private Sound soundAttack;

    public Monster(int x, int y, String spawn, String attack){
        soundSpawn = Gdx.audio.newSound(Gdx.files.internal(spawn));
        soundAttack = Gdx.audio.newSound(Gdx.files.internal(attack));
        HP = 100;
        position = new Vector3(x, y, 0);

        animation_blood = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/player_blood.gif").read());

        healthBarBG = new Pixmap(HP/2, 10, Pixmap.Format.RGBA8888);
        healthBarBG.setColor(Color.RED);
        healthBarBG.fill();
        healthBar = new Texture(healthBarBG);
        healthBarFont = new BitmapFont(Gdx.files.internal("fonts/hbm.fnt"), Gdx.files.internal("fonts/hbm.png"), false);
    }
    public void reset(){}

    public void update(float dt) {
        FollowPlayer(dt);
        VerifyHP();
    }

    public void render(SpriteBatch sb) {
        elapsed += Gdx.graphics.getDeltaTime();
        sb.draw(texture.getKeyFrame(elapsed), position.x, position.y);

        sb.draw(healthBar, position.x + 25,position.y + texture.getKeyFrame(elapsed).getRegionHeight() + 5);
        healthBarFont.draw(sb, String.valueOf(HP), position.x, position.y + texture.getKeyFrame(elapsed).getRegionHeight() + 13);

        if (isAttacked){
            sb.draw(animation_blood.getKeyFrame(elapsed), Player.position.x, Player.position.y, 100, 100);
        }
    }

    public void dispose() {
        //AnimDispose(texture);
        AnimDispose(animation_to_go);
        AnimDispose(animation_attack);
        AnimDispose(animation_blood);

        AnimDispose(animation_to_go_right);
        AnimDispose(animation_to_go_left);
        AnimDispose(animation_attack_right);
        AnimDispose(animation_attack_left);

        soundSpawn.dispose();
        soundAttack.dispose();
        if(!healthBarBG.isDisposed()) {
            healthBarBG.dispose();
        }
        healthBar.dispose();
        healthBarFont.dispose();
    }

    public void goToRight(){
        position.add(GO_TO_SPEED, 0, 0);
        looksIn = 2;
        texture = animation_to_go;
    }
    public void goToLeft(){
        position.sub(GO_TO_SPEED, 0, 0);
        looksIn = 1;
        texture = animation_to_go;
    }
    public void Attack(float dt){
        texture = animation_attack;
        attackTime += dt;
        if (attackTime > ATTACK_TIME_OUT){
            soundAttack.play();
            DealDamage();
            attackTime -= ATTACK_TIME_OUT;
            isAttacked = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            isAttacked = false;
                        }
                    });
                }
            }).start();
        }
    }
    public void DealDamage(){
        Player.HP -= DAMAGE;
    }
    public void FollowPlayer(float dt){
        if((int) position.x <= (int) Player.position.x + ATTACK_RADIUS && (int) position.x >= (int) Player.position.x - (ATTACK_RADIUS / 3) && (int) Player.position.y <= (int) position.y + (ATTACK_RADIUS / 4)){
            Attack(dt);
            return;
        }else if(position.x > Player.position.x){
            goToLeft();
        }else if(position.x < Player.position.x){
            goToRight();
        }
        switch (looksIn){
            case 1:
                animation_to_go = animation_to_go_left;
                animation_attack = animation_attack_left;
                break;
            case 2:
                animation_to_go = animation_to_go_right;
                animation_attack = animation_attack_right;
                break;
        }
    }
    private void VerifyHP(){
        if((HP/2) != healthBarBG.getWidth() && HP > -1){
            if(!healthBarBG.isDisposed()) {
                healthBarBG.dispose();
            }
            healthBarBG = new Pixmap(HP/2, 10, Pixmap.Format.RGBA8888);
            healthBarBG.setColor(Color.RED);
            healthBarBG.fill();
            healthBar.dispose();
            healthBar = new Texture(healthBarBG);
        }
    }
}
