package testPackage.components;

import com.almasb.fxgl.entity.component.Component;

public class DynamicDepthComponent extends Component {
    @Override
    public void onUpdate(double tpf) {
        entity.setZIndex((int) (entity.getY() + entity.getHeight()));
    }
}
