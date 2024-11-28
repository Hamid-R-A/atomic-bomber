package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Fire extends Rectangle {
    public Fire(double x, double y) {
        super(9, 5);
        setX(x);
        setY(y);
        setWidth(50);
        setHeight(50);
        this.setFill(new ImagePattern(new Image(Missile.class.getResource("/assets/fire1.png").toExternalForm())));
    }

}
