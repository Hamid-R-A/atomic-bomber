package view.animations;

import controller.GameController;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Game;
import model.Missile;
import model.Plane;
import model.Tank;
import view.Main;

import java.util.Timer;
import java.util.TimerTask;

public class TankAnimation extends Transition {
    private boolean flag;
    private final Plane plane;
    private final Tank tank;
    private final Game game;
    private final Pane pane;
    private double speed = 0.1;
    private ImageView circle;
    private final int duration = 100;

    public TankAnimation(Plane plane, Pane pane, Game game, Tank tank) {
        this.plane = plane;
        this.pane = pane;
        this.game = game;
        this.tank = tank;
        this.setCycleDuration(Duration.millis(duration));
        this.setCycleCount(-1);
        flag = true;
    }

    @Override
    protected void interpolate(double v) {
        double x = tank.getX() + speed;
        if (tank.getX() >= game.getWIDTH() && speed > 0) {
            speed = -speed;
            x = tank.getX() + speed;
            tank.setScaleX(-1);
        } else if (tank.getX() <= 0 && speed < 0) {
            speed = -speed;
            x = tank.getX() + speed;
            tank.setScaleX(1);
        }
        tank.setX(x);
        if (tank.isBullet()){
            circle.setX(circle.getX() + speed);
        }
        if (tank.isBullet() && Math.sqrt(Math.pow(plane.getY() - tank.getY(), 2) + Math.pow(plane.getX() - tank.getX(), 2)) < game.getGameView().getRadius() / 2 && flag){
            GameController.throwBulletTank(tank, pane, game, plane);
            flag = false;
            putTimeDelayAfterShooting();
        }
    }

    private void putTimeDelayAfterShooting() {
        Timer timer = new Timer();
     timer.schedule(new TimerTask() {
         @Override
         public void run() {
             timer.cancel();
             flag = true;
         }
     }, 2000);
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public void setCircle(ImageView circle) {
        this.circle = circle;
    }
}
