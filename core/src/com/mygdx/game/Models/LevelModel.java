package com.mygdx.game.Models;

import java.util.ArrayList;

/**
 * Created by andrei on 24.01.2017.
 */

public class LevelModel {

    private ArrayList<WallModel> wallList;

    private int startX, startY;
    private int escapeX, escapeY;
    private int koloX, koloY;
    private int andreiX, andreiY;


    private int hriushaX, hriushaY;
    private int currentX, currentY;

    public LevelModel(String levelCode){
        wallList = new ArrayList<WallModel>();
        currentX = 0;
        currentY = 0;
        escapeX = 0;
        escapeY = 0;
        koloX = 0;
        koloY = 0;
        generateWalls(levelCode);
    }

    private void generateWalls(String levelCode) {
        for (int i=0; i < levelCode.length(); i++){
            switch ((int)levelCode.charAt(i)){
                case 10:
                    currentX = 0;
                    currentY++;
                    break;
                case 107:
                    koloX = currentX;
                    koloY = currentY;
                    currentX++;
                    break;
                case 97:
                    andreiX = currentX;
                    andreiY = currentY;
                    currentX++;
                    break;
                case 104:
                    hriushaX = currentX;
                    hriushaY = currentY;
                    currentX++;
                    break;
                case 61:
                    wallList.add(new WallModel(currentX, currentY));
                    currentX++;
                    break;
                case 48:
                    currentX++;
                    break;
                case 115:
                    startX = currentX;
                    startY = currentY;
                    currentX++;
                    break;
                case 101:
                    escapeX = currentX;
                    escapeY = currentY;
                    currentX++;
                    break;
                default:
                    System.out.println("Unknown char: " + (int)levelCode.charAt(i)+ " at position:" + i);
                    currentX++;
                    break;
            }


        }

    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEscapeY() {
        return escapeY;
    }

    public int getEscapeX() {
        return escapeX;
    }

    public int getKoloX() {
        return koloX;
    }
    public int getKoloY() {
        return koloY;
    }
    public int getAndreiY() {
        return andreiY;
    }

    public int getAndreiX() {
        return andreiX;
    }


    public int getHriushaX() {
        return hriushaX;
    }

    public int getHriushaY() {
        return hriushaY;
    }
    public ArrayList<WallModel> getWallList() {
        return wallList;
    }


}
