package com.rillirsimmy.bleach_and_gambling_pixel_game;

import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.SaveGame;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.SaveTime;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.Scanner;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.ConsoleCommand;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.GameStateManager;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.StateType;
import com.rillirsimmy.bleach_and_gambling_pixel_game.states.Level;

public class Main extends ApplicationAdapter {

	private OrthographicCamera camera;
	private GameStateManager gsm;
	private SpriteBatch batch;
	public static Thread commandThread;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Const.WIDTH, Const.HEIGHT);
		batch = new SpriteBatch();
		gsm = new GameStateManager();

		ScreenUtils.clear(1, 0, 0, 1);
		Helper.LoadSettings();
		gsm.push(StateType.MENU);

		if(Gdx.app.getType() == Application.ApplicationType.Desktop){
			ConsoleCommand();
		}
	}

	@Override
	public void render () {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void pause () {
		if(gsm.states.peek() instanceof Level){
			Level level = (Level) gsm.states.peek();
			SaveGame(level.gsm.currentStateType, level.countTheDeadMonsters);
			SaveTime(level.timeSeconds);
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		gsm.dispose();
	}

	@Override
	public void resize(int width, int height) {
		Const.R_WIDTH = Gdx.graphics.getWidth();
		Const.R_HEIGHT = Gdx.graphics.getHeight();
		gsm.resize(width, height);
	}

	private void ConsoleCommand(){
		final Scanner scanner = new Scanner(System.in);
		commandThread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("-BAGPG print command:");
				String command = scanner.nextLine();
				ConsoleCommand.ExecCommand(command, gsm);
				run();
			}
		});
		commandThread.start();
	}

	public static void StopCommandThread(){
		commandThread = null;
	}
}
