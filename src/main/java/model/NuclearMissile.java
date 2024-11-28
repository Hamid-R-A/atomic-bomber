package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.animations.MissileAnimation;

public class NuclearMissile extends Rectangle {
    public NuclearMissile(Plane plane) {
        super(10, 7);
        setX(plane.getX() + plane.getWidth()/2 - 5);
        setY(plane.getY() + 2);
        this.setFill(new ImagePattern(new Image(Missile.class
                .getResource("/assets/nukebomb.png").toExternalForm())));
    }
}
