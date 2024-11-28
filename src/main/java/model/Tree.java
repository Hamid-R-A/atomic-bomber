package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Tree extends Rectangle {
    public Tree(double x, double y) {
        super(x, y, 20, 50);
        setFill(new ImagePattern(new Image(Tree.class.getResource("/assets/tree.png").toExternalForm())));
    }
}
