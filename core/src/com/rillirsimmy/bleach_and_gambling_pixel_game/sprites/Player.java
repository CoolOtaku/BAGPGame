package com.rillirsimmy.bleach_and_gambling_pixel_game.sprites;

import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.AnimDispose;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.GifDecoder;

public class Player {

    public static float ATTACK_TIME_OUT = (float) 0.8;
    public static int GO_TO_SPEED = 2;
    public static int JUMP_SPEED = 1;
    public static int HP = 100;
    public static int MANA = 100;
    public static int ATTACK_RADIUS = 50;
    public static Vector3 position;

    private static int looksIn = 2;
    private static float elapsed;
    private static float delayTime = 0;
    private float delayTimeJump = 0;
    private static boolean isAttacked = false;
    private static boolean isJump = true;

    public static Animation<TextureRegion> texture;
    private Animation<TextureRegion> animation_standing;
    private static Animation<TextureRegion> animation_to_go;
    private static Animation<TextureRegion> animation_attack_0;
    private static Animation<TextureRegion> animation_fly;
    private Animation<TextureRegion> animation_blood;
    private static Animation<TextureRegion> animation_treat;

    private Animation<TextureRegion> animation_standing_right;
    private Animation<TextureRegion> animation_standing_left;
    private Animation<TextureRegion> animation_to_go_right;
    private Animation<TextureRegion> animation_to_go_left;
    private Animation<TextureRegion> animation_attack_0_right;
    private Animation<TextureRegion> animation_attack_0_left;
    private Animation<TextureRegion> animation_fly_right;
    private Animation<TextureRegion> animation_fly_left;
    private Animation<TextureRegion> animation_treat_right;
    private Animation<TextureRegion> animation_treat_left;

    private static Sound soundAttack;

    public Player(int x, int y) {
        position = new Vector3(x, y, 0);
        ATTACK_TIME_OUT = (float) 0.8;
        GO_TO_SPEED = 2;
        JUMP_SPEED = 1;
        HP = 100;
        MANA = 100;
        ATTACK_RADIUS = 50;

        animation_standing_right = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/player_standing_right.gif").read());
        animation_to_go_right = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/player_to_go_right.gif").read());
        animation_attack_0_right = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/player_attack_0_right.gif").read());
        animation_standing_left = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/player_standing_left.gif").read());
        animation_to_go_left = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/player_to_go_left.gif").read());
        animation_attack_0_left = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/player_attack_0_left.gif").read());
        animation_fly_right = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/player_fly_right.gif").read());
        animation_fly_left = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/player_fly_left.gif").read());
        animation_blood = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/player_blood.gif").read());
        animation_treat_right = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/player_treat_right.gif").read());
        animation_treat_left = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/player_treat_left.gif").read());

        animation_standing = animation_standing_right;
        animation_to_go = animation_to_go_right;
        animation_attack_0 = animation_attack_0_right;
        animation_fly = animation_fly_right;
        animation_treat = animation_treat_right;

        texture = animation_standing;
        soundAttack = Gdx.audio.newSound(Gdx.files.internal("music/playerAttack.mp3"));
    }

    protected void handleInput(float dt) {
        if(Gdx.input.isKeyPressed(Input.Keys.R)){
            Treat();
            return;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            Attack(13);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            goToRight();
        }else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            goToLeft();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            Jump();
        }
    }

    public void update(float dt) {
        VerifyState();
        handleInput(dt);
    }

    public void render(SpriteBatch sb) {
        elapsed += Gdx.graphics.getDeltaTime();
        sb.draw(texture.getKeyFrame(elapsed), position.x, position.y);

        if (isAttacked){
            sb.draw(animation_blood.getKeyFrame(elapsed), Monster.position.x, Monster.position.y, 70, 70);
        }
    }

    public void dispose() {
        //AnimDispose(texture);
        AnimDispose(animation_standing);
        AnimDispose(animation_to_go);
        AnimDispose(animation_attack_0);
        AnimDispose(animation_fly);
        AnimDispose(animation_blood);
        AnimDispose(animation_treat);

        AnimDispose(animation_standing_right);
        AnimDispose(animation_standing_left);
        AnimDispose(animation_to_go_right);
        AnimDispose(animation_to_go_left);
        AnimDispose(animation_attack_0_right);
        AnimDispose(animation_attack_0_left);
        AnimDispose(animation_fly_right);
        AnimDispose(animation_fly_left);
        AnimDispose(animation_treat_right);
        AnimDispose(animation_treat_left);
        soundAttack.dispose();
    }

    private void VerifyState(){
        switch (looksIn){
            case 1:
                animation_standing = animation_standing_left;
                animation_to_go = animation_to_go_left;
                animation_attack_0 = animation_attack_0_left;
                animation_fly = animation_fly_left;
                animation_treat = animation_treat_left;
                break;
            case 2:
                animation_standing = animation_standing_right;
                animation_to_go = animation_to_go_right;
                animation_attack_0 = animation_attack_0_right;
                animation_fly = animation_fly_right;
                animation_treat = animation_treat_right;
                break;
        }
        if(!isAttacked && texture.equals(animation_attack_0)){
            texture = animation_standing;
        }
        if(isJump){
            delayTimeJump += Gdx.graphics.getDeltaTime();
            if(delayTimeJump > 1){
                isJump = false;
                delayTimeJump = 0;
                position.set(position.x, 10, 0);
                texture = animation_standing;
            }
        }
        if(!texture.equals(animation_standing) && !texture.equals(animation_attack_0)){
            texture = animation_standing;
        }
    }

    public static void goToRight(){
        position.add(GO_TO_SPEED, 0, 0);
        looksIn = 2;
        texture = animation_to_go;
    }
    public static void goToLeft(){
        position.sub(GO_TO_SPEED, 0, 0);
        looksIn = 1;
        texture = animation_to_go;
    }
    public static void Jump(){
        isJump = true;
        texture = animation_fly;
        switch (looksIn){
            case 1:
                position.add(0, JUMP_SPEED + JUMP_SPEED, 0);
                position.sub(JUMP_SPEED, 0, 0);
                break;
            case 2:
                position.add(JUMP_SPEED, JUMP_SPEED + JUMP_SPEED, 0);
                break;
        }
    }
    public static void Attack(int damage) {
        if(MANA <= 0){
            return;
        }
        texture = animation_attack_0;
        delayTime += Gdx.graphics.getDeltaTime();
        if (delayTime > ATTACK_TIME_OUT) {
            if (looksIn == 1 && Monster.position.x + Monster.texture.getKeyFrame(elapsed).getRegionWidth() - (ATTACK_RADIUS / 1.5) >= position.x && Monster.position.x <= position.x) {
                AttackDamage(damage);
            }else if (looksIn == 2 && Monster.position.x <= position.x + (ATTACK_RADIUS * 3) && Monster.position.x >= position.x) {
                AttackDamage(damage);
            }
        }
    }
    public static void AttackDamage(int damage){
        soundAttack.play();
        DealDamage(damage);
        delayTime = 0;
        isAttacked = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
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
    public static void DealDamage(int damage){
        Monster.HP -= damage;
        MANA -= 1;
    }
    public static void Treat(){
        texture = animation_treat;
        delayTime += Gdx.graphics.getDeltaTime();
        if (delayTime > 1 && HP < 100 || delayTime > 1 && MANA < 100) {
            delayTime = 0;
            HP+=2;
            if(HP>100){
                HP=100;
            }
            MANA+=2;
            if(MANA>100){
                MANA=100;
            }
        }
    }

    public void reset() {
        HP = 100;
        MANA = 100;
        position.x = 10;
        position.y = 10;
    }
}
