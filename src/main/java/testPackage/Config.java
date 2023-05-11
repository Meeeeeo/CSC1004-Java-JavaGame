package testPackage;

public interface Config {

    String DEVELOPER_ACCOUNT = "114514";
    String DEVELOPER_PASSWORD = "1919180";

    int M_HP = 60;
    int M_SPEED = 48*5; // pixels per second
    int BULLET_DAMAGE = 1;
    int BULLET_SPEED = 2000; // pixels per second
    double SHOOT_DELAY = 0.09; // seconds
    double BULLET_ACCURACY = 0.05; // On [0.0, 0.1], the lower, the more accurate.
    int K_HP = 100;
    int K_DAMAGE = 4;
    int K_SPEED = 48*6;
    int W_HP = 60;
    int W_DAMAGE = 4;
    int W_SPEED = 48*6;
    int ENEMY1_HP = 8;
    int ENEMY1_SPEED = 48*2;
    int ENEMY1_DAMAGE = 4;

    int CELL_SIZE = 48; // Tile size
}
