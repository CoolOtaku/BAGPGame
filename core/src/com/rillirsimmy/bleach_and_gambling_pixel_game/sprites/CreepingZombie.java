package com.rillirsimmy.bleach_and_gambling_pixel_game.sprites;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.rillirsimmy.bleach_and_gambling_pixel_game.Const;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.GifDecoder;

public class CreepingZombie extends Monster{

    public CreepingZombie(int x, int y){
        super(x, y, "music/zombieSpawn.mp3", "music/zombieAttack.mp3");
        reset();
        animation_to_go_left = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/zombie_to_go_left.gif").read());
        animation_to_go_right = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/zombie_to_go_right.gif").read());
        animation_attack_right = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/zombie_attack_right.gif").read());
        animation_attack_left = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/zombie_attack_left.gif").read());

        animation_to_go = animation_to_go_left;
        animation_attack = animation_attack_left;
        texture = animation_to_go;
    }
    @Override
    public void reset() {
        GO_TO_SPEED = (float) 0.6;
        ATTACK_TIME_OUT = (float) 1;
        HP = 100;
        ATTACK_RADIUS = 80;
        DAMAGE = 5;
        position.x = random.nextInt(Const.WIDTH);
        position.y = 10;
        soundSpawn.play();
    }
}
