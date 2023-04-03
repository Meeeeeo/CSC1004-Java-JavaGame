package testPackage.components;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class AimingComponent extends Component {

    public Point2D aimDir(Point2D cursorPoint){
        Point2D player1 = entity.getCenter();
        Point2D aimDirection = cursorPoint.subtract(player1);
        return aimDirection;

    }
}
