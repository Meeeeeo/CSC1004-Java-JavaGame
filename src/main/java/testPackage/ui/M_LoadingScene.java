package testPackage.ui;

import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// customized loading scene

public class M_LoadingScene extends LoadingScene {
    public M_LoadingScene() {
        Rectangle rect = new Rectangle(getAppWidth(),getAppHeight(), Color.web("#666666"));
        getContentRoot().getChildren().setAll(rect);
        Rectangle bg = new Rectangle(getAppWidth(), getAppHeight(), Color.BLACK);
        Image logo = FXGL.image("ui/M_logo.png");
        StackPane pane = new StackPane(bg, new ImageView(logo));
        getContentRoot().getChildren().add(pane);

    }
}
