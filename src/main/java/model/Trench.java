package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Trench extends Rectangle {
    private final int killAmount = 4;
    private boolean isHeat = false;

    public Trench(double v, double v1) {
        super(v, v1, 40, 50);
        setFill(new ImagePattern(new Image(Trench.class.getResource("/assets/bunker1.png").toExternalForm())));
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
