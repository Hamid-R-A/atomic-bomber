package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.animations.MigAnimation;

public class Mig extends Rectangle {
    private double radius = 50;
    public Mig(Game game) {
        super(50, 15);
        setX(-110);
        setY(game.getHEIGHT() - 550);
        setFill(new ImagePattern(new Image(Plane.class.getResource("/assets/mig1.png").toExternalForm())));
    }
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
