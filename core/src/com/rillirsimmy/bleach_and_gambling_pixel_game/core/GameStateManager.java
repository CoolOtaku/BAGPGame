package com.rillirsimmy.bleach_and_gambling_pixel_game.core;

import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.StateType.END_GAME;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.StateType.LEVEL_2;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.StateType.LEVEL_3;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.StateType.LEVEL_4;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.StateType.LEVEL_5;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.StateType.LEVEL_6;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.StateType.LEVEL_7;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.StateType.LEVEL_8;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.StateType.LEVEL_9;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;
import java.util.Stack;

import com.rillirsimmy.bleach_and_gambling_pixel_game.states.EndGame;
import com.rillirsimmy.bleach_and_gambling_pixel_game.states.GameOverState;
import com.rillirsimmy.bleach_and_gambling_pixel_game.states.Level;
import com.rillirsimmy.bleach_and_gambling_pixel_game.states.MenuState;

public class GameStateManager {

    public StateType currentStateType;
    public Level level;

    private final Pool<MenuState> menuStatePool = new Pool<MenuState>() {
        @Override
        protected MenuState newObject() {
            return new MenuState(GameStateManager.this);
        }
    };
    private final Pool<GameOverState> gameOverStatePool = new Pool<GameOverState>() {
        @Override
        protected GameOverState newObject() {
            return new GameOverState(GameStateManager.this);
        }
    };
    private final Pool<EndGame> endGamePool = new Pool<EndGame>() {
        @Override
        protected EndGame newObject() {
            return new EndGame(GameStateManager.this);
        }
    };
    private final Pool<Level> levelPool = new Pool<Level>() {
        @Override
        protected Level newObject() {
            return new Level(GameStateManager.this);
        }
    };

    public Stack<State> states;

    public GameStateManager(){
        states = new Stack<State>();
    }

    public void push(StateType stateType){
        pushState(stateType);
    }

    public void dispose(){
        states.peek().dispose();
    }

    public void set(StateType stateType){
        destroyState();
        pushState(stateType);
    }

    public void update(float dt){
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }

    public void resize(int width, int height) {
        states.peek().resize(width, height);
    }

    private void pushState(StateType stateType){
        switch(stateType){
            case MENU:
                states.push(menuStatePool.obtain());
                break;
            case GAME_OVER:
                states.push(gameOverStatePool.obtain());
                break;
            case END_GAME:
                states.push(endGamePool.obtain());
                break;
            case LEVEL_1:
                states.push(levelPool.obtain());
                level = (Level) states.peek();
                level.init("img/bgLevel1.jpg",20, LEVEL_2);
                break;
            case LEVEL_2:
                states.push(levelPool.obtain());
                level = (Level) states.peek();
                level.init("img/bgLevel2.png",15, LEVEL_3);
                break;
            case LEVEL_3:
                states.push(levelPool.obtain());
                level = (Level) states.peek();
                level.init("img/bgLevel3.jpg",15, LEVEL_4);
                break;
            case LEVEL_4:
                states.push(levelPool.obtain());
                level = (Level) states.peek();
                level.init("img/bgLevel4.jpg",20, LEVEL_5);
                break;
            case LEVEL_5:
                states.push(levelPool.obtain());
                level = (Level) states.peek();
                level.init(GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("img/bgLevel5.gif").read()),15, LEVEL_6);
                break;
            case LEVEL_6:
                states.push(levelPool.obtain());
                level = (Level) states.peek();
                level.init("img/bgLevel6.jpeg",15, LEVEL_7);
                break;
            case LEVEL_7:
                states.push(levelPool.obtain());
                level = (Level) states.peek();
                level.init("img/bgLevel7.jpeg",15, LEVEL_8);
                break;
            case LEVEL_8:
                states.push(levelPool.obtain());
                level = (Level) states.peek();
                level.init("img/bgLevel8.jpg",25, LEVEL_9);
                break;
            case LEVEL_9:
                states.push(levelPool.obtain());
                level = (Level) states.peek();
                level.init("img/bgLevel8.jpg",3, END_GAME);
                break;
            default:
                states.push(menuStatePool.obtain());
                break;
        }
        currentStateType = stateType;
    }

    private void destroyState(){
        if(states.peek() instanceof MenuState){
            menuStatePool.free((MenuState) states.pop());
        }else if(states.peek() instanceof GameOverState) {
            gameOverStatePool.free((GameOverState) states.pop());
        }else if(states.peek() instanceof EndGame){
            endGamePool.free((EndGame) states.pop());
        }else if(states.peek() instanceof Level){
            levelPool.free((Level) states.pop());
        }else{
            states.pop().dispose();
        }
    }
}
