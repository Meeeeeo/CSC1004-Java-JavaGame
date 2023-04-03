package testPackage.components;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class PlayerMoveComponent extends Component {

    private int velocity = 2;
//    public void left() {
//        entity.translateX(-2);
//    }
//
//    public void right() {
//        entity.translateX(2);
//    }
//
//    public void up() {
//        entity.translateY(-2);
//    }
//
//    public void down() {
//        entity.translateY(2);
//    }

    public void move(Point2D v){
        Point2D zero = new Point2D(0,0);
        Point2D dir = new Point2D(0,0);
        if (!v.equals(zero)) {
            dir = v.normalize().multiply(velocity);
        }
        entity.translate(dir);
    }
}
