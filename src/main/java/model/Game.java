package model;

import view.GameView;

import java.util.ArrayList;

public class Game {
    private ArrayList<Tank> tanksOfTheGame = new ArrayList<>();
    private Truck truckOfTheGame;
    private Trench trenchOfTheGame;
    private Apartment apartmentOfTheGame;

    public void setTruckOfTheGame(Truck truckOfTheGame) {
        this.truckOfTheGame = truckOfTheGame;
    }

    public void setTrenchOfTheGame(Trench trenchOfTheGame) {
        this.trenchOfTheGame = trenchOfTheGame;
    }

    public void setApartmentOfTheGame(Apartment apartmentOfTheGame) {
        this.apartmentOfTheGame = apartmentOfTheGame;
    }

    public Tree getTreeOfTheGame() {
        return treeOfTheGame;
    }

    public void setTreeOfTheGame(Tree treeOfTheGame) {
        this.treeOfTheGame = treeOfTheGame;
    }

    private Tree treeOfTheGame;
    private final double WIDTH = 1000;
    private final double HEIGHT = 700;
    private GameView gameView;
    private int kills;

    public Game(GameView gameView) {
        this.gameView = gameView;
        this.kills = 0;
    }
    public ArrayList<Tank> getTanksOfTheGame() {
        return tanksOfTheGame;
    }

    public Truck getTruckOfTheGame() {
        return truckOfTheGame;
    }

    public Trench getTrenchOfTheGame() {
        return trenchOfTheGame;
    }

    public Apartment getApartmentOfTheGame() {
        return apartmentOfTheGame;
    }

    public double getWIDTH() {
        return WIDTH;
    }

    public double getHEIGHT() {
        return HEIGHT;
    }

    public GameView getGameView() {
        return gameView;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }
}
