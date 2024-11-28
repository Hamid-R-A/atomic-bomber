package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class PauseView extends Application {
    public ImageView soundOff;
    public ImageView soundOn;

    @Override
    public void start(Stage stage) throws Exception {
        URL url = Main.class.getResource("/FXML/pauseScene.fxml");
        Pane root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void playMusic(MouseEvent mouseEvent) {
        soundOff.setVisible(false);
        soundOn.setVisible(true);
        RegisterMenuView.getCurrentMusic().play();
    }

    public void turnOfMusic(MouseEvent mouseEvent) {
        soundOff.setVisible(true);
        soundOn.setVisible(false);
        RegisterMenuView.getCurrentMusic().stop();
    }

    public void playMusic1(MouseEvent mouseEvent) {
        RegisterMenuView.getCurrentMusic().stop();
        RegisterMenuView.setCurrentMusic(RegisterMenuView.getMusic1());
        RegisterMenuView.getCurrentMusic().play();
        soundOff.setVisible(false);
        soundOn.setVisible(true);
        RegisterMenuView.getCurrentMusic().play();
    }

    public void continueGame(){

    }

    public void playMusic2(MouseEvent mouseEvent) {
        RegisterMenuView.getCurrentMusic().stop();
        RegisterMenuView.setCurrentMusic(RegisterMenuView.getMusic2());
        RegisterMenuView.getCurrentMusic().play();
        soundOff.setVisible(false);
        soundOn.setVisible(true);
        RegisterMenuView.getCurrentMusic().play();
    }

    public void playMusic3(MouseEvent mouseEvent) {
        RegisterMenuView.getCurrentMusic().stop();
        RegisterMenuView.setCurrentMusic(RegisterMenuView.getMusic3());
        RegisterMenuView.getCurrentMusic().play();
        soundOff.setVisible(false);
        soundOn.setVisible(true);
        RegisterMenuView.getCurrentMusic().play();
    }

    public void goToMainMenu(MouseEvent mouseEvent) {
        MenuView menuView = new MenuView();
        try {
            menuView.start(Main.stage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
