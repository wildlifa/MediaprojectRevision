package com.mygdx.game.Models;

import java.util.ArrayList;

/**
 * Created by andrei on 24.01.2017.
 */

public class SimpleModel {

    private ArrayList<WallModel> wallList;

    private int startX, startY;
    private int escapeX;


    private int escapeY;
    private int currentX, currentY;

    public SimpleModel(String levelCode){
        wallList = new ArrayList<WallModel>();
        currentX = 0;
        currentY = 0;
        escapeX = 0;
        escapeY = 0;
        generateWalls(levelCode);
    }

    private void generateWalls(String levelCode) {
        for (int i=0; i < levelCode.length(); i++){
            switch ((int)levelCode.charAt(i)){
                case 10:
                    currentX = 0;
                    currentY++;
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
                    System.out.println("Unknown char: " + levelCode.charAt(i));
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


    public ArrayList<WallModel> getWallList() {
        return wallList;
    }


}
