package com.mygdx.game.Models;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by andrei on 23.01.2017.
 */

public class LevelModel {


    private ArrayList<WallModel> horizontalWalls;
    private ArrayList<WallModel> verticalWalls;
    private int modelWidth;
    private int modelHeight;

    private Position startPosition, endPosition;
    private String tileCode;

    public LevelModel(String levelCode){
        tileCode = "";
        modelWidth = 0;
        modelHeight = 0;
        horizontalWalls = new ArrayList<WallModel>();
        verticalWalls = new ArrayList<WallModel>();
        convertCodeToModel(levelCode);
//        printLevel();
    }

    private void printLevel() {
        System.out.println("Printing horizontal walls:");
        for (int i = 0; i < horizontalWalls.size(); i++){
            System.out.println(horizontalWalls.get(i).getPositionX() + " " + horizontalWalls.get(i).getPositionY());

        }
        System.out.println("Printing vertical walls:");
        for (int i = 0; i < verticalWalls.size(); i++){
            System.out.println(verticalWalls.get(i).getPositionX() + " " + verticalWalls.get(i).getPositionY());
        }
    }

    private void convertCodeToModel(String levelCode) {

        int dotPosition = levelCode.indexOf("x");
        int slashPosition = levelCode.indexOf(":");
        String levelHeight = levelCode.substring(0, dotPosition);
        String levelWidth = levelCode.substring(dotPosition +1, slashPosition);
        modelWidth = Integer.valueOf(levelWidth);
        modelHeight = Integer.valueOf(levelHeight);
        tileCode = levelCode.substring(slashPosition +1);
        tileCode.concat(":");
        int startPos = tileCode.indexOf("s");
        int endPos = tileCode.indexOf("e");
        startPosition = new Position(get2DPosition(startPos).getX(),get2DPosition(startPos).getY());
        endPosition = new Position(get2DPosition(endPos).getX(),get2DPosition(endPos).getY());

//        System.out.println("Starting at X = " + startPosition.getX() + " Y = " + startPosition.getY());
//        System.out.println("Ending at X = " + endPosition.getX() + " Y = " + endPosition.getY());

        organizeWalls();


    }

    private void organizeWalls() {
        Position tempPos = new Position(0,0);
        for (int i = 0; i < modelHeight; i++){
            for (int j = 0; j < modelWidth; j++){
                tempPos.setX(j);
                tempPos.setY(i);
//                System.out.println("X = " + tempPos.getX() + " Y = " + tempPos.getY());
//                System.out.println("SPOS = " + get1DPosition(tempPos));
//                System.out.println("--------------------------");
                if (tileCode.substring(get1DPosition(tempPos),get1DPosition(tempPos)+1).equals("1")){
                    initializeWall(tempPos);
                }
            }
        }
    }

    private void initializeWall(Position tempPos) {
        Random generator = new Random();
        int r = generator.nextInt(2);
        if (r==0){
            System.out.println("Adding horizontal wall at " + tempPos.getX() + " " + tempPos.getY());
            horizontalWalls.add(new WallModel(tempPos.getX(),tempPos.getY()));
        }else{
            System.out.println("Adding vertical wall at " + tempPos.getX() + " " + tempPos.getY());
            verticalWalls.add(new WallModel(tempPos.getX(),tempPos.getY()));
        }
    }

    private Position get2DPosition(int position){

        int xPos = position % modelWidth;
        int yPos = position / modelWidth;
        Position temp = new Position(xPos,yPos);
        return temp;
    }

    private int get1DPosition(Position pos2d){
        int pos = modelWidth * pos2d.getY() + pos2d.getX();
        return pos;
    }

    public ArrayList<WallModel> getHorizontalWalls() {
        return horizontalWalls;
    }

    public void setHorizontalWalls(ArrayList<WallModel> horizontalWalls) {
        this.horizontalWalls = horizontalWalls;
    }

    public ArrayList<WallModel> getVerticalWalls() {
        return verticalWalls;
    }

    public void setVerticalWalls(ArrayList<WallModel> verticalWalls) {
        this.verticalWalls = verticalWalls;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

}
