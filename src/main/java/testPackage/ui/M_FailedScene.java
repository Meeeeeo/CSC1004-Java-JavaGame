package testPackage.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class M_FailedScene extends SubScene {

    public M_FailedScene(int score) {

        StackPane bg = new StackPane(new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight(), Color.rgb(0,0,0,0.8)));
        Texture pane = FXGL.texture("ui/ui_main_pane.png");
        Text text1 = new Text("GAME");
        Text text2 = new Text("OVER");
        text1.setFont(Font.loadFont(getClass().getResourceAsStream("/assets/ui/fonts/buttonFont.ttf"),60));
        text2.setFont(Font.loadFont(getClass().getResourceAsStream("/assets/ui/fonts/buttonFont.ttf"),60));
        Text scoreText = new Text("SCORE:" + score);
        scoreText.setFont(Font.loadFont(getClass().getResourceAsStream("/assets/ui/fonts/font.ttf"), 60));
        VBox tempBox = new VBox(scoreText);
        tempBox.setAlignment(Pos.CENTER);
        M_Button button = new M_Button("back", ()-> {
            FXGL.getSceneService().popSubScene();
            FXGL.getGameController().gotoMainMenu();
        });
        VBox box = new VBox(text1, text2, tempBox, button);

        box.setMinWidth(300);
        pane.setLayoutX(FXGL.getAppWidth()/2 - pane.getWidth()/2);
        pane.setLayoutY(FXGL.getAppHeight()/2 - pane.getHeight()/2);
        box.setLayoutX(FXGL.getAppWidth()/2 - box.getMinWidth()/2);
        box.setLayoutY(128);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(3);

        getContentRoot().getChildren().addAll(bg, pane, box);

    }
}
