package com.rillirsimmy.bleach_and_gambling_pixel_game.obj;

public class GameSettings {

    private int interfaceIndentation = 5;
    private boolean isMusicOn = true;

    public GameSettings() {}

    public int getInterfaceIndentation() {
        return interfaceIndentation;
    }

    public void setInterfaceIndentation(int interfaceIndentation) {
        this.interfaceIndentation = interfaceIndentation;
    }

    public boolean isMusicOn() {
        return isMusicOn;
    }

    public void setMusicOn(boolean musicOn) {
        isMusicOn = musicOn;
    }
}
