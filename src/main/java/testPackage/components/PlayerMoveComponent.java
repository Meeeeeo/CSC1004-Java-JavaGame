package testPackage.components;

import com.almasb.fxgl.entity.component.Component;

public class PlayerMoveComponent extends Component {
    public void left() {
        entity.translateX(-2);
    }

    public void right() {
        entity.translateX(2);
    }

    public void up() {
        entity.translateY(-2);
    }

    public void down() {
        entity.translateY(2);
    }
}
