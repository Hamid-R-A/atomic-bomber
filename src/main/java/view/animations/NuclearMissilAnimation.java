package view.animations;

import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import model.*;

import java.util.Timer;
import java.util.TimerTask;

public class NuclearMissilAnimation extends Transition {
    private Timer timer = new Timer()
    ;
    private final Game game;
    private final Plane plane;
    private final Pane pane;
    private final NuclearMissile missile;
    private final double acceleration = 0.01;
    private double vSpeed;
    private double hSpeed;
    private final int duration = 100;
    private final MediaPlayer mediaPlayer = new MediaPlayer(new Media(NuclearMissilAnimation.class.getResource("/sound/boom.mp3").toExternalForm()));
    public NuclearMissilAnimation(Game game, Plane plane, Pane pane, NuclearMissile missile) {
        this.game = game;
        this.plane = plane;
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

        for (Tank tank : game.getTanksOfTheGame()) {
            if (missile.getBoundsInParent().intersects(tank.getBoundsInParent()) || Math.abs(missile.getX() - missile.getY()) < 200) {
                mediaPlayer.play();
                imageOfFire();
                break;
            }
        }
        if (game.getTruckOfTheGame() != null && missile.getBoundsInParent().intersects(game.getTruckOfTheGame().getBoundsInParent())
        && Math.abs(missile.getX() - missile.getY()) < 200) {
            mediaPlayer.play();

        } else if (game.getApartmentOfTheGame() != null && missile.getBoundsInParent().intersects(game.getApartmentOfTheGame().getBoundsInParent())
        && Math.abs(missile.getX() - missile.getY()) < 200) {
            mediaPlayer.play();

        } else if (game.getTrenchOfTheGame() != null && missile.getBoundsInParent().intersects(game.getTrenchOfTheGame().getBoundsInParent())
        && Math.abs(missile.getX() - missile.getY()) < 200) {
            mediaPlayer.play();

        } else if (game.getTreeOfTheGame() != null && missile.getBoundsInParent().intersects(game.getTreeOfTheGame().getBoundsInParent())
        && Math.abs(missile.getX() - missile.getY()) < 200) {
            mediaPlayer.play();
        }
        if (missile.getY() >= 440){
            stop();
            imageOfFire();
            pane.getChildren().remove(missile);
        }

        missile.setX(x);
        missile.setY(y);
    }

    private void imageOfFire() {
        ImageView imageView = new ImageView(new Image(NuclearMissile.class.getResource("/assets/nukestem.png").toExternalForm()));
        imageView.setX(missile.getX());
        imageView.setY(missile.getY() - 50);
        imageView.setFitWidth(100);
        imageView.setFitHeight(400);
        pane.getChildren().add(imageView);
        timer.schedule(new TimerTask() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count > 4){
                    Platform.runLater(() -> {
                        pane.getChildren().remove(imageView);
                    });
                }
            }
        }, 0 , 300);
    }
}
