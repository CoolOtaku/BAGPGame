package com.rillirsimmy.bleach_and_gambling_pixel_game.core;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.utils.Pool;
import com.rillirsimmy.bleach_and_gambling_pixel_game.Const;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.Bat;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.Boss;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.CreepingZombie;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.Ghost;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.Monster;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.NoneMonster;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.Player;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.Skeleton;
import com.rillirsimmy.bleach_and_gambling_pixel_game.sprites.WalkingZombie;

public class MonsterFactory{

    private static final Pool<CreepingZombie> CreepingZombiePool = new Pool<CreepingZombie>() {
        @Override
        protected CreepingZombie newObject() {
            return new CreepingZombie(random.nextInt(Const.WIDTH - Player.ATTACK_RADIUS), 10);
        }
    };
    private static final Pool<Bat> BatPool = new Pool<Bat>() {
        @Override
        protected Bat newObject() {
            return new Bat(random.nextInt(Const.WIDTH - Player.ATTACK_RADIUS), 40);
        }
    };
    private static final Pool<Boss> BossPool = new Pool<Boss>() {
        @Override
        protected Boss newObject() {
            return new Boss(800, 0);
        }
    };
    private static final Pool<Ghost> GhostPool = new Pool<Ghost>() {
        @Override
        protected Ghost newObject() {
            return new Ghost(random.nextInt(Const.WIDTH - Player.ATTACK_RADIUS), 5);
        }
    };
    private static final Pool<Skeleton> SkeletonPool = new Pool<Skeleton>() {
        @Override
        protected Skeleton newObject() {
            return new Skeleton(random.nextInt(Const.WIDTH - Player.ATTACK_RADIUS), 10);
        }
    };
    private static final Pool<WalkingZombie> WalkingZombiePool = new Pool<WalkingZombie>() {
        @Override
        protected WalkingZombie newObject() {
            return new WalkingZombie(random.nextInt(Const.WIDTH - Player.ATTACK_RADIUS), 15);
        }
    };
    private static final Pool<NoneMonster> NoneMonsterPool = new Pool<NoneMonster>() {
        @Override
        protected NoneMonster newObject() {
            return new NoneMonster();
        }
    };

    public static Monster createMonster(MonsterType monsterType){
        switch(monsterType){
            case ZOMBIE_1:
                return CreepingZombiePool.obtain();
            case BAT:
                return BatPool.obtain();
            case BOSS:
                return BossPool.obtain();
            case GHOST:
                return GhostPool.obtain();
            case SKELETON:
                return SkeletonPool.obtain();
            case ZOMBIE_2:
                return WalkingZombiePool.obtain();
            case NONE_MONSTER:
                return NoneMonsterPool.obtain();
            default:
                return NoneMonsterPool.obtain();
        }
    }

    public static void destroyMonster(Monster monster){
        if(monster instanceof CreepingZombie){
            CreepingZombiePool.free((CreepingZombie) monster);
        }else if(monster instanceof Bat){
            BatPool.free((Bat)monster);
        }else if(monster instanceof Boss){
            BossPool.free((Boss) monster);
        }else if(monster instanceof Ghost){
            GhostPool.free((Ghost) monster);
        }else if(monster instanceof Skeleton){
            SkeletonPool.free((Skeleton) monster);
        }else if(monster instanceof WalkingZombie){
            WalkingZombiePool.free((WalkingZombie) monster);
        }else if(monster instanceof NoneMonster){
            NoneMonsterPool.free((NoneMonster) monster);
        }
    }
}
