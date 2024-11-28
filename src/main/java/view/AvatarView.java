package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class AvatarView extends Application {

    public static ImageView arsenal;
    public static ImageView liverpool;
    public static ImageView manchester;
    public ImageView myChosenAvatar;

    Stage stage = Main.stage;

//    public static void main(String[] args) {
//        launch(args);
//    }

    @FXML
    public void initialize() {
        myChosenAvatar.setImage(User.getCurrentUser().getImage());
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = Main.class.getResource("/FXML/AvatarScene.fxml");
        Pane root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void bringToMyAvatar(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        myChosenAvatar.setImage(imageView.getImage());
        myChosenAvatar.setLayoutX((stage.getWidth() / 2) - 100);
        myChosenAvatar.setLayoutY((stage.getHeight() / 2) - 100);
        User.getCurrentUser().setImage(myChosenAvatar.getImage());
    }

    public void chooseImageFromComputer(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            myChosenAvatar.setImage(image);
            myChosenAvatar.setLayoutX((stage.getWidth() / 2) - 100);
            myChosenAvatar.setLayoutY((stage.getHeight() / 2) - 120);
            User.getCurrentUser().setImage(myChosenAvatar.getImage());
        }
    }

    public void backToProfile(MouseEvent mouseEvent) {
        ProfileMenuView profileMenuView = new ProfileMenuView();
        try {
            profileMenuView.start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dragOver(DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();
        if (dragboard.hasFiles() || dragboard.hasImage()) {
            try {
                myChosenAvatar.setImage(new Image(new FileInputStream(dragboard.getFiles().get(0))));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void dragDropped(DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();
        if (dragboard.hasFiles() || dragboard.hasImage()) {
            dragEvent.acceptTransferModes(TransferMode.COPY);
        }
    }
}
