package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

import java.net.URL;

public class ProfileMenuView extends Application {
    public Label confirmChangingLabel;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField newUsername;

    @Override
    public void start(Stage stage) throws Exception {
        URL url = Main.class.getResource("/FXML/ProfileScene.fxml");
        Pane root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changingNameAndPassword(MouseEvent mouseEvent) {
        User.getCurrentUser().setUsername(newUsername.getText());
        User.getCurrentUser().setPassword(newPassword.getText());
//        System.out.println(User.getCurrentUser().getPassword()+ "  " + User.getCurrentUser().getUsername());
        newUsername.setText("");
        newPassword.setText("");
        confirmChangingLabel.setText("your name changed successfully");
    }

    public void deleteAccount(MouseEvent mouseEvent) {
        Main main = new Main();
        try {
            main.start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        User.getAllUsers().remove(User.getCurrentUser());

    }

    public void goToRegisterMenu(MouseEvent mouseEvent) {
        Main main = new Main();
        try {
            main.start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToAvatarMenu(MouseEvent mouseEvent) {
        AvatarView avatarView = new AvatarView();
        try {
            avatarView.start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goingToMainMenu(MouseEvent mouseEvent) {
        MenuView menuView = new MenuView();
        try {
            menuView.start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
