package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

import java.net.URL;

public class LastControll extends Application {
    public boolean win;
    public Label wonLose;
    public Label lastWave;
    public Label kills;
    public Label accuracy;

//    public LastControll(String hp) {
//        if (hp.equals("0")) {
//            win = false;
//        }
//    }

    @FXML
    public void initialize() {

        if (GameView.winCheck) {
            wonLose.setText("you won");
        } else {
            wonLose.setText("you lost");
        }
        accuracy.setText("accuracy: " + String.valueOf(User.getCurrentUser().getAccuracy()));
        kills.setText("all kills: " + String.valueOf(User.getCurrentUser().getPoints()));
        lastWave.setText("last wave: " + String.valueOf(User.getCurrentUser().getLastWave()));
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = Main.class.getResource("/FXML/lastStage.fxml");
        Pane root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goMainMenu(MouseEvent mouseEvent) {
        MenuView menuView = new MenuView();
        try {
            menuView.start(Main.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
