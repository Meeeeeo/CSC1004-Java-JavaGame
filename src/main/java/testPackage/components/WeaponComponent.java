package testPackage.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import testPackage.McDonalismType;

public class WeaponComponent extends Component {
    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
        entity.setZIndex(FXGL.getGameWorld().getSingleton(McDonalismType.PLAYER).getZIndex() + 1);
    }
}
