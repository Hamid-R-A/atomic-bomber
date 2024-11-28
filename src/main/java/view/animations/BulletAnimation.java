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
import view.Main;
import view.MenuView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BulletAnimation extends Transition {
    private Timer timer = new Timer();
    private Plane plane;
    private final Game game;
    private Mig mig;
    private Tank tank;
    private final Pane pane;
    private final Bullet bullet;
    private double vSpeed;
    private double hSpeed;
    private final int duration = 1000;
    private MediaPlayer mediaPlayer = new MediaPlayer(new Media(MissileAnimation.class.getResource("/sound/ironBomb.mp3").toExternalForm()));
    private Random random = new Random();

    public BulletAnimation(Game game, Pane pane, Bullet bullet, Plane plane) {
        this.game = game;
        this.pane = pane;
        this.bullet = bullet;
        this.plane = plane;
        vSpeed = (plane.getY() - bullet.getY()) / 100;
        hSpeed = (plane.getX() - bullet.getX()) / 100;
        this.setCycleDuration(Duration.millis(50));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double y = bullet.getY() + vSpeed;
        double x = bullet.getX() + hSpeed;
        if (bullet.getBoundsInParent().intersects(plane.getBoundsInParent())) {
            if (game.getGameView().getNumberOfHp() > 1 && !bullet.isHeat()) {
                game.getGameView().setNumberOfHp(game.getGameView().getNumberOfHp() - 1);
                bullet.setHeat(true);
                justExploseThePlane();
                mediaPlayer.play();
            } else if (game.getGameView().getNumberOfHp() == 1){
                explosingThePlaneAndFinish();
                User.getCurrentUser().setPoints(game.getGameView().getNumberOfKills());
                User.getCurrentUser().setPointAccordingToDegreeOfHardless(game.getGameView().getNumberOfKills() * User.getCurrentUser().getHardless());
                User.getCurrentUser().setLastWave(Integer.parseInt(game.getGameView().getWaveNumber().getText().substring(4)));
                if (game.getGameView().getMissileUseInWave1() + game.getGameView().getMissileUseInWave2() + game.getGameView().getMissileUseInWave3() != 0) {
                    User.getCurrentUser().setAccuracy((double) (game.getGameView().getKillsToKnowToGoToNextWave() * 100)
                            / (game.getGameView().getMissileUseInWave1() + game.getGameView().getMissileUseInWave2() + game.getGameView().getMissileUseInWave3()));
                }
                MediaPlayer mediaPlayer = new MediaPlayer(new Media(FlyingAnimation.class.getResource("/sound/ok.wav").toExternalForm()));
                mediaPlayer.play();
            }
        }
        bullet.setX(x);
        bullet.setY(y);

    }

    private void justExploseThePlane() {
        ImageView imageView = new ImageView(new Image(FlyingAnimation.class.getResource("/assets/bigblast1.png").toExternalForm()));
        imageView.setX(plane.getX());
        imageView.setY(plane.getY() - 50);
        imageView.setFitHeight(plane.getHeight() + 50);
        imageView.setFitWidth(plane.getWidth() + 50);
        pane.getChildren().add(imageView);
        plane.setX(200);
        plane.setY(200);
        plane.setRotate(0);
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

    private void explosingThePlaneAndFinish() {
        Timer timer = new Timer();
        ImageView imageView = new ImageView(new Image(FlyingAnimation.class.getResource("/assets/bigblast1.png").toExternalForm()));
        imageView.setX(plane.getX());
        imageView.setY(plane.getY() - 50);
        imageView.setFitHeight(plane.getHeight() + 50);
        imageView.setFitWidth(plane.getWidth() + 50);
        pane.getChildren().add(imageView);
        pane.getChildren().remove(plane);
        timer.schedule(new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                count++;
                if (count == 4) {
                    Platform.runLater(() -> {
                        pane.getChildren().remove(imageView);
                        MenuView menuView = new MenuView();
                        try {
                            menuView.start(Main.stage);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });

                    timer.cancel();
                }
            }
        }, 0, 300);
    }
}
