package com.rillirsimmy.bleach_and_gambling_pixel_game.sprites;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.rillirsimmy.bleach_and_gambling_pixel_game.Const;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.GifDecoder;

public class Skeleton extends Monster{

    public Skeleton(int x, int y) {
        super(x, y, "music/skeletonSpawn.mp3", "music/skeletonAttack.mp3");
        reset();
        animation_to_go_left = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/skeleton_left.gif").read());
        animation_to_go_right = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/skeleton_right.gif").read());
        animation_attack_right = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/skeleton_attack_right.gif").read());
        animation_attack_left = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/skeleton_attack_left.gif").read());

        animation_to_go = animation_to_go_left;
        animation_attack = animation_attack_left;
        texture = animation_to_go;
    }
    @Override
    public void reset() {
        GO_TO_SPEED = (float) 0;
        ATTACK_TIME_OUT = (float) 0.8;
        HP = 200;
        ATTACK_RADIUS = 82;
        DAMAGE = 20;
        position.x = random.nextInt(Const.WIDTH);
        position.y = 10;
        soundSpawn.play();
    }
    @Override
    public void FollowPlayer(float dt){
        if((int) position.x <= (int) Player.position.x + ATTACK_RADIUS + (ATTACK_RADIUS / 5) && (int) position.x + texture.getKeyFrame(dt).getRegionWidth() - ((int) ATTACK_RADIUS / 3) >= (int) Player.position.x){
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
