package com.mygdx.game.Models;

/**
 * Created by andrei on 26.01.2017.
 */

public class LevelInfo {
    private int levelID;
    private int personalBestTime;
    private int worldBestTime;
    private String worldBestPlayer;
    private boolean isUnlocked;

    public LevelInfo(int id, int personal, int world, String player, boolean isUnlocked){
        this.levelID = id;
        this.personalBestTime = personal;
        this.worldBestTime = world;
        this.worldBestPlayer = player;
        this.isUnlocked = isUnlocked;
    }
}
