package testPackage.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.ImagesKt;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import testPackage.Config;
import testPackage.McDonalismApp;
import testPackage.McDonalismType;

public class WeaponComponent extends Component {

    private Point2D position = new Point2D(48,48);

    private AnimatedTexture anim;

    private AnimationChannel
            idle, shoot;

    public WeaponComponent() {

        String assetName = "";

        Image image = FXGL.image("WeaponsSheet2.png");

        idle = new AnimationChannel(
                image,
                8,
                96,
                96,
                Duration.seconds(10),
                112,
                112);

        shoot = new AnimationChannel(
                image,
                8,
                96,
                96,
                Duration.seconds(0.1),
                112,
                114);

        anim = new AnimatedTexture(idle);
        anim.loop();
    }

    private LocalTimer gunTimer = FXGL.newLocalTimer();
    public void shoot(){
        if (gunTimer.elapsed(Duration.seconds(Config.SHOOT_DELAY))){
            Point2D shooter = entity.getCenter().add(position);
            Point2D shootDirection = FXGL.getGameWorld().getSingleton(McDonalismType.PLAYER).getComponent(Player1Component.class).aimDir();
            FXGL.play("gunfire.wav");
            anim.playAnimationChannel(shoot);
            SpawnData data = new SpawnData(shooter)
                    .put("direction", shootDirection);
            FXGL.spawn("bullet1", data);
            gunTimer.capture();

        }
    }

    public void idle(){
        anim.loopAnimationChannel(idle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(28, 32));
        entity.getViewComponent().addChild(anim);
    }

    @Override
    public void onUpdate(double tpf) {
        Point2D aimDir = FXGL.getGameWorld().getSingleton(McDonalismType.PLAYER).getComponent(Player1Component.class).aimDir();


        entity.getTransformComponent().setPosition(FXGL.getGameWorld().getSingleton(McDonalismType.PLAYER).getPosition());


        entity.setZIndex(FXGL.getGameWorld().getSingleton(McDonalismType.PLAYER).getZIndex() + 1);

        if (aimDir.getX() < 0){
            entity.setScaleX(-1);
        }
        else{
            entity.setScaleX(1);
        }

    }

}
