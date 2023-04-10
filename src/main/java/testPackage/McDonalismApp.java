package testPackage;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import testPackage.components.PlayerMoveComponent;
import testPackage.components.ShootComponent;
import testPackage.ui.MainMenu;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;

public class McDonalismApp extends GameApplication{

    @Override
    protected void onPreInit(){

    }

    @Override
    protected void initSettings(GameSettings settings){

        settings.setManualResizeEnabled(true);
        settings.setPreserveResizeRatio(true); // make it resizable

        settings.setWidth(800);
        settings.setHeight(800);
        settings.setTitle("McDonalism");
        settings.setVersion("0.1");
//        settings.setDefaultCursor(new CursorInfo("brick.png", 0, 0));
//        settings.getDefaultCursor();
//        settings.getAchievements().add(new Achievement("", "", ));
//        settings.setAppIcon();

        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory(){
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenu();
            }
        });


        //settings.setDeveloperMenuEnabled(true);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Entity player1;

    public Entity getPlayer1(){
        return player1;
    }

    private Point2D dir = new Point2D(0,0);
    private final Point2D up = new Point2D(0,-1);
    private final Point2D down = new Point2D(0,1);
    private final Point2D left = new Point2D(-1,0);
    private final Point2D right = new Point2D(1,0);


    @Override
    protected void initGame() {

        getGameScene().setBackgroundColor(Color.BLACK);

        FXGL.loopBGM("Crypteque1.mp3");

        FXGL.getGameWorld().addEntityFactory(new McDonalismFactory());

        FXGL.setLevelFromMap("TestLevel.tmx");

        player1 = FXGL.spawn("player1");

        run(() -> FXGL.spawn("enemy1", FXGL.random(0, 800), FXGL.random(0, 800)), Duration.seconds(1));


        Viewport viewport = getGameScene().getViewport();
        viewport.bindToEntity(player1, getAppWidth() / 2, getAppHeight() / 2);
        viewport.setBounds(-400, -400, 1200, 1200);
        viewport.unbind();




        player1.getComponent(PlayerMoveComponent.class).move(dir);
    }

    @Override
    protected void initPhysics() {
        onCollision(McDonalismType.PLAYER, McDonalismType.ENEMY, (player, enemy) ->
        {
//            enemy.removeFromWorld();
        });

        onCollision(McDonalismType.BULLET, McDonalismType.ENEMY, (bullet, enemy) ->
        {
            bullet.removeFromWorld();
            enemy.removeFromWorld();
        });
    }

    @Override
    protected void onUpdate(double tpf) {

            player1.getComponent(PlayerMoveComponent.class).move(dir);
    }

    @Override
    protected void initInput() {

//        onKey(KeyCode.W, () -> player1.translateY(-1));
//        onKey(KeyCode.S, () -> player1.translateY(1));
//        onKey(KeyCode.A, () -> player1.translateX(-1));
//        onKey(KeyCode.D, () -> player1.translateX(1));

        FXGL.getInput().addAction(new UserAction("Up") {
            @Override
            protected void onActionBegin() {
                dir = dir.add(up) ;
            }
            @Override
            protected void onActionEnd() {
                dir = dir.subtract(up);
            }
        }, KeyCode.W);
        FXGL.getInput().addAction(new UserAction("Down") {
            @Override
            protected void onActionBegin() {
                dir = dir.add(down) ;
            }
            @Override
            protected void onActionEnd() {
                dir = dir.subtract(down);
            }
        }, KeyCode.S);
        FXGL.getInput().addAction(new UserAction("Left") {
            @Override
            protected void onActionBegin() {
                dir = dir.add(left) ;
            }
            @Override
            protected void onActionEnd() {
                dir = dir.subtract(left);
                System.out.println(getPlayer1().getPosition3D());
                System.out.println(getPlayer1().getZIndex());
                System.out.println();

            }

        }, KeyCode.A);
        FXGL.getInput().addAction(new UserAction("Right") {
            @Override
            protected void onActionBegin() {
                dir = dir.add(right) ;
            }
            @Override
            protected void onActionEnd() {
                dir = dir.subtract(right);
            }
        }, KeyCode.D);


        FXGL.getInput().addAction(new UserAction("Shoot") {
            @Override
            protected void onAction() {
                player1.getComponent(ShootComponent.class).shoot(getInput().getMousePositionWorld());
            }
        }, MouseButton.PRIMARY);



    }
    @Override
    protected void initGameVars(Map<String, Object> vars) {

    }

    @Override
    protected void initUI(){

    }



    /*
    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100
        textPixels.textProperty().bind(FXGL.getWorldProperties().intProperty("pixelsMoved").asString());
        FXGL.getGameScene().addUINode(textPixels); // add to the scene graph

        var brickTexture = FXGL.getAssetLoader().loadTexture("brick.png");
        brickTexture.setTranslateX(50);
        brickTexture.setTranslateY(450);
        FXGL.getGameScene().addUINode(brickTexture);
    }

     */


}


