package com.mygdx.game.Models;

/**
 * Created by andrei on 20.01.2017.
 */

public class WallModel {
    private int positionX, positionY;


    public WallModel(int x, int y){
        this.positionX = x;
        this.positionY = y;

    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }


}
