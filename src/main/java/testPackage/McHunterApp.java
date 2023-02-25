package testPackage;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.NotNull;
import testPackage.components.PlayerAnimationComponent;
import testPackage.ui.UpdatedMainMenu;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;

public class McHunterApp extends GameApplication{

    @Override
    protected void onPreInit(){

    }

    @Override
    protected void initSettings(GameSettings settings){

        settings.setWidthFromRatio(0.5);
        settings.setHeightFromRatio(0.5);
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("McDonald's Hunter");
        settings.setVersion("0.1");
        //settings.setDefaultCursor(new CursorInfo("brick.png", 0, 0));
        //settings.getDefaultCursor();
        //settings.getAchievements().add(new Achievement("", "", ));
        //settings.setAppIcon();

        settings.setMainMenuEnabled(true);
//        settings.setSceneFactory(new SceneFactory(){
//            @NotNull
//            @Override
//            public FXGLMenu newMainMenu() {
//                return new UpdatedMainMenu();
//            }
//        });


        //settings.setDeveloperMenuEnabled(true);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Entity player;



    @Override
    protected void initGame() {

        getGameScene().setBackgroundColor(Color.BLACK);

        FXGL.getGameWorld().addEntityFactory(new McHunterFactory());

        player = FXGL.spawn("player", new SpawnData());




        FXGL.entityBuilder()
                .type(McHunterType.COIN)
                .at(500, 200)
                .viewWithBBox(new Circle(15, 15, 15, Color.YELLOW))
                .with(new CollidableComponent(true))
                .buildAndAttach();


        Entity ENEMY = FXGL.spawn("enemy", new SpawnData()

        );

        Viewport viewport = getGameScene().getViewport();
        viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        viewport.setBounds(-800, -1200, 1600, 1200);
        viewport.setLazy(true);
    }

    @Override
    protected void initPhysics() {

    }

    @Override
    protected void initInput() {
        FXGL.getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerAnimationComponent.class).walkRight();
            }
        }, KeyCode.D);

        FXGL.getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerAnimationComponent.class).walkLeft();
            }
        }, KeyCode.A);

        FXGL.getInput().addAction(new UserAction("Up") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerAnimationComponent.class).walkUp();
            }
        }, KeyCode.W);

        FXGL.getInput().addAction(new UserAction("Down") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerAnimationComponent.class).walkDown();
            }
        }, KeyCode.S);

    }
    @Override
    protected void initGameVars(Map<String, Object> vars) {

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


