package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.animations.TruckAnimation;

public class Truck extends Rectangle {
    private final int killAmount = 2;
    private TruckAnimation truckAnimation;
    private boolean isHeat = false;
    public Truck(double x, double y) {
        super(x, y, 40, 30);
        setFill(new ImagePattern(new Image(Truck.class.getResource("/assets/truck.png").toExternalForm())));
    }

    public TruckAnimation getTruckAnimation() {
        return truckAnimation;
    }

    public void setTruckAnimation(TruckAnimation truckAnimation) {
        this.truckAnimation = truckAnimation;
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
}
