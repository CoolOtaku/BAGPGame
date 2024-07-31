package com.rillirsimmy.bleach_and_gambling_pixel_game.sprites;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import com.rillirsimmy.bleach_and_gambling_pixel_game.states.Level;

public class Fruit {

    public static int HIL = 2;

    private Texture texture;
    public Vector3 position = new Vector3();

    private static Sound soundEatFruit;

    public Fruit(){
        soundEatFruit = Gdx.audio.newSound(Gdx.files.internal("music/eatFruit.mp3"));
    }

    public void init(){
        texture = new Texture(Gdx.files.internal("img/"+(random.nextInt(11) +1)+".png"));
        position.set(Monster.position);
    }

    public void update(float dt) {
        if(Player.position.x + (Player.texture.getKeyFrame(dt).getTexture().getWidth() / 2) >= position.x && Player.position.x + (Player.texture.getKeyFrame(dt).getTexture().getWidth() / 2) <= position.x + (texture.getWidth() / 5) && Player.position.y <= position.y + (texture.getHeight() / 5)){
            Player.HP+=HIL;
            if(Player.HP>100){
                Player.HP=100;
            }
            Player.MANA+=HIL;
            if(Player.MANA>100){
                Player.MANA=100;
            }
            Level.fruitPool.free(Fruit.this);
            Level.fruits.removeValue(Fruit.this, true);
            soundEatFruit.play();
        }
    }

    public void render(SpriteBatch sb) {
        sb.draw(texture, position.x, position.y, texture.getWidth() / 5, texture.getHeight() / 5);
    }

    public void dispose() {
        texture.dispose();
        soundEatFruit.dispose();
    }
}
