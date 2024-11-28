package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Apartment extends Rectangle {
    private final int killAmount = 5;
    private boolean isHeat = false;
    public Apartment(double x, double y) {
        super(x, y, 40, 50);
        setFill(new ImagePattern(new Image(Apartment.class.getResource("/assets/building.png").toExternalForm())));
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
