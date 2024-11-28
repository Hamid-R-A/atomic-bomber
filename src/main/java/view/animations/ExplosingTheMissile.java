package view.animations;


import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Missile;

public class ExplosingTheMissile extends Transition {
    private final Pane pane;
    private ImageView imageView =  new ImageView();

    public ExplosingTheMissile(Missile missile, Pane pane) {
        this.pane = pane;
        this.setCycleCount(1);
        this.setCycleDuration(Duration.millis(1000));
        imageView.setX(missile.getX());
        imageView.setY(missile.getY());
        imageView.setFitHeight(10);
        imageView.setFitWidth(20);
        pane.getChildren().add(imageView);
    }

    @Override
    protected void interpolate(double v) {
        int number = 1;
        if (0 <= v && v <= 0.33) number = 1;
        else if (0.33 < v && v <= 0.66) number = 2;
        else if (0.66 < v && v <= 1) number = 3;

        imageView.setImage(new Image(ExplosingTheMissile.class.getResource("/assets/bigblast" + number + ".png").toExternalForm()));

        this.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(imageView);
            }
        });

    }
}

