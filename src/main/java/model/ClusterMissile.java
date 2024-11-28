package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class ClusterMissile extends Rectangle {

    public ClusterMissile(Plane plane) {
        super(9, 6);
        setX(plane.getX() + plane.getWidth()/2 - 5);
        setY(plane.getY() + 2);
        this.setFill(new ImagePattern(new Image(Missile.class.getResource("/assets/clusterbomb.png").toExternalForm())));
    }
}
