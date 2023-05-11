package testPackage.components;

import com.almasb.fxgl.entity.component.Component;

//component for entities to realize depth of field
public class DynamicDepthComponent extends Component {

    private int h = 0;
    @Override
    public void onUpdate(double tpf) {
        entity.setZIndex((int) (entity.getY() + entity.getHeight() + h));
    }

}
