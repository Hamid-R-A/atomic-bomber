package view.animations;

import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.*;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MissileAnimation extends Transition {
    private Timer timer = new Timer();
    private final Game game;
    private final Pane pane;
    private final Missile missile;
    private final double acceleration = 0.01;
    private double vSpeed;
    private double hSpeed;
    private final int duration = 100;
    private MediaPlayer mediaPlayer = new MediaPlayer(new Media(MissileAnimation.class.getResource("/sound/ok.wav").toExternalForm()));

    public MissileAnimation(Game game, Plane plane, Pane pane, Missile missile) {
        this.game = game;
        this.pane = pane;
        this.missile = missile;
        hSpeed = Math.cos(Math.toRadians(plane.getRotate()));
        vSpeed = Math.sin(Math.toRadians(plane.getRotate()));
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(duration));
        missile.setRotate(plane.getRotate());
    }

    @Override
    protected void interpolate(double v) {
        double y = missile.getY() + vSpeed;
        double x = missile.getX() + hSpeed;
        vSpeed += acceleration; // vSpeed + acceleration

        //missile rotate
        if (missile.getRotate() > -90 && missile.getRotate() < 90) {
            missile.setRotate(missile.getRotate() + 0.4);
        } else {
            missile.setRotate(missile.getRotate() - 0.4);
        }

        //check intersection of missile to nodes and increase points or bombs
        for (Tank tank : game.getTanksOfTheGame()) {
            if (missile.getBoundsInParent().intersects(tank.getBoundsInParent())) {
                tanksIntersection(tank);
                this.stop();
                pane.getChildren().remove(missile);
                break;
            }
        }
        if (game.getTruckOfTheGame() != null && missile.getBoundsInParent().intersects(game.getTruckOfTheGame().getBoundsInParent())) {
            trucksIntersection();
            pane.getChildren().remove(missile);
        } else if (game.getApartmentOfTheGame() != null && missile.getBoundsInParent().intersects(game.getApartmentOfTheGame().getBoundsInParent())) {
            apartmentIntersection();
            pane.getChildren().remove(missile);
        } else if (game.getTrenchOfTheGame() != null && missile.getBoundsInParent().intersects(game.getTrenchOfTheGame().getBoundsInParent())) {
            trenchIntersection();
            pane.getChildren().remove(missile);
        } else if (game.getTreeOfTheGame() != null && missile.getBoundsInParent().intersects(game.getTreeOfTheGame().getBoundsInParent())) {
            treeIntersection();
            pane.getChildren().remove(missile);
        }
        if (missile.getY() >= 450){
            stop();
            ExplosingTheMissile explosionAnimation = new ExplosingTheMissile(missile, pane);
            explosionAnimation.play();
            mediaPlayer.play();
            pane.getChildren().remove(missile);
        }
        missile.setY(y);
        missile.setX(x);
    }

    private void burningTheTrench() {
        Fire fire = new Fire(game.getTrenchOfTheGame().getX(), game.getTrenchOfTheGame().getY());
        pane.getChildren().add(fire);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            double count = 0;

            @Override
            public void run() {
                count += 1;
                if (count > 4) {
                    Platform.runLater(() -> {
                        pane.getChildren().remove(game.getTrenchOfTheGame());
                        pane.getChildren().remove(fire);
                    });
                    timer.cancel();
                }
            }
        }, 0, 300);
    }

    public void treeIntersection() {
        burnTheHitter(game.getTreeOfTheGame());
        game.setTreeOfTheGame(null);
        game.getGameView().setKillsToKnowToGoToNextWave(game.getGameView().getKillsToKnowToGoToNextWave() + 1);
    }

    public void trenchIntersection() {
        //set score and burnings
        if (!game.getTrenchOfTheGame().isHeat()) {
            game.getGameView().setNumberOfClusters(game.getGameView().getNumberOfClusters() + 1);
            game.setKills(game.getKills() + game.getTrenchOfTheGame().getKillAmount());
            game.getGameView().setNumberOfKills(game.getKills());
            burningTheTrench();
            game.getTrenchOfTheGame().setHeat(true);
            game.getGameView().setKillsToKnowToGoToNextWave(game.getGameView().getKillsToKnowToGoToNextWave() + 1);
        }
    }


    public void apartmentIntersection() {
        //set score and burnings
        if (!game.getApartmentOfTheGame().isHeat()) {
            game.getGameView().setNumberOfNuclear(game.getGameView().getNumberOfNuclear() + 1);
            game.setKills(game.getKills() + game.getApartmentOfTheGame().getKillAmount());
            game.getGameView().setNumberOfKills(game.getKills());
            burnTheHitter(game.getApartmentOfTheGame());
            game.getApartmentOfTheGame().setHeat(true);
            game.getGameView().setKillsToKnowToGoToNextWave(game.getGameView().getKillsToKnowToGoToNextWave() + 1);
        }
    }

    public void trucksIntersection() {
        //set score
        if (!game.getTruckOfTheGame().isHeat()) {
            game.setKills(game.getKills() + game.getTruckOfTheGame().getKillAmount());
            game.getGameView().setNumberOfKills(game.getKills());
            //set burnings
            game.getTruckOfTheGame().getTruckAnimation().stop();
            burnTheHitter(game.getTruckOfTheGame());
            game.getTruckOfTheGame().setHeat(true);
            game.getGameView().setKillsToKnowToGoToNextWave(game.getGameView().getKillsToKnowToGoToNextWave() + 1);
        }
    }

    private void burnTheHitter(Rectangle rectangle) {
        Fire fire = new Fire(rectangle.getX(), rectangle.getY());
        pane.getChildren().add(fire);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            double count = 0;

            @Override
            public void run() {
                count += 1;
                if (count > 4) {
                    Platform.runLater(() -> {
                        pane.getChildren().remove(rectangle);
                        pane.getChildren().remove(fire);
                    });
                    timer.cancel();
                }
            }
        }, 0, 300);
    }

    public void tanksIntersection(Tank tank) {
        //set score and burn the goals
        if (!tank.isHeat()) {
            game.setKills(game.getKills() + tank.getKillAmount());
            game.getGameView().setNumberOfKills(game.getKills());
            game.getTanksOfTheGame().remove(tank);
            tank.getTankAnimation().stop();
            burnTheHitter(tank);
            tank.setHeat(true);
            if (tank.isBullet()){
                pane.getChildren().remove(game.getGameView().getCircleOfTank());

            }
            game.getGameView().setKillsToKnowToGoToNextWave(game.getGameView().getKillsToKnowToGoToNextWave() + 1);
        }
    }
}
