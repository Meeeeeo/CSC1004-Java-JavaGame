package testPackage.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class UpdatedMainMenu extends FXGLMenu {
    public UpdatedMainMenu() {
        super(MenuType.MAIN_MENU);

        Button x1 = new Button("New Game");
        x1.setOnAction(event -> getController().startNewGame());
        Button x2 = new Button("Options");
        x2.setOnAction(event -> getController().gotoGameMenu());
        Button x3 = new Button("Exit");
        x3.setOnAction(event -> getController().exit());
        //Button x4 = new Button("New Game");
        //Button x5 = new Button("New Game");
        //Button x6 = new Button("New Game");
        VBox box = new VBox(x1,x2,x3);
        getContentRoot().getChildren().setAll(box);
    }
}
