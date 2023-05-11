package testPackage.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import testPackage.Config;
import testPackage.McDonalismType;

//enabling gun movement, bullet spawning

public class WeaponComponent extends Component {

    private Point2D muzzle = new Point2D(70,39);
    private Point2D rotateOrigin = new Point2D(22,40);
    private double dis = muzzle.subtract(rotateOrigin).magnitude()/10;

    private AnimatedTexture anim;

    private AnimationChannel
            idle, shoot;

    public WeaponComponent() {

        String assetName = "";

        Image image = FXGL.image("weapon/P90.png");

        idle = new AnimationChannel(
                image,
                3,
                96,
                96,
                Duration.seconds(10),
                0,
                0);

        shoot = new AnimationChannel(
                image,
                3,
                96,
                96,
                Duration.seconds(0.15),
                0,
                2);
        anim = new AnimatedTexture(idle);
        anim.loop();
    }

    private LocalTimer gunTimer = FXGL.newLocalTimer();

    public void shoot(){
        if (gunTimer.elapsed(Duration.seconds(Config.SHOOT_DELAY))){
            if (FXGL.getGameWorld().getSingleton(McDonalismType.PLAYER_M).getComponent(EnergyComponent.class).isReady()){
            double scaleY = 1.0;
            double scaleX = 1.0;
            if (FXGL.random(0,1) < 0.5) scaleY = -1.0;
            if (FXGL.random(0,1) < 0.5) scaleX = -1.0;
            Point2D shootDirection = FXGL.getGameWorld().getSingleton(McDonalismType.PLAYER_M).getComponent(M_Component.class).aimDir();
            Point2D shooter = entity.getCenter().add(rotateOrigin).add(shootDirection.multiply(dis));
            anim.playAnimationChannel(shoot);
            FXGL.getGameWorld().getSingleton(McDonalismType.PLAYER_M).getComponent(EnergyComponent.class).dec();
            SpawnData data = new SpawnData(shooter)
                    .put("direction", shootDirection)
                    .put("scaleX", scaleX)
                    .put("scaleY", scaleY);
            FXGL.spawn("bullet", data);
            FXGL.spawn("recoil").getComponent(RecoilComponent.class).recoil();
            gunTimer.capture();
            }
            else anim.playAnimationChannel(idle);
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
        Point2D aimDir = FXGL.getGameWorld().getSingleton(McDonalismType.PLAYER_M).getComponent(M_Component.class).aimDir();

        entity.getTransformComponent().setPosition(FXGL.getGameWorld().getSingleton(McDonalismType.PLAYER_M).getPosition().add(18,21));

        entity.setZIndex(FXGL.getGameWorld().getSingleton(McDonalismType.PLAYER_M).getZIndex() + 1);

        entity.rotateToVector(aimDir);

        if (aimDir.getX() < 0){
            entity.setScaleY(-1);
        }
        else{
            entity.setScaleY(1);
        }

    }

}
