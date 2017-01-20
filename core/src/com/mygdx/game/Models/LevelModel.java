package com.mygdx.game.Models;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by andrei on 19.01.2017.
 */

public class LevelModel {
    private ArrayList<WallModel> horizontalWalls;
    private ArrayList<WallModel> verticalWalls;
    private TileModel[][] tiles;
    private int modelWidth;
    private int modelHeight;


    public LevelModel(String levelCode){
        horizontalWalls = new ArrayList<WallModel>();
        verticalWalls = new ArrayList<WallModel>();
        convertCodeToTileModel(levelCode);
        convertTileModelToWallModel();
        //printLevelModel();
    }

    private void printLevelModel() {
        System.out.println("Horizontal walls:");
        for(WallModel wall : horizontalWalls){
            System.out.println(wall.getPositionX());
            System.out.println(wall.getPositionY());
            System.out.println(wall.getLength());
            System.out.println("-----------------------------");
        }
        System.out.println("Vertical walls:");
        for(WallModel wall : verticalWalls){
            System.out.println(wall.getPositionX());
            System.out.println(wall.getPositionY());
            System.out.println(wall.getLength());
            System.out.println("-----------------------------");
        }
        System.out.println("Done Printing");
    }

    private void convertTileModelToWallModel() {
        for (int i = 0; i < modelHeight; i++){
            for (int j = 0; j < modelWidth; j++){

                if (!tiles[i][j].isAddedToWall() && (tiles[i][j].getTileType() == TileModel.TileType.WALL)){
                    generateNewWall(i, j);
                }
            }
        }
    }

    private void generateNewWall(int i, int j) {
        boolean rightNeighbourIsCandidate = false;
        boolean bottomNeighbourIsCandidate = false;
        if (j < modelWidth-1){
            if(getRightNeighbour(i,j).getTileType() == TileModel.TileType.WALL){
                rightNeighbourIsCandidate = true;
            }
        }
        if (i < modelHeight-1){
            if(getBottomNeighbour(i,j).getTileType() == TileModel.TileType.WALL){
                bottomNeighbourIsCandidate = true;
            }
        }
        if ((!rightNeighbourIsCandidate) && (!bottomNeighbourIsCandidate)){
            buildSoloWall(i,j);
        }

        if ((rightNeighbourIsCandidate) && (!bottomNeighbourIsCandidate)){
            buildHorizontalWall(i,j);
        }

        if ((!rightNeighbourIsCandidate) && bottomNeighbourIsCandidate){
            buildVerticalWall(i,j);
        }

        if (rightNeighbourIsCandidate && bottomNeighbourIsCandidate){
            buildSpecialWall(i,j);
        }

    }

    private void buildSpecialWall(int i, int j) {
        boolean horizontalCandidate = false;
        boolean verticalCandidate = false;
        tiles[i][j].setLeader(true);
        tiles[i][j].setAddedToWall(true);

        if (i>0){
            if(getTopNeighbour(i,j).getTileType() == TileModel.TileType.WALL){
                if(getTopNeighbour(i,j).isAddedToWall()){
                    if(getTopNeighbour(i,j).isHorizontal()){
                        verticalCandidate = true;
                    }else{
                        horizontalCandidate = true;
                    }
                }
            }
        }

        if (j>0){
            if(getLeftNeighbour(i,j).getTileType() == TileModel.TileType.WALL){
                if(getLeftNeighbour(i,j).isAddedToWall()){
                    if(getLeftNeighbour(i,j).isHorizontal()){
                        verticalCandidate = true;
                    }else{
                        horizontalCandidate = true;
                    }
                }
            }
        }

        if (horizontalCandidate != verticalCandidate){
            if (horizontalCandidate){
                buildHorizontalWall(i,j);
            }else{
                buildVerticalWall(i,j);
            }
        }

        if (horizontalCandidate == verticalCandidate){
            Random generator = new Random();
            int r = generator.nextInt(2);
            if (r==0){
                buildHorizontalWall(i,j);
            }else{
                tiles[i][j].setHorizontal(false);
                buildVerticalWall(i,j);
            }
        }
    }

    private void buildHorizontalWall(int i, int j) {
        tiles[i][j].setLeader(true);
        tiles[i][j].setAddedToWall(true);
        tiles[i][j].setHorizontal(true);
        boolean possibleToExpand = true;
        Random generator = new Random();
        int maxWallSize = generator.nextInt(3)+3;
        int supremeWallSize = modelWidth - j;
        int wallRunner = j;
        int currentWallSize = 1;
        while ((currentWallSize < supremeWallSize) && (currentWallSize < maxWallSize) && possibleToExpand){
            possibleToExpand = false;
            wallRunner++;
            if(tiles[i][wallRunner].getTileType()== TileModel.TileType.WALL){
                if(!tiles[i][wallRunner].isAddedToWall()){
                    tiles[i][wallRunner].setAddedToWall(true);
                    tiles[i][wallRunner].setHorizontal(true);
                    possibleToExpand = true;
                    currentWallSize++;
                }
            }
        }
        horizontalWalls.add(new WallModel(i,j,currentWallSize));
        System.out.println("Added horizontal wall x:" + j + " y:" + i + " Size:" + currentWallSize);
    }

