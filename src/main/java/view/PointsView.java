package view;

import controller.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Game;
import model.User;

import java.net.URL;

public class PointsView extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        URL url = Main.class.getResource("/FXML/pointsScene.fxml");
        Pane root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //==============
        GameController.arrangeTheUsersByKillNumber();
        showTheUsersByKill(root);
        //============
        GameController.arrangeTheUsersByAccuracy();
        showTheUsersByAccuracy(root);
        //==============
        GameController.arrangeTheUsersByHardless();
        showTheUsersByHardlessPoint(root);
    }

    private void showTheUsersByHardlessPoint(Pane root) {
        for (int i = 0; i < User.getAllUsers().size(); i++) {
            Label label = new Label((i + 1) + ":  " + User.getAllUsers().get(i).getUsername() + "   kills: "
                    + User.getAllUsers().get(i).getPointAccordingToDegreeOfHardless() + "  wave:" + User.getAllUsers().get(i).getLastWave());
            label.setLayoutX(26);
            label.setLayoutY(25 + 20 * i);
            label.setPrefHeight(18);
            label.setPrefWidth(220);
            root.getChildren().add(label);
            if (i < 3) {
                label.setStyle("-fx-text-fill: #0040ff;");
            }
        }
    }

    private void showTheUsersByAccuracy(Pane root) {
        for (int i = 0; i < User.getAllUsers().size(); i++) {
            Label label = new Label((i + 1) + ":  " + User.getAllUsers().get(i).getUsername() + "  accuracy: "
                    + (int) User.getAllUsers().get(i).getAccuracy() + "  wave:" + User.getAllUsers().get(i).getLastWave());
            label.setLayoutX(215);
            label.setLayoutY(25 + 20 * i);
            label.setPrefHeight(18);
            label.setPrefWidth(220);
            root.getChildren().add(label);
            if (i < 3) {
                label.setStyle("-fx-text-fill: #0040ff;");
            }
        }
    }

    private void showTheUsersByKill(Pane root) {
        for (int i = 0; i < User.getAllUsers().size(); i++) {
            Label label = new Label((i + 1) + ":  " + User.getAllUsers().get(i).getUsername() + "  kills: "
                    + (int) User.getAllUsers().get(i).getPoints() + "  wave:" + User.getAllUsers().get(i).getLastWave());
            label.setLayoutX(437);
            label.setLayoutY(25 + 20 * i);
            label.setPrefHeight(18);
            label.setPrefWidth(220);
            root.getChildren().add(label);
            if (i < 3) {
                label.setStyle("-fx-text-fill: #0040ff;");
            }
        }
    }

    public void backToMainMenu(MouseEvent mouseEvent) {
        MenuView menuView = new MenuView();
        try {
            menuView.start(Main.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
