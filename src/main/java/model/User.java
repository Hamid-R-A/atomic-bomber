package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class User {
    private static ArrayList<User> allUsers = new ArrayList<>();
    private Image image;
    private static User currentUser;
    private String username;
    private String password;
    private int points = 0;
    private int lastWave = 0;
    private double accuracy = 0;
    private int DegreeOfHardless = 0;
    private int pointAccordingToDegreeOfHardless = 0;
    private boolean usingArrows = true;
    private boolean blackWhiteGame = false;
    private int hardless = 1;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }



    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static User giveUserByUsername(String username){
        for (User user : allUsers){
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getLastWave() {
        return lastWave;
    }

    public void setLastWave(int lastWave) {
        this.lastWave = lastWave;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public int getPointAccordingToDegreeOfHardless() {
        return pointAccordingToDegreeOfHardless;
    }

    public void setPointAccordingToDegreeOfHardless(int pointAccordingToDegreeOfHardless) {
        this.pointAccordingToDegreeOfHardless = pointAccordingToDegreeOfHardless;
    }

    public boolean isUsingArrows() {
        return usingArrows;
    }

    public void setUsingArrows(boolean usingArrows) {
        this.usingArrows = usingArrows;
    }

    public boolean isBlackWhiteGame() {
        return blackWhiteGame;
    }

    public void setBlackWhiteGame(boolean blackWhiteGame) {
        this.blackWhiteGame = blackWhiteGame;
    }

    public int getHardless() {
        return hardless;
    }

    public void setHardless(int hardless) {
        this.hardless = hardless;
    }
}
