package testPackage.components;

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityGroup;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.ImagesKt;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import kotlin.Lazy;
import testPackage.Config;
import testPackage.McDonalismType;

import java.util.List;

public class Player1Component extends Component {

    private Point2D zero = new Point2D(0,0);
    private Point2D position = new Point2D(42,90);



//    private List<Entity> borders = FXGL.getGameWorld().getEntitiesByType(McDonalismType.BORDER);

    private AnimatedTexture anim;

    private AnimationChannel
            idle, move;

    private PhysicsComponent physics;

    public void move(Point2D v){
        Point2D dir = new Point2D(0,0);
        if (!v.equals(zero)) {
            dir = v.normalize().multiply(Config.PLAYER_SPEED);
        }
        physics.setLinearVelocity(dir);
    }



    public Point2D aimDir(){
        Point2D cursorPoint = FXGL.getInput().getMousePositionWorld();
        Point2D player1 = entity.getCenter();
        Point2D aimDirection = cursorPoint.subtract(player1).normalize();
        return aimDirection;
    }

    public Player1Component() {


        String assetName = "";

        Image image = FXGL.image("TestSheet2.png");

        idle = new AnimationChannel(
                image,
                13,
                96,
                96,
                Duration.seconds(0.65*1.5),
                0,
                12);

        move = new AnimationChannel(
                image,
                13,
                96,
                96,
                Duration.seconds(0.4*1.5),
                13,
                20);

        anim = new AnimatedTexture(idle);
        anim.loop();
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(position);
        entity.getViewComponent().addChild(anim);
    }

    @Override
    public void onUpdate(double tpf) {
        if (aimDir().getX() < 0){
            entity.setScaleX(-1);
        }
        else{
            entity.setScaleX(1);
        }


        if (physics.isMoving()) {
            if (anim.getAnimationChannel() != move) {
                anim.loopAnimationChannel(move);
            }
        }
        else {
            if (anim.getAnimationChannel() != idle) {
                anim.loopAnimationChannel(idle);
            }
        }

    }
}