    private void buildVerticalWall(int i, int j) {
        tiles[i][j].setLeader(true);
        tiles[i][j].setAddedToWall(true);
        tiles[i][j].setHorizontal(false);
        boolean possibleToExpand = true;
        Random generator = new Random();
        int maxWallSize = generator.nextInt(3)+3;
        int supremeWallSize = modelHeight - i;
        int wallRunner = i;
        int currentWallSize = 1;
        while ((currentWallSize < supremeWallSize) && (currentWallSize < maxWallSize) && possibleToExpand){
            possibleToExpand = false;
            wallRunner++;
            if(tiles[wallRunner][j].getTileType()== TileModel.TileType.WALL){
                if(!tiles[wallRunner][j].isAddedToWall()){
                    tiles[wallRunner][j].setAddedToWall(true);
                    tiles[wallRunner][j].setHorizontal(false);
                    possibleToExpand = true;
                    currentWallSize++;
                }
            }
        }
        horizontalWalls.add(new WallModel(i,j,currentWallSize));
        System.out.println("Added vertical wall x:" + j + " y:" + i + " Size:" + currentWallSize);
    }

    private void buildSoloWall(int i, int j) {
        boolean horizontalCandidate = false;
        boolean verticalCandidate = false;
        tiles[i][j].setLeader(true);
        tiles[i][j].setAddedToWall(true);
        if (i>0){
            if(getTopNeighbour(i,j).getTileType() == TileModel.TileType.WALL){
                if(getTopNeighbour(i,j).isAddedToWall()){
                    if(getTopNeighbour(i,j).isHorizontal()){
                        verticalCandidate = true;
                    }else{
                        horizontalCandidate = true;
                    }
                }
            }
        }

        if (j>0){
            if(getLeftNeighbour(i,j).getTileType() == TileModel.TileType.WALL){
                if(getLeftNeighbour(i,j).isAddedToWall()){
                    if(getLeftNeighbour(i,j).isHorizontal()){
                        verticalCandidate = true;
                    }else{
                        horizontalCandidate = true;
                    }
                }
            }
        }

        if (horizontalCandidate != verticalCandidate){
            if (horizontalCandidate){
                tiles[i][j].setHorizontal(true);
                horizontalWalls.add(new WallModel(i,j,1));
                System.out.println("Added horizontal wall x:" + j + " y:" + i + " Size:" + 1);
            }else{
                tiles[i][j].setHorizontal(false);
                verticalWalls.add(new WallModel(i,j,1));
                System.out.println("Added vertical wall x:" + j + " y:" + i + " Size:" + 1);
            }
        }

        if (horizontalCandidate == verticalCandidate){
            Random generator = new Random();
            int r = generator.nextInt(2);
            if (r==0){
                tiles[i][j].setHorizontal(true);
                horizontalWalls.add(new WallModel(i,j,1));
                System.out.println("Added horizontal wall x:" + j + " y:" + i + " Size:" + 1);
            }else{
                tiles[i][j].setHorizontal(false);
                verticalWalls.add(new WallModel(i,j,1));
                System.out.println("Added vertical wall x:" + j + " y:" + i + " Size:" + 1);
            }
        }
    }

    private TileModel getLeftNeighbour(int i, int j) {
        return tiles[i][j-1];
    }

    private TileModel getBottomNeighbour(int i, int j) {
        return tiles[i+1][j];
    }

    private TileModel getTopNeighbour(int i, int j) {
        return tiles[i-1][j];
    }

    private TileModel getRightNeighbour(int i, int j) {
        return tiles[i][j+1];
    }


    private void convertCodeToTileModel(String levelCode) {
        int dotPosition = levelCode.indexOf(".");
        int slashPosition = levelCode.indexOf("/");
        String levelWidth = levelCode.substring(0, dotPosition);
        String levelHeight = levelCode.substring(dotPosition +1, slashPosition);
        modelWidth = Integer.valueOf(levelWidth);
        modelHeight = Integer.valueOf(levelHeight);
        String tileCode = levelCode.substring(slashPosition +1);
        fillTileModel(tileCode);


    }

    private void fillTileModel(String tileCode) {
        int stringRunner = 0;
        String subString;
        tiles = new TileModel[modelWidth][modelHeight];
        for (int i = 0; i < modelHeight; i++){
            for (int j = 0; j < modelWidth; j++){
                subString = tileCode.substring(stringRunner, stringRunner+1);
                tiles[i][j] = new TileModel(Integer.valueOf(subString));
                stringRunner++;
            }
        }
    }
}
