package com.rillirsimmy.bleach_and_gambling_pixel_game.states;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.AnimDispose;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.SaveGame;
import static com.rillirsimmy.bleach_and_gambling_pixel_game.core.Helper.SaveTime;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import com.rillirsimmy.bleach_and_gambling_pixel_game.Const;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.GameStateManager;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.MonsterFactory;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.State;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.StateType;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.Fruit;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.Monster;
import com.rillirsimmy.bleach_and_gambling_pixel_game.core.MonsterType;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.NoneMonster;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.Player;

public class Level extends State {

    private Texture background;
    private Animation<TextureRegion> background2;
    private boolean isGif = false;
    private float elapsed;
    private int x;

    public static Music music;
    public Player player;
    public Monster monster;
    private StateType nextState;

    public NavigationState navigationState;
    public static final Pool<Fruit> fruitPool = new Pool<Fruit>() {
        @Override
        protected Fruit newObject() {
            return new Fruit();
        }
    };
    public static Array<Fruit> fruits = new Array<Fruit>();

    public static int countTheDeadMonsters = 0;
    private int MaxCountTheDeadMonsters;
    private int playerWidth;

    public BitmapFont countTheDeadMonstersFont;

    private boolean isGameOver;
    private boolean isGoToNext;
    public static boolean isPaused = false;

    public static float timeSeconds = 0f;
    private float period = 1f;

    public Level(GameStateManager gsm) {
        super(gsm);
        countTheDeadMonstersFont = new BitmapFont(Gdx.files.internal("fonts/hbm.fnt"), Gdx.files.internal("fonts/hbm.png"), false);
        navigationState = new NavigationState();
        player = new Player(10, 10);
        switch (gsm.currentStateType){
            case LEVEL_9:
            case LEVEL_7:
                monster = MonsterFactory.createMonster(MonsterType.SKELETON);
                break;
            case LEVEL_3:
                monster = MonsterFactory.createMonster(MonsterType.BAT);
                break;
            case LEVEL_4:
                monster = MonsterFactory.createMonster(MonsterType.GHOST);
                break;
            case LEVEL_5:
            case LEVEL_6:
                monster = MonsterFactory.createMonster(MonsterType.ZOMBIE_2);
                break;
            default:
                monster = MonsterFactory.createMonster(MonsterType.ZOMBIE_1);
                break;
        }
        monster.reset();
        ResetMusic();
    }
    public void init(String background, int MaxCountTheDeadMonsters, StateType nextState){
        isGif = false;
        this.background = new Texture(Gdx.files.internal(background));
        init(MaxCountTheDeadMonsters, nextState);
    }
    public void init(Animation<TextureRegion> background, int MaxCountTheDeadMonsters, StateType nextState){
        isGif = true;
        this.background2 = background;
        init(MaxCountTheDeadMonsters, nextState);
    }
    private void init(int MaxCountTheDeadMonsters, StateType nextState){
        this.MaxCountTheDeadMonsters = MaxCountTheDeadMonsters;
        player.reset();
        this.nextState = nextState;
        Spawn();
        timeSeconds = 0f;
        isGameOver = false;
        isGoToNext = false;
        isPaused = false;
    }

    @Override
    protected void handleInput() {
        if(!isGoToNext){
            navigationState.handleInput();
        }
    }

