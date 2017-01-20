package com.mygdx.game.Models;

/**
 * Created by andrei on 20.01.2017.
 */

public class TileModel {


    protected enum TileType {WALL, WAY,};


    private TileType tileType;
    private boolean isLeader;
    private boolean isAddedToWall;
    private boolean isHorizontal;

    public TileModel(int tileType){
        this.isAddedToWall = false;
        this.isLeader = false;
        this.isHorizontal = false;
        switch (tileType) {
            case 1: this.tileType = TileType.WALL;
                break;
            case 0: this.tileType = TileType.WAY;
                break;
        }
    }

    public boolean isAddedToWall() {
        return isAddedToWall;
    }

    public void setAddedToWall(boolean addedToWall) {
        isAddedToWall = addedToWall;
    }

    public TileType getTileType() {
        return tileType;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }
}
