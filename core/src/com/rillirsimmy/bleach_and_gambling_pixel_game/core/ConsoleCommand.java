package com.rillirsimmy.bleach_and_gambling_pixel_game.core;

import com.badlogic.gdx.Gdx;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.Monster;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.Player;

public class ConsoleCommand {

    public static void ExecCommand(String command, final GameStateManager gsm){
        switch(command){
            case "-BAGPG start level1":
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        gsm.set(StateType.LEVEL_1);
                    }
                });
                break;
            case "-BAGPG start level2":
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        gsm.set(StateType.LEVEL_2);
                    }
                });
                break;
            case "-BAGPG start level3":
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        gsm.set(StateType.LEVEL_3);
                    }
                });
                break;
            case "-BAGPG start level4":
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        gsm.set(StateType.LEVEL_4);
                    }
                });
                break;
            case "-BAGPG start level5":
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        gsm.set(StateType.LEVEL_5);
                    }
                });
                break;
            case "-BAGPG start level6":
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        gsm.set(StateType.LEVEL_6);
                    }
                });
                break;
            case "-BAGPG start level7":
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        gsm.set(StateType.LEVEL_7);
                    }
                });
                break;
            case "-BAGPG start level8":
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        gsm.set(StateType.LEVEL_8);
                    }
                });
                break;
            case "-BAGPG start level9":
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        gsm.set(StateType.LEVEL_9);
                    }
                });
                break;
            case "-BAGPG start EndGame":
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        gsm.set(StateType.END_GAME);
                    }
                });
                break;
            //Kill
            case "-BAGPG kill monster":
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        Monster.HP = 0;
                    }
                });
                break;
            case "-BAGPG kill player":
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        Player.HP = 0;
                    }
                });
                break;
            default:
                System.out.println("-BAGPG not found command");
                break;
        }
    }

}
