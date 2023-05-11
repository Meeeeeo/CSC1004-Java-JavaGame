package testPackage.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;
import testPackage.Config;
import testPackage.McDonalismType;

public class MeleeAreaComponent extends Component {
    @Override
    public void onUpdate(double tpf) {
        FXGL.getGameWorld().getEntitiesByType(McDonalismType.ENEMY).forEach(entity1 -> {
            if (entity.isColliding(entity1)){
                LocalTimer timer = FXGL.newLocalTimer();
                if (timer.elapsed(Duration.seconds(0.3))){
                    FXGL.play("W_punch.wav");
                    timer.capture();
                }
                HealthIntComponent hp = entity1.getComponent(HealthIntComponent.class);
                Enemy1Component component = entity1.getComponent(Enemy1Component.class);
                hp.damage(Config.W_DAMAGE);
                component.getHurt();
                if (hp.isZero()){
                    component.die();
                }
            }
        });
    }
}
