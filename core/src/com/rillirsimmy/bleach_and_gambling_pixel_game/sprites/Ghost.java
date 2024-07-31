package com.rillirsimmy.bleach_and_gambling_pixel_game.sprites;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.rillirsimmy.bleach_and_gambling_pixel_game.Const;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.GifDecoder;

public class Ghost extends Monster{

    public Ghost(int x, int y) {
        super(x, y, "music/ghostSpawn.mp3", "music/ghostAttack.mp3");
        reset();
        animation_to_go_left = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/ghost_left.gif").read());
        animation_to_go_right = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/ghost_right.gif").read());
        animation_attack_right = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/ghost_attack_right.gif").read());
        animation_attack_left = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/ghost_attack_left.gif").read());

        animation_to_go = animation_to_go_left;
        animation_attack = animation_attack_left;
        texture = animation_to_go;
    }
    @Override
    public void reset() {
        GO_TO_SPEED = (float) 0.8;
        ATTACK_TIME_OUT = (float) 0.9;
        HP = 120;
        ATTACK_RADIUS = 75;
        DAMAGE = 6;
        position.x = random.nextInt(Const.WIDTH);
        position.y = 5;
        soundSpawn.play();
    }
}
