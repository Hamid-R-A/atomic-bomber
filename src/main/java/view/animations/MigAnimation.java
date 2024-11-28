package view.animations;

import controller.GameController;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Bullet;
import model.Game;
import model.Mig;
import model.Plane;

import java.util.Timer;
import java.util.TimerTask;

public class MigAnimation extends Transition {
    private final Mig mig;
    private final Pane pane;
    private final Game game;
    private final ImageView circle;
    private Plane plane;
    private int speed = 1;
    private boolean flag = true;

    public MigAnimation(Game game, Mig mig, Pane pane, Plane plane, ImageView circle) {
        this.mig = mig;
        this.game = game;
        this.pane = pane;
        this.circle = circle;
        this.plane = plane;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(100));
    }

    @Override
    protected void interpolate(double v) {
        if (Math.sqrt(Math.pow(plane.getY() - mig.getY(), 2) + Math.pow(plane.getX() - mig.getX(), 2)) < mig.getRadius()/2
         && flag) {
            GameController.throwBulletMig(mig, game, pane, plane);
            flag = false;
            putTimeDelayAfterShooting();
        }
        mig.setX(mig.getX() + speed);
        circle.setX(circle.getX() + speed);
    }
    private void putTimeDelayAfterShooting() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                flag = true;
            }
        }, 2000);
    }
}
