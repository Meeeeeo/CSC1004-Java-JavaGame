package testPackage;

import javafx.geometry.Point2D;

public interface Config {
    int PLAYER_SPEED = 48*5; // pixels per second

    int BULLET_SPEED = 1000;

    double SHOOT_DELAY = 0.1; // seconds

    double BULLET_ACCURACY = 0.1; // On [0.0, 0.1], the lower, the more accurate.

    int CELL_SIZE = 48; // Tile setting
}
