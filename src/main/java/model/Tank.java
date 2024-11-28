package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.animations.TankAnimation;

public class Tank extends Rectangle {
    private final int killAmount = 3;
    private boolean isBullet;
    private TankAnimation tankAnimation;
    private boolean isHeat;


    public Tank(double x, double y, boolean isBullet) {
        super(x,y, 30,20);
        setFill(new ImagePattern(new Image(Tank.class.getResource("/assets/tank1.png").toExternalForm())));
        this.isBullet = isBullet;
    }

    public TankAnimation getTankAnimation() {
        return tankAnimation;
    }

    public void setTankAnimation(TankAnimation tankAnimation) {
        this.tankAnimation = tankAnimation;
    }

    public int getKillAmount() {
        return killAmount;
    }

    public boolean isHeat() {
        return isHeat;
    }

    public void setHeat(boolean heat) {
        isHeat = heat;
    }

    public boolean isBullet() {
        return isBullet;
    }
}
