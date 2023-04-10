package testPackage.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.scene.layout.VBox;
import testPackage.database.JdbcUtils;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends FXGLMenu {

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
    public MainMenu() {
        super(MenuType.MAIN_MENU);

        GameButton b1 = new GameButton("New Game", this::fireNewGame);
        GameButton b2 = new GameButton("Options", () -> getController().gotoGameMenu());
        GameButton b3 = new GameButton("Exit", this::fireExit);

        VBox box = new VBox(b1,b2,b3);
        getContentRoot().getChildren().setAll(box);
    }
}
