package testPackage.components;

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityGroup;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import kotlin.Lazy;
import testPackage.Config;
import testPackage.McDonalismType;

import java.util.List;

public class Player1Component extends Component {

    private Point2D zero = new Point2D(0,0);

    private List<Entity> borders = FXGL.getGameWorld().getEntitiesByType(McDonalismType.BORDER);

    private boolean collision = false;

    public void move(Point2D v){
        Point2D dir = new Point2D(0,0);
        if (!v.equals(zero)) {
            dir = v.normalize().multiply(Config.PLAYER_SPEED);
        }
        entity.getComponent(PhysicsComponent.class).setLinearVelocity(dir);
    }

    private LocalTimer gunTimer = FXGL.newLocalTimer();
    public void shoot(){
        if (gunTimer.elapsed(Duration.seconds(Config.SHOOT_DELAY))){
            Point2D shooter = entity.getCenter();
            Point2D shootDirection = aimDir();
            FXGL.play("gunfire.wav");
            SpawnData data = new SpawnData(shooter)
                    .put("direction", shootDirection);
            FXGL.spawn("bullet1", data);
            gunTimer.capture();
        }


    }

    public Point2D aimDir(){
        Point2D cursorPoint = FXGL.getInput().getMousePositionWorld();
        Point2D player1 = entity.getCenter();
        Point2D aimDirection = cursorPoint.subtract(player1).normalize();
        return aimDirection;
    }
}
