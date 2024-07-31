package com.rillirsimmy.bleach_and_gambling_pixel_game.sprites;

import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.ScaleY;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.rillirsimmy.bleach_and_gambling_pixel_game.Const;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.GifDecoder;

public class NoneMonster extends Monster {

    public NoneMonster() {
        super(0, 0, "music/victory.mp3", "music/victory.mp3");
        reset();
        animation_to_go_left = null;
        animation_to_go_right = null;
        animation_attack_right = null;
        animation_attack_left = null;

        animation_to_go = animation_to_go_left;
        animation_attack = animation_attack_left;
    }

    @Override
    public void update(float dt) {
        return;
    }

    @Override
    public void dispose() {
        return;
    }

    @Override
    public void goToRight(){
        return;
    }

    @Override
    public void goToLeft(){
        return;
    }

    @Override
    public void Attack(float dt){
        return;
    }

    @Override
    public void DealDamage(){
        return;
    }

    @Override
    public void reset() {
        texture = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/fireworks.gif").read());
        GO_TO_SPEED = (float) 0;
        ATTACK_TIME_OUT = (float) 0;
        HP = 1;
        ATTACK_RADIUS = 0;
        DAMAGE = 0;
        dispose();
        position.x = (Const.WIDTH / 2) - 480 / 2;
        position.y = ScaleY(91);
        soundSpawn.play();
    }
}
