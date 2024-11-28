package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

import java.net.URL;

public class MenuView extends Application {
    @FXML
    private Label username;
    @FXML
    private ImageView avatarShownOnTopOfMenu;

    @FXML
    public void initialize() {
        username.setText(User.getCurrentUser().getUsername());
        avatarShownOnTopOfMenu.setImage(User.getCurrentUser().getImage());
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = Main.class.getResource("/FXML/MenuScene.fxml");
        Pane root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.show();
    }

    public void backToProfileMenu(MouseEvent mouseEvent) {
        ProfileMenuView profileMenuView = new ProfileMenuView();
        try {
            profileMenuView.start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startGame(MouseEvent mouseEvent) {
        GameView gameView = new GameView();
        try {
            setHardlessOfTheGame(gameView);
            gameView.start(Main.stage);
            setFocusForGameView(gameView);
            setBlackAndWhite();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBlackAndWhite() {
        if (User.getCurrentUser().isBlackWhiteGame()) {
            ColorAdjust grayScale = new ColorAdjust();
            grayScale.setSaturation(-1);
            Main.stage.getScene().getRoot().setEffect(grayScale);
        }
    }

    private void setHardlessOfTheGame(GameView gameView) {
        if (User.getCurrentUser().getHardless() == 1) {
            gameView.setRadius(150);
            gameView.setSpeedOfTanks(0.1);
        } else if (User.getCurrentUser().getHardless() == 2) {
            gameView.setRadius(250);
            gameView.setSpeedOfTanks(0.3);
        } else {
            gameView.setRadius(350);
            gameView.setSpeedOfTanks(0.6);
        }
    }

    private void setFocusForGameView(GameView gameView) {
        if (User.getCurrentUser().isUsingArrows()) {
            gameView.bringPauseFocusToArrowKeys();
        } else {
            gameView.bringPauseFocusToWDSA();
        }
    }

    public void pointsMenu(MouseEvent mouseEvent) {
        PointsView pointsView = new PointsView();
        try {
            pointsView.start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void settingMenu(MouseEvent mouseEvent) {
        SettingView settingView = new SettingView();
        try {
            settingView.start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeTheStage(MouseEvent mouseEvent) {
        Main.stage.close();
    }

    public void continueGame(MouseEvent mouseEvent) {

    }
}
