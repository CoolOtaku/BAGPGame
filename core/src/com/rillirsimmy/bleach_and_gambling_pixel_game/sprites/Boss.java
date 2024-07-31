package com.rillirsimmy.bleach_and_gambling_pixel_game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.GifDecoder;

public class Boss extends Monster {

    public Boss(int x, int y) {
        super(x, y, "music/bossSpawn.mp3", "music/bossAttack.mp3");
        reset();
        animation_to_go_left = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/boss_left.gif").read());
        animation_to_go_right = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/boss_right.gif").read());
        animation_attack_right = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/boss_attack_right.gif").read());
        animation_attack_left = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/boss_attack_left.gif").read());

        animation_to_go = animation_to_go_left;
        animation_attack = animation_attack_left;
        texture = animation_to_go;
    }
    @Override
    public void reset() {
        GO_TO_SPEED = (float) 0.3;
        ATTACK_TIME_OUT = (float) 1.3;
        HP = 1000;
        ATTACK_RADIUS = 70;
        DAMAGE = 33;
        position.x = 800;
        position.y = 0;
        soundSpawn.play();
    }
    @Override
    public void FollowPlayer(float dt){
        if((int) position.x <= (int) Player.position.x + ATTACK_RADIUS && (int) position.x + texture.getKeyFrame(dt).getRegionWidth() - ATTACK_RADIUS >= (int) Player.position.x){
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
}
