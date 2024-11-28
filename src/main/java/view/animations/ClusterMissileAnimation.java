package view.animations;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.*;

import java.util.Timer;

public class ClusterMissileAnimation extends Transition {
    private final ClusterMissile missile;
    private final double acceleration = 0.01;
    private double vSpeed;
    private double hSpeed;
    private final int duration = 100;

    public ClusterMissileAnimation(Game game, Plane plane, Pane pane, ClusterMissile missile) {
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
        missile.setX(x);
        missile.setY(y);
    }
}
