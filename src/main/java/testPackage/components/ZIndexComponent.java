package testPackage.components;

import com.almasb.fxgl.entity.component.Component;

public class ZIndexComponent extends Component {
    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
        entity.setZIndex((int) (entity.getY() - entity.getHeight()));
    }
}
