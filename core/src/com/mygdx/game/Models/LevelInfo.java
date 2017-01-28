package com.mygdx.game.Models;

/**
 * Created by andrei on 26.01.2017.
 */

public class LevelInfo {
    private int levelID;
    private int personalBestTime;
//    private int worldBestTime;
//    private String worldBestPlayer;
    private boolean isUnlocked;

    public LevelInfo(int id, int personal, boolean isUnlocked){
        this.levelID = id;
        this.personalBestTime = personal;
//        this.worldBestTime = world;
//        this.worldBestPlayer = player;
        this.isUnlocked = isUnlocked;

    }

    public String getString() {
        if (isUnlocked) {
            if (personalBestTime <0){
                String tempString = "Level:" + levelID + "\n" + "Personal Record:\n" + "--:--:--" ;
                return tempString;
            } else {
                String tempString = "Level:" + levelID + "\n" + "Personal Record:\n" + personalBestTime ;
                return tempString;
            }

        } else {
            String tempString = "Level:" + levelID + "\n(LOCKED)";
            return tempString;
        }

//        if (isUnlocked) {
//            String tempString = "Level:" + levelID + "\n" + "Personal Record:\n" + personalBestTime + "\nWorld Record:\n" + worldBestTime + "\n by " + worldBestPlayer;
//            return tempString;
//        } else {
//            String tempString = "(LOCKED)\n" + "Level:" + levelID + "\n" + "World Record:\n" + worldBestTime + "\n by " + worldBestPlayer;
//            return tempString;
//        }
    }
}
