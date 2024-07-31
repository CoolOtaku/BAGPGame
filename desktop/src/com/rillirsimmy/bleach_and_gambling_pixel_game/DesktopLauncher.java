package com.rillirsimmy.bleach_and_gambling_pixel_game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowIcon("img/Ico.png");
		//Bleach and gambling pixel Game
		config.setTitle("BAGP Game");
		config.setWindowedMode(Const.WIDTH,Const.HEIGHT);
		new Lwjgl3Application(new Main(), config);
	}
}
