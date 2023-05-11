package testPackage.ui;

import com.almasb.fxgl.app.scene.StartupScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class M_StartupScene extends StartupScene {
    public M_StartupScene(int appWidth, int appHeight) {
        super(appWidth, appHeight);
        Rectangle bg = new Rectangle(appWidth, appHeight, Color.BLACK);
        Image logo = new Image(getClass().getResource("/assets/textures/ui/M_logo.png").toExternalForm());
        StackPane pane = new StackPane(bg, new ImageView(logo));
        getContentRoot().getChildren().add(pane);
        // add normal background for start-up scene
    }
}
