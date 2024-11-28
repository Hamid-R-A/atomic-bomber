package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.User;

import java.io.File;
import java.util.Random;

public class RegisterMenuView {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    private static MediaPlayer music1;
    private static MediaPlayer music2;
    private static MediaPlayer music3;
    private static GameView gameView = new GameView();


    public static MediaPlayer getCurrentMusic() {
        return currentMusic;
    }

    public static void setCurrentMusic(MediaPlayer currentMusic) {
        RegisterMenuView.currentMusic = currentMusic;
    }

    private static MediaPlayer currentMusic = music1;


    public void register(MouseEvent mouseEvent) {
        if (username.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("username section is empty!");
            alert.setHeaderText("invalid username");
            alert.setContentText("enter a username");
            alert.show();
        } else if (password.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("password section is empty!");
            alert.setHeaderText("invalid password");
            alert.setContentText("enter a password");
            alert.show();
        } else if (User.giveUserByUsername(username.getText()) != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalid username");
            alert.setHeaderText("invalid username");
            alert.setContentText("this username already given");
            alert.show();
        } else {
            User.getAllUsers().add(new User(username.getText(), password.getText()));
            User.setCurrentUser(User.giveUserByUsername(username.getText()));
            randomAvatar();
            username.setText("");
            ProfileMenuView profileMenuView = new ProfileMenuView();
            try {
                profileMenuView.start(Main.stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void login(MouseEvent mouseEvent) {
        if (username.getText().isEmpty()) {
            alertForUsernameEmpty();
        } else if (password.getText().isEmpty()) {
            alertForPasswordEmpty();
        } else if (User.giveUserByUsername(username.getText()) == null) {
            alertForUsernameLack();
        } else if (User.giveUserByUsername(username.getText()) != null
                && !User.giveUserByUsername(username.getText()).getPassword().equals(password.getText())) {
            alertForPasswordWrong();
        } else {
            User.setCurrentUser(User.giveUserByUsername(username.getText()));
            ProfileMenuView profileMenuView = new ProfileMenuView();
            try {
                profileMenuView.start(Main.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void alertForPasswordWrong() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("invalid password");
        alert.setHeaderText("invalid password");
        alert.setContentText("password is wrong");
        alert.show();
    }

    private void alertForUsernameLack() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("invalid username");
        alert.setHeaderText("invalid username");
        alert.setContentText("no such user");
        alert.show();
    }

    private void alertForPasswordEmpty() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("password section is empty!");
        alert.setHeaderText("invalid password");
        alert.setContentText("enter a password");
        alert.show();
    }

    private void alertForUsernameEmpty() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("username section is empty!");
        alert.setHeaderText("invalid username");
        alert.setContentText("enter a username");
        alert.show();
    }

    public void putSound(){
//        if (currentMusic == null) {
//            String musicFile = "C:\\Users\\ASUS\\Desktop\\اهنگ\\along-the-wayside-medieval-folk-music-128697.mp3";
//            music1 = new MediaPlayer(new Media(new File(musicFile).toURI().toString()));
//            musicFile = "C:\\Users\\ASUS\\Desktop\\اهنگ\\Shadmehr Aghili – Mitarsam[320].mp3";
//            music2 = new MediaPlayer(new Media(new File(musicFile).toURI().toString()));
//            musicFile = "C:\\Users\\ASUS\\Desktop\\اهنگ\\Hamid Askari – Baroon128 (UpMusic).mp3";
//            music3 = new MediaPlayer(new Media(new File(musicFile).toURI().toString()));
//            currentMusic = music1;
//            currentMusic.play();
//        }
    }


    public void openGame(MouseEvent mouseEvent) {
        try {
            User user = new User("guest", "111");
            User.setCurrentUser(user);
            GameView gameView = new GameView();
            gameView.setSpeedOfTanks(0.1);
            gameView.start(Main.stage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void randomAvatar(){
            Random rand = new Random();
            int randomNumber = rand.nextInt(3);
            switch (randomNumber) {
                case 0:
                    User.getCurrentUser().setImage(new Image(RegisterMenuView.class.getResource("/images/Arsenal.png").toExternalForm()));
                    break;
                case 1:
                    User.getCurrentUser().setImage(new Image(RegisterMenuView.class.getResource("/images/Liverpool.png").toExternalForm()));
                    break;
                case 2:
                    User.getCurrentUser().setImage(new Image(RegisterMenuView.class.getResource("/images/Manchester.png").toExternalForm()));
                    break;
            }
    }

    public static MediaPlayer getMusic1() {
        return music1;
    }

    public static MediaPlayer getMusic2() {
        return music2;
    }

    public static MediaPlayer getMusic3() {
        return music3;
    }

}
