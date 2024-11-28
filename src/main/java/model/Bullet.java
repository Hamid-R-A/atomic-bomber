package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.animations.BulletAnimation;
import view.animations.MissileAnimation;

public class Bullet extends Rectangle {
    private final double WIDTH = 9;
    private  final double HEIGHT = 5;
    private boolean isHeat = false;

    public Bullet(Rectangle rectangle) {
        super(9, 5);
        setX(rectangle.getX() + rectangle.getWidth()/2 - 5);
        setY(rectangle.getY() + 2);
        this.setFill(new ImagePattern(new Image(Missile.class.getResource("/assets/bullet1.png").toExternalForm())));
    }

    public boolean isHeat() {
        return isHeat;
    }

    public void setHeat(boolean heat) {
        isHeat = heat;
    }
}
