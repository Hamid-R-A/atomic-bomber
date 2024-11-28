package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

import java.net.URL;

public class SettingView extends Application {
    @FXML
    private CheckBox hard;
    @FXML
    private CheckBox medium;
    @FXML
    private CheckBox easy;
    @FXML
    private CheckBox blackWhite;
    @FXML
    private ImageView soundOn;
    @FXML
    private ImageView soundOff;
    @FXML
    private CheckBox WDSAUse;
    @FXML
    private CheckBox arrowUse;

    @FXML
    public void initialize() {
        if (User.getCurrentUser().getHardless() == 1) {
            easy.setSelected(true);
        } else if (User.getCurrentUser().getHardless() == 2) {
            medium.setSelected(true);
        } else {
            hard.setSelected(true);
        }

        if (User.getCurrentUser().isBlackWhiteGame()){
            blackWhite.setSelected(true);
        }

        if (User.getCurrentUser().isUsingArrows()){
            arrowUse.setSelected(true);
        } else {
            WDSAUse.setSelected(true);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = Main.class.getResource("/FXML/SettingScene.fxml");
        Pane root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void turnOfMusic(MouseEvent mouseEvent) {
        soundOff.setVisible(true);
        soundOn.setVisible(false);
        RegisterMenuView.getCurrentMusic().stop();
    }

    public void playMusic(MouseEvent mouseEvent) {
        soundOff.setVisible(false);
        soundOn.setVisible(true);
        RegisterMenuView.getCurrentMusic().play();
    }

    public void backToMainMenu(MouseEvent mouseEvent) {
        MenuView menuView = new MenuView();
        try {
            menuView.start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void useArrows(MouseEvent mouseEvent) {
        arrowUse.setSelected(true);
        WDSAUse.setSelected(false);
        User.getCurrentUser().setUsingArrows(true);
    }

    public void useWDSA(MouseEvent mouseEvent) {
        arrowUse.setSelected(false);
        WDSAUse.setSelected(true);
        User.getCurrentUser().setUsingArrows(false);
    }

    public void blackWhiteGame(MouseEvent mouseEvent) {
        if (!blackWhite.isSelected()) {
            blackWhite.setSelected(false);
            User.getCurrentUser().setBlackWhiteGame(false);
        } else {
            blackWhite.setSelected(true);
            User.getCurrentUser().setBlackWhiteGame(true);
        }
    }

    public void changeTheHardlessToHard(MouseEvent mouseEvent) {
        hard.setSelected(true);
        easy.setSelected(false);
        medium.setSelected(false);
        User.getCurrentUser().setHardless(3);
    }

    public void changeTheHardlessToEasy(MouseEvent mouseEvent) {
        hard.setSelected(false);
        easy.setSelected(true);
        medium.setSelected(false);
        User.getCurrentUser().setHardless(1);
    }

    public void changeTheHardlessToMedium(MouseEvent mouseEvent) {
        hard.setSelected(false);
        easy.setSelected(false);
        medium.setSelected(true);
        User.getCurrentUser().setHardless(2);
    }
}
