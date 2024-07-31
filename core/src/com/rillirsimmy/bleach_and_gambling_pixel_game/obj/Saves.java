package com.rillirsimmy.bleach_and_gambling_pixel_game.obj;

import com.rillirsimmy.bleach_and_gambling_pixel_game.core.StateType;

public class Saves {

    private StateType levelType;
    private int progress;
    private int hp;
    private int mana;

    public Saves() {}

    public StateType getLevelType() {
        return levelType;
    }

    public void setLevelType(StateType levelType) {
        this.levelType = levelType;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }
}
