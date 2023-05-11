package testPackage.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import testPackage.Transfer;
import testPackage.database.JdbcUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class M_MainMenu extends FXGLMenu {

    private int heroId = 0;
    private int heroId1 = 0;
    private int heroId2 = 0;

    private boolean verify(String username, String password) {
        JdbcUtils j = new JdbcUtils();
        j.getConnection();

        String sql = "select count(1) count from `user` where username = ? and password = ?";
        List<String> params = new ArrayList<>();
        params.add(username);
        params.add(password);
        try {
            return j.count(sql, params) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean exists(String username) {
        JdbcUtils j = new JdbcUtils();
        j.getConnection();

        String sql = "select count(1) count from `user` where username = ?";
        List<String> params = new ArrayList<>();
        params.add(username);
        try {
            return j.count(sql, params) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean signUp(String username, String password) {
        JdbcUtils j = new JdbcUtils();
        j.getConnection();

        String sql = "insert into `user` (username,password) values (?,?)";
        List<String> params = new ArrayList<>();
        params.add(username);
        params.add(password);
        try {
            return j.insert(sql, params) == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public M_MainMenu() {
        super(MenuType.MAIN_MENU);

        final Font passwordFont =
                Font.loadFont(getClass().getResourceAsStream("/assets/ui/fonts/passwordFont.ttf"),20);
        final Font usernameFont =
                Font.loadFont(getClass().getResourceAsStream("/assets/ui/fonts/font.ttf"),29);
        final Font feedback =
                Font.loadFont(getClass().getResourceAsStream("/assets/ui/fonts/font.ttf"),20);
        final Font font =
                Font.loadFont(getClass().getResourceAsStream("/assets/ui/fonts/font.ttf"), 30);
        final Font bigFont =
                Font.loadFont(getClass().getResourceAsStream("/assets/ui/fonts/font.ttf"), 45);

        final Image iconRandom = FXGL.image("ui/ui_heroIcon_random.png");
        final Image iconM = FXGL.image("ui/ui_heroIcon_m.png");
        final Image iconK = FXGL.image("ui/ui_heroIcon_k.png");
        final Image iconW = FXGL.image("ui/ui_heroIcon_w.png");

        Transfer transfer = new Transfer();//transfer the settings via Transfer.class to App

        StackPane bg = new StackPane(
                new Rectangle(getAppWidth(), getAppHeight(), Color.BLACK),
                new ImageView(FXGL.image("ui/M_logo.png")));
        //Background setting

        StackPane pane = new StackPane(new Rectangle(getAppWidth(), getAppHeight(), Color.TRANSPARENT));
        Label hint = new Label("CLICK TO SKIP");
        hint.setFont(font);
        hint.setTextFill(Color.WHITESMOKE);
        hint.setLayoutX(560);
        hint.setLayoutY(510);
        //create hint to show "CLICK TO SKIP"

        Media media = new Media(new File("src/main/resources/assets/textures/Startup.mp4").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaPlayer.play();
        //play the prepared video

        pane.setOnMouseClicked(mouseEvent -> mediaPlayer.stop());
        mediaPlayer.setOnEndOfMedia(mediaPlayer::stop);
        //stop the video when user clicks or video finishes

        mediaPlayer.setOnStopped(() -> getContentRoot().getChildren().removeAll(bg, mediaView, hint, pane));
        //remove the stuff when stop the video

        StackPane background = new StackPane(FXGL.texture("ui/background.png"));

        Pane heroPane = new Pane(FXGL.texture("ui/ui_hero_pane.png"));
        VBox heroBox = new VBox();
        heroBox.setSpacing(3);
        heroBox.setLayoutX(264+100+20);
        heroBox.setLayoutY(114-90);
        heroBox.setVisible(false);
        heroBox.setAlignment(Pos.CENTER);
        heroPane.setVisible(false);
        heroPane.setLayoutX(220+100+20);
        heroPane.setLayoutY(75-65);

        Pane heroesPane = new Pane(FXGL.texture("ui/ui_heroes_pane.png"));
        VBox heroesBox = new VBox();
        heroesBox.setSpacing(3);
        heroesBox.setLayoutX(264-6);
        heroesBox.setLayoutY(114-90);
        heroesBox.setVisible(false);
        heroesBox.setAlignment(Pos.CENTER);
        heroesPane.setVisible(false);
        heroesPane.setLayoutX(264-6-18);
        heroesPane.setLayoutY(75-65);

        Pane preHeroPane = new Pane(FXGL.texture("ui/ui_preHero_pane.png"));
        VBox preHeroBox = new VBox();
        preHeroBox.setSpacing(3);
        preHeroBox.setLayoutX(264+200-96-20);
        preHeroBox.setLayoutY(114);
        preHeroBox.setVisible(false);
        preHeroBox.setAlignment(Pos.CENTER);
        preHeroPane.setVisible(false);
        preHeroPane.setLayoutX(220+200-96-20);
        preHeroPane.setLayoutY(75);

        Pane mainPane = new Pane(FXGL.texture("ui/ui_main_pane.png"));
        VBox mainBox = new VBox();
        mainBox.setSpacing(3);
        mainBox.setLayoutX(264+120);
        mainBox.setLayoutY(114);
        mainBox.setVisible(false);
        mainBox.setAlignment(Pos.CENTER);
        mainPane.setVisible(false);
        mainPane.setLayoutX(220+120);
        mainPane.setLayoutY(75);

        Pane loginPane = new Pane(FXGL.texture("ui/ui_login_pane.png"));
        VBox loginBox = new VBox();
        loginBox.setSpacing(3);
        loginBox.setLayoutX(240+120);
        loginBox.setLayoutY(27);
        loginBox.setAlignment(Pos.TOP_CENTER);
        loginPane.setLayoutX(220+120);
        loginPane.setLayoutY(12);

        Pane signUpPane = new Pane(FXGL.texture("ui/ui_signUp_pane.png"));
        VBox signUpBox = new VBox();
        signUpBox.setSpacing(3);
        signUpBox.setAlignment(Pos.TOP_CENTER);
        signUpBox.setVisible(false);
        signUpPane.setVisible(false);
        signUpBox.setLayoutX(240+120);
        signUpBox.setLayoutY(40);
        signUpPane.setLayoutX(220+120);
        signUpPane.setLayoutY(25);


        Label player = new Label("PLAYER");
        Label player1 = new Label("PLAYER 1");
        Label player2 = new Label("PLAYER 2");
        Label heroName = new Label("Random");
        Label heroName1 = new Label("Random");
        Label heroName2 = new Label("Random");
        ImageView heroIcon = new ImageView(iconRandom);
        ImageView heroIcon1 = new ImageView(iconRandom);
        ImageView heroIcon2 = new ImageView(iconRandom);
        heroName.setFont(font);
        heroName.setAlignment(Pos.CENTER);
        heroName.setMinWidth(102);
        heroName1.setFont(font);
        heroName1.setAlignment(Pos.CENTER);
        heroName1.setMinWidth(102);
        heroName2.setFont(font);
        heroName2.setAlignment(Pos.CENTER);
        heroName2.setMinWidth(102);
        player.setFont(bigFont);
        player.setAlignment(Pos.TOP_CENTER);
        player1.setFont(bigFont);
        player1.setAlignment(Pos.TOP_CENTER);
        player2.setFont(bigFont);
        player2.setAlignment(Pos.TOP_CENTER);
        Pane space1 = new Pane(new Rectangle(9,9,Color.TRANSPARENT));

        M_Button backBtn1 = new M_Button("back", () -> {
            preHeroBox.setVisible(true);
            preHeroPane.setVisible(true);
            heroBox.setVisible(false);
            heroPane.setVisible(false);
        });
        M_Button startBtn1 = new M_Button("start", () -> {
            transfer.setHeroId(heroId);
            fireNewGame();
            heroBox.setVisible(false);
            heroPane.setVisible(false);
            mainBox.setVisible(true);
            mainPane.setVisible(true);
            heroId = 0;
            heroIcon.setImage(iconRandom);
            heroName.setText("Random");
        });
        M_Button leftBtn = new M_Button("left", () -> {
            heroId--;
            if (heroId %4 == 0) {
                heroIcon.setImage(iconRandom);
                heroName.setText("Random");
            }
            else if (heroId %4 == 1 || heroId %4 == -3) {
                heroIcon.setImage(iconM);
                heroName.setText("McDonald");
            }
            else if (heroId %4 == 2 || heroId %4 == -2) {
                heroIcon.setImage(iconK);
                heroName.setText("Sanders");
            }
            else if (heroId %4 == 3 || heroId %4 == -1) {
                heroIcon.setImage(iconW);
                heroName.setText("Wiz");
            }
        });
        M_Button rightBtn = new M_Button("right", () -> {
            heroId++;
            if (heroId %4 == 0) {
                heroIcon.setImage(iconRandom);
                heroName.setText("Random");
            }
            else if (heroId %4 == 1 || heroId %4 == -3) {
                heroIcon.setImage(iconM);
                heroName.setText("McDonald");
            }
            else if (heroId %4 == 2 || heroId %4 == -2) {
                heroIcon.setImage(iconK);
                heroName.setText("Sanders");
            }
            else if (heroId %4 == 3 || heroId %4 == -1) {
                heroIcon.setImage(iconW);
                heroName.setText("Wiz");
            }
        });
        HBox choiceBox = new HBox(leftBtn, heroName, rightBtn);
        choiceBox.setSpacing(3);
        choiceBox.setMinHeight(70);
        choiceBox.setAlignment(Pos.CENTER);
        heroBox.getChildren().setAll(player, heroIcon, choiceBox, space1, startBtn1, backBtn1);

        M_Button backBtn2 = new M_Button("back", () -> {
            preHeroBox.setVisible(true);
            preHeroPane.setVisible(true);
            heroesBox.setVisible(false);
            heroesPane.setVisible(false);
        });
        M_Button startBtn2 = new M_Button("start", () -> {
            transfer.setHeroId1(heroId1);
            transfer.setHeroId2(heroId2);
            fireNewGame();
            heroesBox.setVisible(false);
            heroesPane.setVisible(false);
            mainBox.setVisible(true);
            mainPane.setVisible(true);
            heroId1 = 0;
            heroId2 = 0;
            heroIcon1.setImage(iconRandom);
            heroName1.setText("Random");
            heroIcon2.setImage(iconRandom);
            heroName2.setText("Random");
        });
        M_Button leftBtn1 = new M_Button("left", () -> {
            heroId1--;
            if (heroId1 %2 == 0) {
                heroIcon1.setImage(iconRandom);
                heroName1.setText("Random");
            }
            else {
                heroIcon1.setImage(iconM);
                heroName1.setText("McDonald");
            }
        });
        M_Button rightBtn1 = new M_Button("right", () -> {
            heroId1++;
            if (heroId1 %2 == 0) {
                heroIcon1.setImage(iconRandom);
                heroName1.setText("Random");
            }
            else {
                heroIcon1.setImage(iconM);
                heroName1.setText("McDonald");
            }
        });
        M_Button leftBtn2 = new M_Button("left", () -> {
            heroId2--;
            if (heroId2 %3 == 0) {
                heroIcon2.setImage(iconRandom);
                heroName2.setText("Random");
            }
            else if (heroId2 %3 == 1 || heroId2%3 == -2) {
                heroIcon2.setImage(iconK);
                heroName2.setText("Sanders");
            }
            else {
                heroIcon2.setImage(iconW);
                heroName2.setText("Wiz");
            }
        });
        M_Button rightBtn2 = new M_Button("right", () -> {
            heroId2++;
            if (heroId2 %3 == 0) {
                heroIcon2.setImage(iconRandom);
                heroName2.setText("Random");
            }
            else if (heroId2 %3 == 1 || heroId2%3 == -2) {
                heroIcon2.setImage(iconK);
                heroName2.setText("Sanders");
            }
            else {
                heroIcon2.setImage(iconW);
                heroName2.setText("Wiz");
            }
        });
        HBox choiceBox1 = new HBox(leftBtn1, heroName1, rightBtn1);
        choiceBox1.setSpacing(3);
        choiceBox1.setMinHeight(70);
        choiceBox1.setAlignment(Pos.CENTER);
        HBox choiceBox2 = new HBox(leftBtn2, heroName2, rightBtn2);
        choiceBox2.setSpacing(3);
        choiceBox2.setMinHeight(70);
        choiceBox2.setAlignment(Pos.CENTER);
        VBox heroBox1 = new VBox(player1, heroIcon1, choiceBox1, space1);
        heroBox1.setAlignment(Pos.CENTER);
        VBox heroBox2 = new VBox(player2, heroIcon2, choiceBox2, space1);
        heroBox2.setAlignment(Pos.CENTER);
        HBox heroes = new HBox(heroBox1, heroBox2);
        heroes.setSpacing(60);
        heroesBox.getChildren().setAll(heroes, startBtn2, backBtn2);

        M_Button backBtn3 = new M_Button("back", () -> {
            mainBox.setVisible(true);
            mainPane.setVisible(true);
            preHeroBox.setVisible(false);
            preHeroPane.setVisible(false);
        });
        M_Button soloBtn = new M_Button("single", () -> {
            transfer.setSingle(true);
            preHeroBox.setVisible(false);
            preHeroPane.setVisible(false);
            heroBox.setVisible(true);
            heroPane.setVisible(true);
        });
        M_Button multiBtn = new M_Button("multi", () -> {
            transfer.setSingle(false);
            preHeroBox.setVisible(false);
            preHeroPane.setVisible(false);
            heroesBox.setVisible(true);
            heroesPane.setVisible(true);
        });
        preHeroBox.getChildren().setAll(soloBtn, multiBtn, backBtn3);

        M_Button playBtn = new M_Button("play", () -> {
            mainBox.setVisible(false);
            mainPane.setVisible(false);
            preHeroBox.setVisible(true);
            preHeroPane.setVisible(true);
        });
        LocalTimer timer = getTimer().newLocalTimer(); //avoid duplicated children added to game menu
        M_Button optionsBtn = new M_Button("options", () ->{
            if (timer.elapsed(Duration.seconds(0.2)))
                getController().gotoGameMenu();
            timer.capture();
        });
        M_Button exitBtn1 = new M_Button("exit", this::fireExit);
        mainBox.getChildren().setAll(
                playBtn,
                optionsBtn,
                exitBtn1);

        TextField f1 = new TextField();
        PasswordField f2 = new PasswordField();
        Label l1 = new Label("USERNAME");
        Label l2 = new Label("PASSWORD");
        Label l3 = new Label();
        f1.setFont(usernameFont);
        f2.setFont(passwordFont);
        f1.setBackground(new Background(new BackgroundImage(
                FXGL.image("ui/inputField.png"),
                BackgroundRepeat.ROUND,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
        f2.setBackground(new Background(new BackgroundImage(
                FXGL.image("ui/inputField.png"),
                BackgroundRepeat.ROUND,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
        f1.setPrefWidth(240);
        f2.setPrefWidth(240);
        l1.setFont(font);
        l2.setFont(font);
        l3.setFont(feedback);
        l3.setTextFill(Color.RED);
        l3.setAlignment(Pos.TOP_CENTER);
        VBox loginFieldBox = new VBox(l1, f1, l2, f2);
        loginFieldBox.setAlignment(Pos.TOP_LEFT);
        M_Button loginBtn = new M_Button("signIn", () -> {
            if(f1.getText().equals("") && f2.getText().equals("")) {
                loginBox.setVisible(false);
                loginPane.setVisible(false);
                mainBox.setVisible(true);
                mainPane.setVisible(true);
            }
            if (verify(f1.getText(), f2.getText())) {
                loginBox.setVisible(false);
                loginPane.setVisible(false);
                mainBox.setVisible(true);
                mainPane.setVisible(true);
            } else {
                l3.setText("Invalid username or password");
            }
        });
        M_Button signUpBtn1 = new M_Button("signUp", () -> {
            loginBox.setVisible(false);
            loginPane.setVisible(false);
            signUpBox.setVisible(true);
            signUpPane.setVisible(true);
        });
        M_Button exitBtn2 = new M_Button("exit", this::fireExit);
        loginBox.getChildren().setAll(
                loginFieldBox,
                l3,
                loginBtn,
                signUpBtn1,
                exitBtn2);

        TextField f3 = new TextField();
        PasswordField f4 = new PasswordField();
        PasswordField f5 = new PasswordField();
        Label l4 = new Label("USERNAME");
        Label l5 = new Label("PASSWORD");
        Label l6 = new Label("CONFIRM YOUR PASSWORD");
        Label l7 = new Label();
        f3.setFont(usernameFont);
        f4.setFont(passwordFont);
        f5.setFont(passwordFont);
        f3.setBackground(new Background(new BackgroundImage(
                FXGL.image("ui/inputField.png"),
                BackgroundRepeat.ROUND,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
        f4.setBackground(new Background(new BackgroundImage(
                FXGL.image("ui/inputField.png"),
                BackgroundRepeat.ROUND,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
        f5.setBackground(new Background(new BackgroundImage(
                FXGL.image("ui/inputField.png"),
                BackgroundRepeat.ROUND,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
        f3.setPrefWidth(240);
        f4.setPrefWidth(240);
        f5.setPrefWidth(240);
        l4.setFont(font);
        l5.setFont(font);
        l6.setFont(font);
        l7.setFont(feedback);
        l7.setTextFill(Color.RED);
        l7.setAlignment(Pos.TOP_CENTER);
        VBox signUpFieldBox = new VBox(l4, f3, l5, f4, l6, f5);
        signUpFieldBox.setAlignment(Pos.TOP_LEFT);
        M_Button signUpBtn2 = new M_Button("signUp", () -> {
            if(f3.getText().isEmpty()){
                l7.setText("Username cannot be empty");
            }else if (exists(f3.getText())) {
                l7.setText("Username already exists");
            } else {
                if(!f4.getText().equals(f5.getText())){
                    l7.setText("Two passwords do not match");
                }
                else if (signUp(f3.getText(), f4.getText())) {
                    l7.setText("");
                    signUpBox.setVisible(false);
                    signUpPane.setVisible(false);
                    mainPane.setVisible(true);
                    mainBox.setVisible(true);
                }
            }
        });
        M_Button backBtn4 = new M_Button("back", () -> {
            signUpBox.setVisible(false);
            signUpPane.setVisible(false);
            loginBox.setVisible(true);
            loginPane.setVisible(true);
        });
        signUpBox.getChildren().setAll(signUpFieldBox, l7, signUpBtn2, backBtn4);


        getContentRoot().getChildren().setAll(
                background,
                mainPane, mainBox,
                preHeroPane, preHeroBox,
                heroPane, heroBox,
                heroesPane, heroesBox,
                loginPane, loginBox,
                signUpPane, signUpBox);

        getContentRoot().getChildren().addAll(bg, mediaView, hint, pane);
        getContentRoot().setCursor(new ImageCursor(FXGL.image("ui/cursor.png"),10,10));
    }
}
