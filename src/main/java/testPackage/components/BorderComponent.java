package testPackage.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

//border component, used to make sure border will not move
public class BorderComponent extends Component {

    private final Point2D pos;

    public BorderComponent(Point2D pos){
        this.pos = pos;
    }

    @Override
    public void onUpdate(double tpf) {
        entity.getComponent(PhysicsComponent.class).overwritePosition(pos);
    }
}
