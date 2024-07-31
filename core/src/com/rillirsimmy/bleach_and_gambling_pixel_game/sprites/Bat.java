package com.rillirsimmy.bleach_and_gambling_pixel_game.sprites;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.rillirsimmy.bleach_and_gambling_pixel_game.Const;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.GifDecoder;

public class Bat extends Monster{

    public Bat(int x, int y) {
        super(x, y, "music/batSpawn.mp3", "music/batAttack.mp3");
        reset();
        animation_to_go_left = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/bat_left.gif").read());
        animation_to_go_right = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/bat_right.gif").read());
        animation_attack_right = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/bat_attack_right.gif").read());
        animation_attack_left = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/bat_attack_left.gif").read());

        animation_to_go = animation_to_go_left;
        animation_attack = animation_attack_left;
        texture = animation_to_go;
    }
    @Override
    public void reset() {
        GO_TO_SPEED = (float) 1.3;
        ATTACK_TIME_OUT = (float) 1;
        HP = 90;
        ATTACK_RADIUS = 70;
        DAMAGE = 10;
        position.x = random.nextInt(Const.WIDTH);
        position.y = 40;
        soundSpawn.play();
    }
}
