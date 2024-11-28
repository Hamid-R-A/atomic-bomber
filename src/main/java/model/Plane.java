package model;

import controller.GameController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Plane extends Rectangle {
    public Plane(Game game) {
        super(50, 15);
        setX((game.getWIDTH() - 100) / 2);
        setY(150);
        setFill(new ImagePattern(new Image(Plane.class.getResource("/assets/plane2.png").toExternalForm())));
    }
}
