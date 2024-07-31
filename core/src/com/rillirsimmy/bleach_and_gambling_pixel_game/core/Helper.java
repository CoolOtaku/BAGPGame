package com.rillirsimmy.bleach_and_gambling_pixel_game.core;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rillirsimmy.bleach_and_gambling_pixel_game.Const;
import com.rillirsimmy.bleach_and_gambling_pixel_game.obj.GameSettings;
import com.rillirsimmy.bleach_and_gambling_pixel_game.obj.Saves;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.Player;

public class Helper {

    public static Preferences settingsPrefs = Gdx.app.getPreferences("settings");
    private static GameSettings gameSettings;
    public static Saves saves = new Saves();
    private static float localTime = 0;

    public static float ScaleX(float size){
        float difference = (float) Const.R_WIDTH / Const.WIDTH;
        float res = size*difference;
        return res;
    }
    public static float ScaleY(float size){
        float difference = (float) Const.R_HEIGHT / Const.HEIGHT;
        float res = size*difference;
        return res;
    }

    public static float ReturnX(float size){
        float difference = (float) Const.R_WIDTH / Const.WIDTH;
        float res = size/difference;
        return res;
    }
    public static float ReturnY(float size){
        float difference = (float) Const.R_HEIGHT / Const.HEIGHT;
        float res = size/difference;
        return res;
    }

    public static void AnimDispose(Animation<TextureRegion> texture){
        Object[] f = texture.getKeyFrames();
        for(int i = 0; i < f.length; i++){
            TextureRegion tr = (TextureRegion) f[i];
            tr.getTexture().dispose();
        }
    }

    public static void LoadSettings(){
        if(settingsPrefs.contains("settings")){
            gameSettings = json.fromJson(GameSettings.class, settingsPrefs.getString("settings"));
            Const.interfaceIndentation = gameSettings.getInterfaceIndentation();
            Const.isMusicOn = gameSettings.isMusicOn();
        }
    }
    public static void SaveSettings(GameSettings settings){
        settingsPrefs.putString("settings", json.toJson(settings));
        settingsPrefs.flush();
    }

    public static void SaveGame(StateType levelType, int progress){
        saves.setLevelType(levelType);
        saves.setProgress(progress);
        saves.setHp(Player.HP);
        saves.setMana(Player.MANA);

        settingsPrefs.putString("saves", json.toJson(saves));
        settingsPrefs.flush();
    }
    public static boolean LoadSavesGame(){
        if(settingsPrefs.contains("saves")){
            saves = json.fromJson(Saves.class, settingsPrefs.getString("saves"));
            if(saves.getLevelType() == StateType.MENU){
                return false;
            }
            return true;
        }
        return false;
    }

    public static void SaveTime(float time){
        localTime = LoadTime();
        float f = localTime+time;
        settingsPrefs.putFloat("time", f);
        settingsPrefs.flush();
    }
    public static void DeleteTime(){
        if(settingsPrefs.contains("time")) {
            settingsPrefs.putFloat("time", 0);
            settingsPrefs.flush();
        }
    }
    public static float LoadTime(){
        if(settingsPrefs.contains("time")) {
            settingsPrefs.flush();
            return settingsPrefs.getFloat("time");
        }
        return 0;
    }
}
