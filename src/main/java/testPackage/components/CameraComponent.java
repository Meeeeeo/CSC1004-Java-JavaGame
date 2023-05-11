package testPackage.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import testPackage.McDonalismType;

//camera to simulate recoil
public class CameraComponent extends Component {

    @Override
    public void onUpdate(double tpf) {
        entity.setPosition(FXGL.getGameWorld().getSingleton(McDonalismType.PLAYER_M).getPosition());
        FXGL.getGameWorld().getEntitiesByType(McDonalismType.RECOIL).forEach(entity1 ->
                entity.translate(entity1.getCenter()));
    }
}
