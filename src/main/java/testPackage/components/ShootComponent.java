package testPackage.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class ShootComponent extends Component {

    private LocalTimer gunTimer = FXGL.newLocalTimer();
    public void shoot(Point2D CursorPoint){
        if (gunTimer.elapsed(Duration.seconds(0.1))){
            Point2D shooter = entity.getCenter();
            Point2D shootDirection = CursorPoint.subtract(shooter);
            FXGL.play("gunfire.wav");
            SpawnData data = new SpawnData(shooter)
                    .put("cursor", CursorPoint)
                    .put("direction", shootDirection);
            FXGL.spawn("bullet1", data);
            gunTimer.capture();
        }

    }
}
