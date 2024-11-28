package view;

import controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import view.animations.FlyingAnimation;
import view.animations.MigAnimation;
import view.animations.TankAnimation;
import view.animations.TruckAnimation;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends Application {

    private final Game game;
    public static boolean winCheck = false;
    private final double WIDTH = 1000;
    private final double HEIGHT = 700;
    private Button pause;
    private Pane pane;
    private Plane plane;
    private Label numberOfNuclear = new Label();
    private Label numberOfKills = new Label();
    private Label numberOfHp = new Label();
    private Label numberOfClusters = new Label();
    private Label waveNumber = new Label();
    private Random random = new Random();
    private double radius = 150;
    private double speedOfTanks;
    private Timer timerForCreatingTankAndTruck;
    private ImageView circleOfTank;
    private int missileUseInWave1;
    private int missileUseInWave2;
    private int missileUseInWave3;
    private int numberOfAllGoals;
    private int killsToKnowToGoToNextWave;
    private Label accuracyShow = new Label();


    public GameView() {
//        this.settingView = settingView;
        this.game = new Game(this);
        waveNumber.setText("wave1");
        numberOfHp.setText("1");
        missileUseInWave1 = 0;
        missileUseInWave2 = 0;
        missileUseInWave3 = 0;
        numberOfAllGoals = 7;
        killsToKnowToGoToNextWave = 0;
        accuracyShow.setPrefWidth(100);
        accuracyShow.setPrefHeight(20);
        accuracyShow.setLayoutX(500);
        accuracyShow.setLayoutY(100);
    }


    @Override
    public void start(Stage stage) throws Exception {
        pause = new Button("pause");
        pause.setLayoutX(10);
        pause.setLayoutY(10);
        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                User.getCurrentUser().setLastWave(Integer.parseInt(waveNumber.getText().substring(4)));
                User.getCurrentUser().setPoints(game.getGameView().getNumberOfKills());
                User.getCurrentUser().setPointAccordingToDegreeOfHardless(game.getGameView().getNumberOfKills() * User.getCurrentUser().getHardless());
                if (game.getGameView().getMissileUseInWave1() + game.getGameView().getMissileUseInWave2() + game.getGameView().getMissileUseInWave3() != 0) {
                    User.getCurrentUser().setAccuracy((double) (game.getGameView().getKillsToKnowToGoToNextWave() * 100)
                            / (game.getGameView().getMissileUseInWave1() + game.getGameView().getMissileUseInWave2() + game.getGameView().getMissileUseInWave3()));
                }
                try {
                    new PauseView().start(Main.stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //set to don't focus on pause by SPACE key
        pause.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) {
                    event.consume();
                    GameController.shoot(pane, plane, game);
                }
            }
        });
        pane = new Pane();
        pane.getChildren().add(pause);
        setSize(pane);
        createPlane();
        createIconsAndInitialNumbers();
        pane.setBackground(new Background(createBackGroundImage()));
        //
        firstWave();
        //
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        FlyingAnimation flyingAnimation = new FlyingAnimation(game, pane, plane, pause);
        flyingAnimation.play();
    }

    private void firstWave() {
        showTheWave("wave1");
        pane.getChildren().add(waveNumber);
        createApartment();
        createTrench();
        createTankAndTruck(speedOfTanks, 3);
        createTree();
        checkToStartSecondWave();
    }

    private void checkToStartSecondWave() {
        if (missileUseInWave1 != 0) {
            accuracyShow.setText("accuracy: " + (killsToKnowToGoToNextWave * 100 / missileUseInWave1));
        } else {
            accuracyShow.setText("0");
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int count = 0;
            @Override
            public void run() {
                if (killsToKnowToGoToNextWave == numberOfAllGoals) {
                    if (count == 4) {
                        Platform.runLater(() -> {
                            secondWave();
                            accuracyShow.setText("");
                        });
                        timer.cancel();
                    } else {
                        if (!pane.getChildren().contains(accuracyShow)) {
                            Platform.runLater(() -> {
                                if (missileUseInWave1 != 0) {
                                    accuracyShow.setText("accuracy: " + (killsToKnowToGoToNextWave * 100 / missileUseInWave1));
                                }
                                pane.getChildren().add(accuracyShow);
                            });
                        }
                        count++;
                    }
                }
            }
        }, 0, 1000);
    }

    public void secondWave() {
        deleteNodes();
        showTheWave("wave2");
        createTankAndTruck(speedOfTanks, 2);
        createBulletTank(speedOfTanks);
        createApartment();
        createTree();
        createTrench();
        checkToStartThirdWave();
    }

    private void checkToStartThirdWave() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int count = 0;
            @Override
            public void run() {
                if (killsToKnowToGoToNextWave - 7 == numberOfAllGoals) {
                    if (count == 4) {
                        Platform.runLater(() -> {
                            thirdWave();
                            accuracyShow.setText("");
                        });
                        timer.cancel();
                    } else {
                        if (!pane.getChildren().contains(accuracyShow)) {
                            Platform.runLater(() -> {
                                pane.getChildren().add(accuracyShow);
                            });
                        }
                        Platform.runLater(() -> {
                            accuracyShow.setText("accuracy: " + ((killsToKnowToGoToNextWave - 7) * 100 / missileUseInWave2));
                        });
                        count++;
                    }
                }
            }
        }, 0, 1000);
    }

    private void thirdWave() {
        showTheWave("wave3");
        createTankAndTruck(speedOfTanks, 3);
        createApartment();
        createTree();
        createTrench();
        createMig(radius);
        checkToFinishTheGame();
    }

    private void checkToFinishTheGame() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int count = 0;
            @Override
            public void run() {
                if (numberOfHp.getText().equals("0") || killsToKnowToGoToNextWave - 14 == numberOfAllGoals) {
                    if (killsToKnowToGoToNextWave - 14 == numberOfAllGoals){
                        winCheck = true;
                    }
                    if (count == 2) {
                        Platform.runLater(() -> {
                            LastControll lastControll = new LastControll();
                            try {
                                lastControll.start(Main.stage);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                    } else {
                        Platform.runLater(() -> {
                            accuracyShow.setText("last accuracy: " + (killsToKnowToGoToNextWave * 100 / (missileUseInWave1 + missileUseInWave2 + missileUseInWave3)));
                        });
                        User.getCurrentUser().setLastWave(3);
                        User.getCurrentUser().setAccuracy((killsToKnowToGoToNextWave * 100) / (missileUseInWave1 + missileUseInWave2 + missileUseInWave3));
                        User.getCurrentUser().setPoints(Integer.parseInt(numberOfKills.getText()));
                        User.getCurrentUser().setPointAccordingToDegreeOfHardless(game.getGameView().getNumberOfKills() * User.getCurrentUser().getHardless());
                    }
                    count++;
                }
            }
        }, 0, 1000);
    }

    private void createBulletTank(double speedOfTanks) {
        Tank tank = new Tank(random.nextDouble(game.getWIDTH()), 440, true);
        this.game.getTanksOfTheGame().add(tank);
        pane.getChildren().add(tank);
        tank.setTankAnimation(new TankAnimation(plane, pane, game, tank));
        tank.getTankAnimation().setSpeed(speedOfTanks);
        ////
        ImageView imageView1 = new ImageView(new Image(GameView.class.getResource("/images/h.png").toExternalForm()));
        circleOfTank = imageView1;
        imageView1.setFitWidth(radius);
        imageView1.setFitHeight(radius);
        imageView1.setX(tank.getX() - imageView1.getFitWidth() / 2 + 20);
        imageView1.setY(tank.getY() - imageView1.getFitHeight() / 2 + 15);
        pane.getChildren().add(imageView1);
        tank.getTankAnimation().setCircle(imageView1);
        tank.getTankAnimation().play();

    }

    public int getNumberOfNuclear() {
        return Integer.parseInt(numberOfNuclear.getText());
    }

    public void setNumberOfNuclear(int numberOfNuclear) {
        this.numberOfNuclear.setText(String.valueOf(numberOfNuclear));
    }

    public int getNumberOfKills() {
        return Integer.parseInt(numberOfKills.getText());
    }

    public void setNumberOfKills(int numberOfKills) {
        this.numberOfKills.setText(String.valueOf(numberOfKills));
    }

    public int getNumberOfHp() {
        return Integer.parseInt(numberOfHp.getText());
    }

    public void setNumberOfHp(int numberOfHp) {
        this.numberOfHp.setText(String.valueOf(numberOfHp));
    }

    public int getNumberOfClusters() {
        return Integer.parseInt(numberOfClusters.getText());
    }

    public void setNumberOfClusters(int numberOfClusters) {
        this.numberOfClusters.setText(String.valueOf(numberOfClusters));
    }

    private void showTheWave(String wave) {
        waveNumber.setText(wave);
        waveNumber.setLayoutX(game.getWIDTH() / 2);
        waveNumber.setLayoutY(40);
        waveNumber.setTextFill(Paint.valueOf("#891b1b"));
    }


    private void createIconsAndInitialNumbers() {
        ImageView imageView = new ImageView(new Image(GameView.class.getResource("/assets/scoreicon.png").toExternalForm()));
        imageView.setLayoutX(130);
        imageView.setLayoutY(10);
        pane.getChildren().add(imageView);
        //========================
        imageView = new ImageView(new Image(GameView.class.getResource("/assets/nukeicon.png").toExternalForm()));
        imageView.setLayoutX(70);
        imageView.setLayoutY(10);
        pane.getChildren().add(imageView);
        //=======================
        imageView = new ImageView(new Image(GameView.class.getResource("/assets/bonuscluster.png").toExternalForm()));
        imageView.setLayoutX(190);
        imageView.setLayoutY(12);
        pane.getChildren().add(imageView);
        //======================
        numberOfNuclear = new Label("0");
        numberOfNuclear.setLayoutX(110);
        numberOfNuclear.setLayoutY(10);
        numberOfNuclear.setFont(Font.font(20));
        //.
        numberOfHp = new Label("2");
        numberOfHp.setLayoutX(160);
        numberOfHp.setLayoutY(18);
        numberOfHp.setFont(Font.font(20));
        //.
        numberOfKills = new Label("0");
        numberOfKills.setLayoutX(160);
        numberOfKills.setLayoutY(3);
        numberOfKills.setFont(Font.font(20));
        //.
        numberOfClusters = new Label("0");
        numberOfClusters.setLayoutX(220);
        numberOfClusters.setLayoutY(15);
        numberOfClusters.setFont(Font.font(20));
        pane.getChildren().addAll(numberOfHp, numberOfKills, numberOfNuclear, numberOfClusters);
    }

    private void createTree() {
        Tree tree = new Tree(600, 410);
        this.game.setTreeOfTheGame(tree);
        pane.getChildren().add(tree);
        game.setTreeOfTheGame(tree);
    }

    private void createApartment() {
        Apartment apartment = new Apartment(450, 410);
        this.game.setApartmentOfTheGame(apartment);
        pane.getChildren().add(apartment);
        game.setApartmentOfTheGame(apartment);
    }

    private void createTrench() {
        Trench trench = new Trench(150, 410);
        this.game.setTrenchOfTheGame(trench);
        pane.getChildren().add(trench);
        game.setTrenchOfTheGame(trench);
    }

    private void createPlane() {
        plane = new Plane(game);
        pane.getChildren().add(plane);
        pause.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.UP) {
                GameController.rotateUp(plane);
            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                GameController.rotateRight(plane);
            } else if (keyEvent.getCode() == KeyCode.LEFT) {
                GameController.rotateLeft(plane);
            } else if (keyEvent.getCode() == KeyCode.DOWN) {
                GameController.rotateDown(plane);
            } else if (keyEvent.getCode() == KeyCode.SPACE) {
                GameController.shoot(pane, plane, game);
            } else if (keyEvent.getCode() == KeyCode.R && !numberOfNuclear.getText().equals("0")) {
                GameController.shootNuclear(pane, plane, game);
                numberOfNuclear.setText(String.valueOf(Integer.parseInt(numberOfNuclear.getText()) - 1));
            } else if (keyEvent.getCode() == KeyCode.C && !numberOfClusters.getText().equals("0")) {
                GameController.shootCluster(pane, plane, game);
                numberOfClusters.setText(String.valueOf(Integer.parseInt(numberOfClusters.getText()) - 1));
            } else if (keyEvent.getCode() == KeyCode.T) {
                createRandomTank();
            } else if (keyEvent.getCode() == KeyCode.G) {
                setNumberOfNuclear(getNumberOfNuclear() + 1);
            } else if (keyEvent.getCode() == KeyCode.CONTROL) {
                setNumberOfClusters(getNumberOfClusters() + 1);
            } else if (keyEvent.getCode() == KeyCode.P) {
                timerForCreatingTankAndTruck.cancel();
                if (waveNumber.getText().substring(4).equals("1")) {
                    deleteNodes();
                    secondWave();
                } else if (waveNumber.getText().substring(4).equals("2")) {
                    deleteNodes();
                    thirdWave();
                }
            }
        });
    }

    public void increaseNumberOfUsedMissile() {
        if (waveNumber.getText().equals("wave1")) {
            missileUseInWave1++;
        } else if (waveNumber.getText().equals("wave2")) {
            missileUseInWave2++;
        } else {
            missileUseInWave3++;
        }

    }

    private void deleteNodes() {
        ArrayList<Node> nodesToRemove = new ArrayList<>();
        for (Node node : pane.getChildren()) {
            if (node instanceof Tank || node instanceof Tree || node instanceof Trench || node instanceof Truck || node instanceof Apartment) {
                nodesToRemove.add(node);
            }
        }

        if (waveNumber.getText().equals("wave2")) {
            if (circleOfTank != null) {
                nodesToRemove.add(circleOfTank);
            }
        }
        pane.getChildren().removeAll(nodesToRemove);
    }

    private void createRandomTank() {
        Tank tank = new Tank(random.nextDouble(game.getWIDTH()), 450, false);
        game.getTanksOfTheGame().add(tank);
        pane.getChildren().add(tank);
        numberOfAllGoals++;
        tank.setTankAnimation(new TankAnimation(plane, pane, game, tank));
        tank.getTankAnimation().setSpeed(speedOfTanks);
        tank.getTankAnimation().play();
    }

    private void createTankAndTruck(double speedOfTanks, int numberOfTanks) {
        Timer timer = new Timer();
        this.timerForCreatingTankAndTruck = timer;
        timer.schedule(new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                count++;
//                Tank tank = new Tank(random.nextDouble(game.getWIDTH()), game.getHEIGHT() - 30, false);
                Tank tank = new Tank(0, 440, false);
                game.getTanksOfTheGame().add(tank);
                Platform.runLater(() -> {
                    pane.getChildren().add(tank);
                });
                tank.setTankAnimation(new TankAnimation(plane, pane, game, tank));
                tank.getTankAnimation().setSpeed(speedOfTanks);
                tank.getTankAnimation().play();
                if (count == 2) {
                    Truck truck = new Truck(game.getWIDTH(), 440);
                    game.setTruckOfTheGame(truck);
                    Platform.runLater(() -> {
                        pane.getChildren().add(truck);
                    });
                    truck.setTruckAnimation(new TruckAnimation(pane, game, truck));
                    truck.getTruckAnimation().play();
                    truck.getTruckAnimation().setSpeed(speedOfTanks);
                    game.setTruckOfTheGame(truck);
                }
                if (count == numberOfTanks) {
                    timer.cancel();
                }
            }
        }, 0, 6000);
    }

    private void createMig(double radius) {

        Mig mig = new Mig(game);
        mig.setRadius(radius);
        ImageView warning = new ImageView(new Image(GameView.class.getResource("/assets/migwarning.png").toExternalForm()));
        warning.setX(game.getWIDTH() / 2);
        warning.setY(game.getHEIGHT() / 2);
        pane.getChildren().add(warning);
        //======================= create circle around mig
        ImageView circle = new ImageView(new Image(GameView.class.getResource("/images/h.png").toExternalForm()));
        circle.setFitWidth(radius);
        circle.setFitHeight(radius);
        circle.setX(mig.getX() - circle.getFitWidth() / 2 + 20);
        circle.setY(mig.getY() - circle.getFitHeight() / 2 + 15);
        pane.getChildren().add(circle);
        pane.getChildren().add(mig);
        MigAnimation migAnimation = new MigAnimation(game, mig, pane, plane, circle);
        migAnimation.play();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (!pane.getChildren().contains(warning)) {
                        pane.getChildren().add(warning);
                    }
                });
            }
        }, 0, 10000);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    pane.getChildren().remove(warning);
                });
                mig.setX(-200);
                circle.setX(mig.getX() - circle.getFitWidth() / 2 + 20);
            }
        }, 0, 15000);

    }
    private BackgroundImage createBackGroundImage() {
        Image image = new Image(Main.class.getResource("/images/sky.jpg").toExternalForm(), WIDTH, HEIGHT, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        return backgroundImage;
    }
    private void setSize(Pane pane) {
        pane.setMinHeight(HEIGHT);
        pane.setMaxHeight(HEIGHT);
        pane.setMinWidth(WIDTH);
        pane.setMaxWidth(WIDTH);
        Main.stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - Main.stage.getWidth() - 400) / 2);
        Main.stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - Main.stage.getHeight() - 300) / 2);
    }
    public void bringPauseFocusToArrowKeys() {
        pause.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.UP) {
                GameController.rotateUp(plane);
            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                GameController.rotateRight(plane);
            } else if (keyEvent.getCode() == KeyCode.LEFT) {
                GameController.rotateLeft(plane);
            } else if (keyEvent.getCode() == KeyCode.DOWN) {
                GameController.rotateDown(plane);
            } else if (keyEvent.getCode() == KeyCode.W) {
            } else if (keyEvent.getCode() == KeyCode.D) {
            } else if (keyEvent.getCode() == KeyCode.A) {
            } else if (keyEvent.getCode() == KeyCode.S) {
            } else if (keyEvent.getCode() == KeyCode.R && !numberOfNuclear.getText().equals("0")) {
                GameController.shootNuclear(pane, plane, game);
                numberOfNuclear.setText(String.valueOf(Integer.parseInt(numberOfNuclear.getText()) - 1));
            } else if (keyEvent.getCode() == KeyCode.C && !numberOfClusters.getText().equals("0")) {
                GameController.shootCluster(pane, plane, game);
                numberOfClusters.setText(String.valueOf(Integer.parseInt(numberOfClusters.getText()) - 1));
            } else if (keyEvent.getCode() == KeyCode.T) {
                createRandomTank();
            } else if (keyEvent.getCode() == KeyCode.G) {
                setNumberOfNuclear(getNumberOfNuclear() + 1);
            } else if (keyEvent.getCode() == KeyCode.CONTROL) {
                setNumberOfClusters(getNumberOfClusters() + 1);
            } else if (keyEvent.getCode() == KeyCode.H) {
                setNumberOfHp(getNumberOfHp() + 1);
            } else if (keyEvent.getCode() == KeyCode.P) {
                timerForCreatingTankAndTruck.cancel();
                if (waveNumber.getText().substring(4).equals("1")) {
                    deleteNodes();
                    secondWave();
                } else if (waveNumber.getText().substring(4).equals("2")) {
                    deleteNodes();
                    thirdWave();
                }
            }
        });
    }
    public void bringPauseFocusToWDSA() {
        pause.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.UP) {
            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
            } else if (keyEvent.getCode() == KeyCode.LEFT) {
            } else if (keyEvent.getCode() == KeyCode.DOWN) {
            } else if (keyEvent.getCode() == KeyCode.W) {
                GameController.rotateUp(plane);
            } else if (keyEvent.getCode() == KeyCode.D) {
                GameController.rotateRight(plane);
            } else if (keyEvent.getCode() == KeyCode.A) {
                GameController.rotateLeft(plane);
            } else if (keyEvent.getCode() == KeyCode.S) {
                GameController.rotateDown(plane);
            } else if (keyEvent.getCode() == KeyCode.R && !numberOfNuclear.getText().equals("0")) {
                GameController.shootNuclear(pane, plane, game);
                numberOfNuclear.setText(String.valueOf(Integer.parseInt(numberOfNuclear.getText()) - 1));
            } else if (keyEvent.getCode() == KeyCode.C && !numberOfClusters.getText().equals("0")) {
                GameController.shootCluster(pane, plane, game);
                numberOfClusters.setText(String.valueOf(Integer.parseInt(numberOfClusters.getText()) - 1));
            } else if (keyEvent.getCode() == KeyCode.T) {
                createRandomTank();
            } else if (keyEvent.getCode() == KeyCode.G) {
                setNumberOfNuclear(getNumberOfNuclear() + 1);
            } else if (keyEvent.getCode() == KeyCode.CONTROL) {
                setNumberOfClusters(getNumberOfClusters() + 1);
            } else if (keyEvent.getCode() == KeyCode.P) {
                timerForCreatingTankAndTruck.cancel();
                if (waveNumber.getText().substring(4).equals("1")) {
                    deleteNodes();
                    secondWave();
                } else if (waveNumber.getText().substring(4).equals("2")) {
                    deleteNodes();
                    thirdWave();
                }
            }
        });

    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }


    public void setSpeedOfTanks(double speedOfTanks) {
        this.speedOfTanks = speedOfTanks;
    }

    public int getKillsToKnowToGoToNextWave() {
        return killsToKnowToGoToNextWave;
    }

    public void setKillsToKnowToGoToNextWave(int addKillsForKnowToGoToNextWave) {
        this.killsToKnowToGoToNextWave = addKillsForKnowToGoToNextWave;
    }

    public ImageView getCircleOfTank() {
        return circleOfTank;
    }

    public Label getWaveNumber() {
        return waveNumber;
    }

    public int getMissileUseInWave1() {
        return missileUseInWave1;
    }

    public int getMissileUseInWave2() {
        return missileUseInWave2;
    }

    public int getMissileUseInWave3() {
        return missileUseInWave3;
    }
}
