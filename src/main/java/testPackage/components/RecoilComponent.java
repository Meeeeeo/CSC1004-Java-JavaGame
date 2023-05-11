package testPackage.components;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.AnimationBuilder;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import testPackage.McDonalismType;

import static com.almasb.fxgl.dsl.FXGL.animationBuilder;

public class RecoilComponent extends Component {
    private final Point2D origin = new Point2D(0,0);

    public void recoil() {

        Point2D pos = FXGL.getGameWorld().getSingleton(McDonalismType.PLAYER_M).getComponent(M_Component.class).aimDir().multiply(-6);
        animationBuilder()
                .interpolator(Interpolators.ELASTIC.EASE_OUT())
                .duration(Duration.seconds(0.08))
                .translate(entity)
                .from(origin)
                .to(pos)
                .buildAndPlay();
        recover(pos);

        FXGL.getGameScene().getViewport().shake(1.5, 0.05);
    }

    public void recover(Point2D pos) {
        animationBuilder()
                .delay(Duration.seconds(0.03))
                .interpolator(Interpolators.SMOOTH.EASE_IN())
                .duration(Duration.seconds(0.15))
                .translate(entity)
                .from(pos)
                .to(origin)
                .buildAndPlay();
    }

}