    @Override
    public void update(float dt) {
        if(!isPaused){
            timeSeconds += Gdx.graphics.getDeltaTime();
        }
        if(timeSeconds > period){
            timeSeconds-=period;
        }
        if(!isPaused) {
            player.update(dt);
            monster.update(dt);
            VerifyState(dt);
        }
        handleInput();
        navigationState.update(dt);
        for(Fruit fruit:fruits){
            fruit.update(dt);
        }
        if(player.HP <= 0){
            isGameOver = true;
        }
        if(countTheDeadMonsters >= MaxCountTheDeadMonsters){
            isGoToNext = true;
        }
        if(isPaused && !MenuState.music.isPlaying() && Const.isMusicOn){
            MenuState.music.play();
        }else if(!isPaused && MenuState.music.isPlaying()){
            MenuState.music.pause();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        if(isGif){
            elapsed += Gdx.graphics.getDeltaTime();
            sb.draw(background2.getKeyFrame(elapsed), 0, 0, Const.WIDTH, Const.HEIGHT);
        }else {
            sb.draw(background, 0, 0, Const.WIDTH, Const.HEIGHT);
        }
        for(Fruit fruit:fruits){
            fruit.render(sb);
        }
        if(!isPaused) {
            player.render(sb);
            monster.render(sb);
        }
        countTheDeadMonstersFont.draw(sb, "Monsters of the dead: "+countTheDeadMonsters+" of "+MaxCountTheDeadMonsters, 5, 350);
        navigationState.render(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        AnimDispose(background2);
        music.dispose();
        navigationState.dispose();
        monster.dispose();
        for(Fruit fruit:fruits){
            fruit.dispose();
        }
        countTheDeadMonstersFont.dispose();
    }

    @Override
    public void resize(int width, int height) {}

    private void ResetMusic(){
        music = Gdx.audio.newMusic(Gdx.files.internal("music/"+(random.nextInt(8) +1 )+".mp3"));
        music.setLooping(false);
        if(Const.isMusicOn) {
            music.play();
        }
    }
    private void VerifyState(float dt){
        playerWidth = Player.texture.getKeyFrame(dt).getTexture().getWidth();
        if(Player.position.x <= (0 - playerWidth)){
            Player.position.x = Const.WIDTH;
        }else if(Player.position.x >= (Const.WIDTH + playerWidth)){
            Player.position.x = 0 - playerWidth;
        }
        if(!music.isPlaying() && Const.isMusicOn){
            ResetMusic();
        }
        if(isGameOver){
            music.stop();
            gsm.set(StateType.GAME_OVER);
            isGameOver = false;
        }
        if(isGoToNext){
            if(MaxCountTheDeadMonsters != 0){
                MaxCountTheDeadMonsters = 0;
                MonsterFactory.destroyMonster(monster);
                monster = MonsterFactory.createMonster(MonsterType.NONE_MONSTER);
                monster.reset();
                SaveGame(gsm.currentStateType, countTheDeadMonsters);
                SaveTime(timeSeconds);
            }
            player.goToRight();
            if(player.position.x > Const.WIDTH){
                Monster.HP = 0;
                countTheDeadMonsters = 0;
                GoToNextLevel();
            }
        }
        if(monster.HP <= 0){
            countTheDeadMonsters ++;
            fruits.add(fruitPool.obtain());
            fruits.peek().init();
            Spawn();
        }
    }
    private void Spawn(){
        if(countTheDeadMonsters == 0){
            return;
        }
        MonsterFactory.destroyMonster(monster);
        switch (gsm.currentStateType){
            case LEVEL_1:
                monster = MonsterFactory.createMonster(MonsterType.ZOMBIE_1);
                break;
            case LEVEL_2:
                x = random.nextInt(2);
                if (x == 1) {
                    monster = MonsterFactory.createMonster(MonsterType.BAT);
                }else {
                    monster = MonsterFactory.createMonster(MonsterType.ZOMBIE_1);
                }
                break;
            case LEVEL_3:
                x = random.nextInt(2);
                if (x == 1) {
                    monster = MonsterFactory.createMonster(MonsterType.GHOST);
                }else{
                    monster = MonsterFactory.createMonster(MonsterType.BAT);
                }
                break;
            case LEVEL_4:
                monster = MonsterFactory.createMonster(MonsterType.GHOST);
                break;
            case LEVEL_5:
            case LEVEL_6:
                x = random.nextInt(3);
                if (x == 0) {
                    monster = MonsterFactory.createMonster(MonsterType.GHOST);
                }else if(x == 1){
                    monster = MonsterFactory.createMonster(MonsterType.BAT);
                }else{
                    monster = MonsterFactory.createMonster(MonsterType.ZOMBIE_2);
                }
                break;
            case LEVEL_7:
                x = random.nextInt(3);
                if (x == 0) {
                    monster = MonsterFactory.createMonster(MonsterType.SKELETON);
                }else if(x == 1){
                    monster = MonsterFactory.createMonster(MonsterType.BAT);
                }else{
                    monster = MonsterFactory.createMonster(MonsterType.ZOMBIE_2);
                }
                break;
            case LEVEL_8:
                x = random.nextInt(5);
                if (x == 0) {
                    monster = MonsterFactory.createMonster(MonsterType.SKELETON);
                }else if(x == 1){
                    monster = MonsterFactory.createMonster(MonsterType.BAT);
                }else if(x == 2){
                    monster = MonsterFactory.createMonster(MonsterType.ZOMBIE_2);
                }else if(x == 3){
                    monster = MonsterFactory.createMonster(MonsterType.ZOMBIE_1);
                }else{
                    monster = MonsterFactory.createMonster(MonsterType.GHOST);
                }
                break;
            case LEVEL_9:
                if (countTheDeadMonsters == 0){
                    monster = MonsterFactory.createMonster(MonsterType.SKELETON);
                }else if (countTheDeadMonsters == 1){
                    monster = MonsterFactory.createMonster(MonsterType.SKELETON);
                }else{
                    monster = MonsterFactory.createMonster(MonsterType.BOSS);
                }
                break;
        }
        monster.reset();
    }
    public void GoToNextLevel(){
        gsm.set(nextState);
        isGoToNext = false;
    }
}
