package testPackage;

import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.*;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.time.LocalTimer;
import com.almasb.fxgl.time.TimerAction;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import testPackage.collisions.*;
import testPackage.components.*;
import testPackage.ui.M_FailedScene;
import testPackage.ui.M_LoadingScene;
import testPackage.ui.M_MainMenu;
import testPackage.ui.M_StartupScene;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;

public class McDonalismApp extends GameApplication{

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
        vars.put("mp", 0);
        vars.put("mp1", 0);
        vars.put("mp2", 0);
        vars.put("isAlive", true);
        vars.put("isAlive1", true);
        vars.put("isAlive2", true);
    }

    @Override
    protected void onPreInit(){

        getAssetLoader().loadMusic("Crypteque1.mp3");
        getAssetLoader().loadSound("gunfire.wav");
        getAssetLoader().loadSound("M_hurt.wav");
        getAssetLoader().loadSound("M_die.wav");
        getAssetLoader().loadSound("W_hurt.wav");
        getAssetLoader().loadSound("W_die.wav");
        getAssetLoader().loadSound("K_hurt.wav");
        getAssetLoader().loadSound("K_die.wav");
        getAssetLoader().loadSound("enemy_die.wav");
        getAssetLoader().loadSound("hover.wav");
        getAssetLoader().loadSound("confirm.wav");

        //load assets when initializing
    }

    @Override
    protected void initSettings(GameSettings settings){

        settings.setDeveloperMenuEnabled(true);

        CursorInfo cursor = new CursorInfo("ui/cursor.png",10,10);
        settings.setDefaultCursor(cursor);
        settings.setManualResizeEnabled(true);
        settings.setPreserveResizeRatio(true);
        settings.setWidth(720);
        settings.setHeight(540);
        settings.setTitle("McDonalism");
        settings.setVersion("0.8");
        settings.setFontUI("font.ttf");
        settings.setFontGame("font.ttf");
        settings.setFontMono("font.ttf");
        settings.setFontText("font.ttf");
        settings.setAppIcon("ui/M_logo.png");
        settings.setFontSizeScaleUI(1.5);
        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public LoadingScene newLoadingScene() {
                return new M_LoadingScene();
            }

            @Override
            public StartupScene newStartup(int width, int height) {
                return new M_StartupScene(width, height);
            }
            @Override
            public FXGLMenu newMainMenu() {
                return new M_MainMenu();
            }
        });
    }
    //basic settings, including cursor, resolution, fonts, enabling customized scene, and basic information of game

    public static void main(String[] args) {
        launch(args);
    }

    private static Entity player1;
    public static Entity getPlayer1(){
        return player1;
    }
    private int id = 0;
    private int id1 = 0;
    private int id2 = 0;
    private static Entity player2;
    public static Entity getPlayer2(){
        return player2;
    }
    private Entity weapon;
    private Point2D dir1 = new Point2D(0,0);
    private Point2D dir2 = new Point2D(0,0);
    private final Point2D up = new Point2D(0,-1);
    private final Point2D down = new Point2D(0,1);
    private final Point2D left = new Point2D(-1,0);
    private final Point2D right = new Point2D(1,0);
    private LocalTimer scoreTimer;
    private boolean isSingle = true;

    private boolean end = false;


    TimerAction action;

    @Override
    protected void initGame() {

        getGameScene().setBackgroundColor(Color.BLACK);
        FXGL.getGameWorld().addEntityFactory(new McDonalismFactory());
        FXGL.setLevelFromMap("TestLevel.tmx");
        Viewport viewport = getGameScene().getViewport();
        //basic initialization including adding level, factory, etc.

        end = false;
        //signal to detect whether a game should end; it will be used later.

        FXGL.loopBGM("Crypteque1.mp3");
        action = getEngineTimer().runAtInterval(()-> {
            if ((getSceneService().getCurrentScene().toString().equals("DialogSubScene")
                    || getSceneService().getCurrentScene().toString().equals("FXGLDefaultMenu"))
                    && !end){
                getAudioPlayer().pauseAllMusic();
            }
            else if (getSceneService().getCurrentScene().toString().equals("M_MainMenu") || end){
                getAudioPlayer().stopAllMusic();
                action.expire();
            }
            else
                getAudioPlayer().resumeAllMusic();
        }, Duration.seconds(tpf()));
        //bgm settings. Enable it only to be played in game, and restart when start a new game.

        Transfer transfer = new Transfer();
        isSingle = transfer.isSingle();
        id = transfer.getHeroId();
        id1 = transfer.getHeroId1();
        id2 = transfer.getHeroId2();
        //"transfer" is a class to handle and transfer the data from menu, such as chosen mode and hero.



        if (isSingle){
            if (id == 1){
                getGameScene().setCursor(FXGL.image("ui/cursor_crossHair.png"), new Point2D(23,23));
                player1 = FXGL.spawn("M");
                player1.getComponent(EnergyComponent.class).setPlayer("");
                weapon = FXGL.spawn("weapon");
                Entity camera = FXGL.spawn("camera");
                viewport.bindToEntity(camera,
                        getAppWidth() / 2 - player1.getWidth(),
                        getAppHeight() / 2 - player1.getHeight());
            }
            else if (id == 2){
                getGameScene().setCursor(FXGL.image("ui/cursor.png"), new Point2D(10,10));
                player1 = FXGL.spawn("K");
                player1.getComponent(EnergyComponent.class).setPlayer("");
                viewport.bindToEntity(player1,
                        getAppWidth() / 2 - player1.getWidth(),
                        getAppHeight() / 2 - player1.getHeight());
            }
            else {
                getGameScene().setCursor(FXGL.image("ui/cursor.png"), new Point2D(10,10));
                player1 = FXGL.spawn("W");
                player1.getComponent(EnergyComponent.class).setPlayer("");
                viewport.bindToEntity(player1,
                        getAppWidth() / 2 - player1.getWidth(),
                        getAppHeight() / 2 - player1.getHeight());
            }

            run(() -> {
                for (int i = 0; i < getGameTimer().getNow()/10; i++){
                    boolean inView = true;
                    int trial = 0;
                    while (inView){
                        trial++;
                        Point2D pos = new Point2D(FXGL.random(0, 1000), FXGL.random(100, 1000));
                        if(player1.getCenter().subtract(pos).magnitude() >= 200 || trial > 10){
                            inView = false;
                            FXGL.spawn("enemy1", pos);
                        }
                    }
                }
            }, Duration.seconds(5));

            viewport.setBounds(-480, -480, 48*50, 48*40);
            viewport.setZoom(1.0);
        }
        //settings for single player mode


        else {
            if (id1 == 1){
                player1 = FXGL.spawn("M");
                player1.getComponent(EnergyComponent.class).setPlayer("1");
                weapon = FXGL.spawn("weapon");
                getGameScene().setCursor(FXGL.image("ui/cursor_crossHair.png"), new Point2D(23,23));
            }

            if (id2 == 1){
                player2 = FXGL.spawn("K");
                player2.getComponent(EnergyComponent.class).setPlayer("2");
            }
            else if (id2 == 2){
                player2 = FXGL.spawn("W");
                player2.getComponent(EnergyComponent.class).setPlayer("2");
            }

            viewport.bindToFit(getAppWidth()/2,getAppHeight()/2, player1, player2);
            viewport.setBounds(-480, -480, 48*50, 48*40);


            run(() -> {
                for (int i = 0; i < getGameTimer().getNow()/10; i++){
                    boolean inView = true;
                    int trial = 0;
                    while (inView){
                        trial++;
                        Point2D pos = new Point2D(FXGL.random(0, 1000), FXGL.random(100, 1000));
                        if((player2.getCenter().subtract(pos).magnitude() >= 200
                                && player1.getCenter().subtract(pos).magnitude() >= 200)
                                || trial > 15){
                            inView = false;
                            FXGL.spawn("enemy1", pos);
                        }
                    }
                }
            }, Duration.seconds(5));
        }
        //settings for multiplayer mode


        scoreTimer = FXGL.newLocalTimer();
        scoreTimer.capture();
        //timer to measure the survival time, which is counted in the final score.
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0,0);

        getPhysicsWorld().addCollisionHandler(new BulletBorderHandler());

        getPhysicsWorld().addCollisionHandler(new BulletEnemyHandler());

        getPhysicsWorld().addCollisionHandler(new M_EnemyHandler());

        getPhysicsWorld().addCollisionHandler(new M_BodyHandler());

        getPhysicsWorld().addCollisionHandler(new W_BodyHandler());

        getPhysicsWorld().addCollisionHandler(new W_EnemyHandler());

        getPhysicsWorld().addCollisionHandler(new K_BodyHandler());

        getPhysicsWorld().addCollisionHandler(new K_EnemyHandler());

        getPhysicsWorld().addCollisionHandler(new K_MeleeEnemyHandler());

        //adding collision handler. The usage can be seen in class names.
    }

    @Override
    protected void onUpdate(double tpf) {

        if(isSingle){
            if (!getb("isAlive")){
                if(!end){
                    scoreTimer.capture();
                    end =true;
                    FXGL.play("gameOver.wav");
                }
                if(scoreTimer.elapsed(Duration.seconds(2)))
                    getSceneService().pushSubScene(new M_FailedScene(geti("score")));
            }else {
                if(scoreTimer.elapsed(Duration.seconds(2))){
                    inc("score", 1);
                    scoreTimer.capture();
                }
                if (id == 1){
                    player1.getComponent(M_Component.class).move(dir2);
                }
                else if (id == 2){
                    player1.getComponent(K_Component.class).move(dir2);
                }
                else{
                    player1.getComponent(W_Component.class).move(dir2);
                }
            }
        } else {
            if (!getb("isAlive1") && !getb("isAlive2")){
                if(!end){
                    scoreTimer.capture();
                    end =true;
                    FXGL.play("gameOver.wav");
                }
                if(scoreTimer.elapsed(Duration.seconds(2)))
                    getSceneService().pushSubScene(new M_FailedScene(geti("score")));
            }
            else {
                if(scoreTimer.elapsed(Duration.seconds(2))){
                    inc("score", 1);
                    scoreTimer.capture();
                }
                if (id1 == 1)
                    player1.getComponent(M_Component.class).move(dir1);
                if (id2 == 1)
                    player2.getComponent(K_Component.class).move(dir2);
                else player2.getComponent(W_Component.class).move(dir2);
            }
        }
    }

    //onupdate will be called every frame. The usage is as followed
    //1. check whether all players are dead. if yes, end the game.
    //2. record the score generated by survival time
    //3. enable player moving method

    @Override
    protected void initInput() {

        FXGL.getInput().addAction(new UserAction("Player1 Move Up") {
            @Override
            protected void onActionBegin() {
                dir1 = dir1.add(up);
            }
            @Override
            protected void onActionEnd() {
                dir1 = dir1.subtract(up);
            }
        }, KeyCode.UP);
        FXGL.getInput().addAction(new UserAction("Player1 Move Down") {
            @Override
            protected void onActionBegin() {
                dir1 = dir1.add(down);
            }
            @Override
            protected void onActionEnd() {
                dir1 = dir1.subtract(down);
            }
        }, KeyCode.DOWN);
        FXGL.getInput().addAction(new UserAction("Player1 Move Left") {
            @Override
            protected void onActionBegin() {
                dir1 = dir1.add(left) ;
            }
            @Override
            protected void onActionEnd() { dir1 = dir1.subtract(left);
            }
        }, KeyCode.LEFT);
        FXGL.getInput().addAction(new UserAction("Player1 Move Right") {
            @Override
            protected void onActionBegin() {
                dir1 = dir1.add(right) ;
            }
            @Override
            protected void onActionEnd() {
                dir1 = dir1.subtract(right);
            }
        }, KeyCode.RIGHT);
        //player1 movement

        FXGL.getInput().addAction(new UserAction("Player or Player2 Move Up") {
            @Override
            protected void onActionBegin() {
                dir2 = dir2.add(up);
            }
            @Override
            protected void onActionEnd() {
                dir2 = dir2.subtract(up);
            }
        }, KeyCode.W);
        FXGL.getInput().addAction(new UserAction("Player or Player2 Move Down") {
            @Override
            protected void onActionBegin() {
                dir2 = dir2.add(down) ;
            }
            @Override
            protected void onActionEnd() {
                dir2 = dir2.subtract(down);
            }
        }, KeyCode.S);
        FXGL.getInput().addAction(new UserAction("Player or Player2 Move Left") {
            @Override
            protected void onActionBegin() {
                dir2 = dir2.add(left) ;
            }
            @Override
            protected void onActionEnd() {
                dir2 = dir2.subtract(left);
            }
        }, KeyCode.A);
        FXGL.getInput().addAction(new UserAction("Player or Player2 Move Right") {
            @Override
            protected void onActionBegin() {
                dir2 = dir2.add(right) ;
            }
            @Override
            protected void onActionEnd() {
                dir2 = dir2.subtract(right);
            }
        }, KeyCode.D);


        FXGL.getInput().addAction(new UserAction("Shoot") {
            @Override
            protected void onAction() {
                if (weapon != null)
                    if(weapon.hasComponent(WeaponComponent.class)){
                        weapon.getComponent(WeaponComponent.class).shoot();
                    }
            }
            @Override
            protected void onActionEnd(){
                if (weapon != null)
                    if(weapon.hasComponent(WeaponComponent.class))
                        weapon.getComponent(WeaponComponent.class).idle();
            }
        }, MouseButton.PRIMARY);

        FXGL.getInput().addAction(new UserAction("Left Punch/Summon") {
            @Override
            protected void onActionBegin() {
                if (isSingle){
                    if(id == 2){
                        player1.getComponent(K_Component.class).punch(-1.0);
                    }
                    else if (id == 3)
                        player1.getComponent(W_Component.class).summon(true);
                }
                else {
                    if (id2 == 1)
                        player2.getComponent(K_Component.class).punch(-1.0);
                    else if (id2 == 2){
                        player2.getComponent(W_Component.class).summon(true);
                    }
                }
            }
            @Override
            protected void onActionEnd(){
                if (isSingle){
                    if (id == 3)
                        player1.getComponent(W_Component.class).summon(false);
                }
                else {
                    if (id2 == 2){
                        player2.getComponent(W_Component.class).summon(false);
                    }
                }
            }
        }, KeyCode.J);

        FXGL.getInput().addAction(new UserAction("Right Punch/Recover") {
            @Override
            protected void onActionBegin() {
                if (isSingle){
                    if (id == 2)
                        player1.getComponent(K_Component.class).punch(1.0);
                    if (id == 3)
                        player1.getComponent(W_Component.class).recover(true);
                }
                else {
                    if (id2 == 1)
                        player2.getComponent(K_Component.class).punch(1.0);
                    else if (id2 == 2){
                        player2.getComponent(W_Component.class).recover(true);
                    }
                }
            }
            @Override
            protected void onActionEnd(){
                if (isSingle){
                    if (id == 3)
                        player1.getComponent(W_Component.class).recover(false);
                }
                else if (id2 == 2)
                    player2.getComponent(W_Component.class).recover(false);
            }
        }, KeyCode.K);

    }
    //handle input and bind the actions to specific functions


    @Override
    protected void initUI() {

        Pane scorePane = new Pane(FXGL.texture("ui/ui_score_pane.png"));
        addUINode(scorePane, 24, 160);
        Text scoreValue = FXGL.addVarText("score",100,190);
        scoreValue.fontProperty().unbind();
        scoreValue.setFont(Font.loadFont(getClass().getResourceAsStream("/assets/ui/fonts/buttonFont.ttf"),20));
        scoreValue.setFill(Color.BLACK);
        Text score = new Text("SCORE:");
        score.setFill(Color.BLACK);
        score.setFont(Font.loadFont(getClass().getResourceAsStream("/assets/ui/fonts/buttonFont.ttf"),20));
        addUINode(score, 36, 190);

        if (isSingle){
            Text player = new Text("   PLAYER");
            player.setFill(Color.WHITESMOKE);
            player.setFont(Font.loadFont(getClass().getResourceAsStream("/assets/ui/fonts/buttonFont.ttf"),18));

            ProgressBar barHp = new ProgressBar();
            barHp.setMaxValue(player1.getComponent(HealthIntComponent.class).getMaxValue());
            barHp.currentValueProperty().bind(player1.getComponent(HealthIntComponent.class).valueProperty());
            barHp.setFill(Color.RED);
            barHp.setWidth(120);
            barHp.setHeight(12);

            ProgressBar barMp = new ProgressBar();
            barMp.setMaxValue(player1.getComponent(EnergyComponent.class).getMax());
            barMp.currentValueProperty().bind(getip("mp"));
            barMp.setFill(Color.BLUE);
            barMp.setWidth(120);
            barMp.setHeight(12);

            VBox box = new VBox(player, barHp, barMp);
            box.setLayoutX(8);
            box.setLayoutY(24);
            box.setSpacing(-3);
            Pane pane = new Pane(FXGL.texture("ui/ui_hud_pane.png"));
            pane.getChildren().add(box);
            pane.setLayoutX(20);
            pane.setLayoutY(20);
            addUINode(pane);
        }
        else {
            player1.getComponent(EnergyComponent.class).setPlayer("1");
            player2.getComponent(EnergyComponent.class).setPlayer("2");

            Text player1Text = new Text("   PLAYER1");
            player1Text.setFill(Color.WHITESMOKE);
            player1Text.setFont(Font.loadFont(getClass().getResourceAsStream("/assets/ui/fonts/buttonFont.ttf"),18));

            ProgressBar barHp1 = new ProgressBar();
            barHp1.setMaxValue(McDonalismApp.player1.getComponent(HealthIntComponent.class).getMaxValue());
            barHp1.currentValueProperty().bind(McDonalismApp.player1.getComponent(HealthIntComponent.class).valueProperty());
            barHp1.setFill(Color.RED);
            barHp1.setWidth(120);
            barHp1.setHeight(12);

            ProgressBar barMp1 = new ProgressBar();
            barMp1.setMaxValue(McDonalismApp.player1.getComponent(EnergyComponent.class).getMax());
            barMp1.currentValueProperty().bind(getip("mp1"));
            barMp1.setFill(Color.BLUE);
            barMp1.setWidth(120);
            barMp1.setHeight(12);

            VBox box1 = new VBox(player1Text, barHp1, barMp1);
            box1.setLayoutX(8);
            box1.setLayoutY(24);
            box1.setSpacing(-3);
            Pane pane1 = new Pane(FXGL.texture("ui/ui_hud_pane.png"));
            pane1.getChildren().add(box1);
            pane1.setLayoutX(20);
            pane1.setLayoutY(20);
            addUINode(pane1);

            Text player2Text = new Text("   PLAYER2");
            player2Text.setFill(Color.WHITESMOKE);
            player2Text.setFont(Font.loadFont(getClass().getResourceAsStream("/assets/ui/fonts/buttonFont.ttf"),18));

            ProgressBar barHp2 = new ProgressBar();
            barHp2.setMaxValue(player2.getComponent(HealthIntComponent.class).getMaxValue());
            barHp2.currentValueProperty().bind(player2.getComponent(HealthIntComponent.class).valueProperty());
            barHp2.setFill(Color.RED);
            barHp2.setWidth(120);
            barHp2.setHeight(12);

            ProgressBar barMp2 = new ProgressBar();
            barMp2.setMaxValue(player2.getComponent(EnergyComponent.class).getMax());
            barMp2.currentValueProperty().bind(getip("mp2"));
            barMp2.setFill(Color.BLUE);
            barMp2.setWidth(120);
            barMp2.setHeight(12);

            VBox box2 = new VBox(player2Text, barHp2, barMp2);
            box2.setLayoutX(8);
            box2.setLayoutY(24);
            box2.setSpacing(-3);
            Pane pane2 = new Pane(FXGL.texture("ui/ui_hud_pane.png"));
            pane2.getChildren().add(box2);
            pane2.setLayoutX(230);
            pane2.setLayoutY(20);
            addUINode(pane2);
        }
    }
    //initUI is to show the basic HUD in game, including hp, mp, player id.
}


