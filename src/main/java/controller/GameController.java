package controller;

import javafx.animation.RotateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.*;
import view.GameView;
import view.animations.BulletAnimation;
import view.animations.ClusterMissileAnimation;
import view.animations.MissileAnimation;
import view.animations.NuclearMissilAnimation;

import java.util.*;

public class GameController {
    public static void rotateUp(Plane plane) {
        if (plane.getScaleY() == 1) {
            plane.setRotate(plane.getRotate() - 10);
        } else
            plane.setRotate(plane.getRotate() + 10);
    }

    public static void rotateDown(Plane plane) {
        if (plane.getScaleY() == 1) {
            plane.setRotate(plane.getRotate() + 10);
        } else
            plane.setRotate(plane.getRotate() - 10);
    }

    public static void shoot(Pane pane, Plane plane, Game game) {
        game.getGameView().increaseNumberOfUsedMissile();
        Missile missile = new Missile(plane);
        pane.getChildren().add(missile);
        MissileAnimation missileAnimation = new MissileAnimation(game, plane, pane, missile);
        missileAnimation.play();
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(GameView.class.getResource("/sound/explosion.mp3").toExternalForm()));
        mediaPlayer.play();
    }

    public static void rotateRight(Plane plane) {
        plane.setRotate(0);
        plane.setScaleY(1);
    }

    public static void rotateLeft(Plane plane) {
        plane.setRotate(180);
        plane.setScaleY(-1);
    }

    public static void shootNuclear(Pane pane, Plane plane, Game game) {
        NuclearMissile nuclearMissile = new NuclearMissile(plane);
        pane.getChildren().add(nuclearMissile);
        NuclearMissilAnimation nuclearMissilAnimation = new NuclearMissilAnimation(game, plane, pane, nuclearMissile);
        nuclearMissilAnimation.play();
    }

    public static void shootCluster(Pane pane, Plane plane, Game game) {
        ClusterMissile clusterMissile = new ClusterMissile(plane);
        pane.getChildren().add(clusterMissile);
        ClusterMissileAnimation clusterMissileAnimation = new ClusterMissileAnimation(game, plane, pane, clusterMissile);
        clusterMissileAnimation.play();
    }

    public static void throwBulletMig(Mig mig, Game game, Pane pane, Plane plane) {
        Bullet bullet = new Bullet(mig);
        pane.getChildren().add(bullet);
        BulletAnimation bulletAnimation = new BulletAnimation(game, pane, bullet, plane);
        bulletAnimation.play();
    }

    public static void arrangeTheUsersByKillNumber() {
        User.getAllUsers().sort(Comparator.comparing(User::getPoints).reversed().thenComparing(User::getLastWave));
    }

    public static void arrangeTheUsersByAccuracy() {
        User.getAllUsers().sort(Comparator.comparing(User::getAccuracy).reversed().thenComparing(User::getLastWave));
    }

    public static void arrangeTheUsersByHardless() {
        User.getAllUsers().sort(Comparator.comparing(User::getPointAccordingToDegreeOfHardless).reversed().thenComparing(User::getLastWave));
    }

    public static void throwBulletTank(Tank tank, Pane pane, Game game, Plane plane) {
        Bullet bullet = new Bullet(tank);
        pane.getChildren().add(bullet);
        BulletAnimation bulletAnimation = new BulletAnimation(game, pane, bullet, plane);
        bulletAnimation.play();
    }
}
