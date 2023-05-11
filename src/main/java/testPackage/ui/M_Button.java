package testPackage.ui;

import com.almasb.fxgl.dsl.FXGL;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.play;


public class M_Button extends StackPane{

    private PauseTransition delay = new PauseTransition(Duration.seconds(0.1));
    private ImageView imageView = new ImageView();
    public M_Button(String content, Runnable action) {

        final Image idle = FXGL.image("ui/ui_"+content+"_idle.png");
        final Image hover = FXGL.image("ui/ui_"+content+"_hover.png");
        final Image confirm = FXGL.image("ui/ui_"+content+"_confirm.png");
        //images for different states

        setMaxWidth(idle.getWidth());
        setMaxHeight(idle.getHeight());
        //button size

        imageView.setImage(idle);
        getChildren().addAll(imageView);
        setAlignment(Pos.CENTER);
        //initial idle state

        setOnMouseEntered(event -> {
            play("hover.wav");
            imageView.setImage(hover);
        });
        //hover state

        setOnMouseExited(event -> {
            imageView.setImage(idle);
        });
        //hover state ends, enter idle state

        setOnMousePressed(event -> {
            FXGL.play("confirm.wav");
            imageView.setImage(confirm);
        });
        setOnMouseReleased(events -> {
            imageView.setImage(idle);
            delay.playFromStart();
            delay.setOnFinished(event -> {
                action.run();
            });
        });
        //click state

    }
}
