package com.mygdx.game.Models;

import com.mygdx.game.Actors.Bochek;

import java.util.ArrayList;

/**
 * Created by andrei on 28.01.2017.
 */

public class CurrentState {


    private float time;
    private int lvlID;
    private Bochek bochek;
    private ArrayList<WallModel> wallList;

    public CurrentState(){
        time = -12f;
        bochek = null;
        lvlID = -1;
        wallList = new ArrayList<WallModel>();
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public int getLvlID() {
        return lvlID;
    }

    public void setLvlID(int lvlID) {
        this.lvlID = lvlID;
    }

    public Bochek getBochek() {
        return bochek;
    }

    public void setBochek(Bochek bochek) {
        this.bochek = bochek;
    }

    public ArrayList<WallModel> getWallList() {
        return wallList;
    }

    public void setWallList(ArrayList<WallModel> wallList) {
        this.wallList = wallList;
    }
}
