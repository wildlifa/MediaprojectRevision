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

    public String getString() {
        if (isUnlocked) {
            String tempString = "Level:" + levelID + "\n" + "Personal Record:\n" + personalBestTime + "\nWorld Record:\n" + worldBestTime + "\n by " + worldBestPlayer;
            return tempString;
        } else {
            String tempString = "(LOCKED)\n" + "Level:" + levelID + "\n" + "World Record:\n" + worldBestTime + "\n by " + worldBestPlayer;
            return tempString;
        }
    }
}
