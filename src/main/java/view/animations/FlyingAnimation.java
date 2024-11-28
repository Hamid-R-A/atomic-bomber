package view.animations;

import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import model.Game;
import model.Plane;
import model.User;
import view.LastControll;
import view.Main;
import view.MenuView;

import java.util.Timer;
import java.util.TimerTask;

public class FlyingAnimation extends Transition {
    private final Game game;
    private final Pane pane;
    private final Plane plane;
    private final double speed = 1;
    private final int duration = 100;

    public FlyingAnimation(Game game, Pane pane, Plane plane, Button pause) {
        this.game = game;
        this.pane = pane;
        this.plane = plane;
        this.setCycleDuration(Duration.millis(duration));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double x = plane.getX() + speed * Math.cos(Math.toRadians(plane.getRotate()));
        double y = plane.getY() + speed * Math.sin(Math.toRadians(plane.getRotate()));
        if (x >= game.getWIDTH()) {
            plane.setX(0);
        } else if (x < 0) {
            plane.setX(game.getWIDTH());
        } else if (y <= 0) {
        } else if (y >= 450) {
            if (game.getGameView().getNumberOfHp() == 1) {
                User.getCurrentUser().setPoints(game.getGameView().getNumberOfKills());
                User.getCurrentUser().setLastWave(Integer.parseInt(game.getGameView().getWaveNumber().getText().substring(4)));
                User.getCurrentUser().setPointAccordingToDegreeOfHardless(game.getGameView().getNumberOfKills() * User.getCurrentUser().getHardless());
                User.getCurrentUser().setAccuracy((double) (game.getGameView().getKillsToKnowToGoToNextWave() * 100)
                        /(game.getGameView().getMissileUseInWave1() + game.getGameView().getMissileUseInWave2() + game.getGameView().getMissileUseInWave3()));
                MediaPlayer mediaPlayer = new MediaPlayer(new Media(FlyingAnimation.class.getResource("/sound/ok.wav").toExternalForm()));

                LastControll lastControll = new LastControll();
                try {
                    lastControll.start(Main.stage);
                    stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                putTimerToFire();
                plane.setX((game.getWIDTH() - 100) / 2);
                plane.setY(game.getHEIGHT() - 350);
                plane.setRotate(0);
                game.getGameView().setNumberOfHp(game.getGameView().getNumberOfHp() - 1);
            }
        } else {
            plane.setX(x);
            plane.setY(y);
        }
    }

    private void putTimerToFire() {
        Timer timer = new Timer();
        ImageView imageView = new ImageView(new Image(FlyingAnimation.class.getResource("/assets/bigblast1.png").toExternalForm()));
        imageView.setX(plane.getX());
        imageView.setY(plane.getY() - 50);
        imageView.setFitHeight(plane.getHeight() + 50);
        imageView.setFitWidth(plane.getWidth() + 50);
        pane.getChildren().add(imageView);
        timer.schedule(new TimerTask() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count == 4) {
                    Platform.runLater(() -> {
                        pane.getChildren().remove(imageView);
                    });
                    timer.cancel();
                }
            }
        }, 0, 300);
    }
}

