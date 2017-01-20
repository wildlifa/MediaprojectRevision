package com.mygdx.game.Models;

/**
 * Created by andrei on 20.01.2017.
 */

public class WallModel {
    private int positionX, positionY;
    private int length;

    public WallModel(int x, int y, int length){
        this.positionX = x;
        this.positionY = y;
        this.length = length;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getLength() {
        return length;
    }
}
