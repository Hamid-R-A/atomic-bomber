package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.animations.MissileAnimation;

public class Missile extends Rectangle {
    public Missile(Plane plane) {
        super(9, 5);
        setX(plane.getX() + plane.getWidth()/2 - 5);
        setY(plane.getY() + 2);
        this.setFill(new ImagePattern(new Image(Missile.class.getResource("/assets/ironbomb.png").toExternalForm())));
    }

}
