package view.animations;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Game;
import model.Truck;

import java.net.URL;

public class TruckAnimation extends Transition {
    private Truck truck;
    private Pane pane;
    private Game game;
    private double speed = 0.5;
    private final int duration = 100;
    public TruckAnimation(Pane pane, Game game, Truck truck) {
        this.pane = pane;
        this.game = game;
        this.truck = truck;
        this.setCycleDuration(Duration.millis(duration));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double x = truck.getX() + speed;
        if (truck.getX() >= game.getWIDTH() && speed > 0) {
            speed = -speed;
            x = truck.getX() + speed;
            truck.setScaleX(-1);
        } else if (truck.getX() <= 0 && speed < 0) {
            speed = -speed;
            x = truck.getX() + speed;
            truck.setScaleX(1);
        }
        truck.setX(x);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
