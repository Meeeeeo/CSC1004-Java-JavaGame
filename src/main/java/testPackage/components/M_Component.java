package testPackage.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import testPackage.Config;

public class M_Component extends Component {

    private final Point2D zero = new Point2D(0,0);
    private Point2D dir = new Point2D(0,0);
    private final Point2D position = new Point2D(42,90);
    private AnimatedTexture anim;
    private AnimationChannel
            idle, move, hurt, dead;
    private boolean isHurt = false;
    private boolean isDead = false;
    private PhysicsComponent physics;

    public void move(Point2D v){
        if (!isDead && hurtTimer.elapsed(Duration.seconds(0.2))) {
            if (!v.equals(zero)) {
                dir = v.normalize().multiply(Config.M_SPEED);
            }
            else dir = zero;
            physics.setLinearVelocity(dir);
        }
        if (isDead)
            physics.setLinearVelocity(zero);
    }

    private final LocalTimer hurtTimer = FXGL.newLocalTimer();
    public boolean isHurt(){
        return isHurt;
    }
    public void getHurt(Point2D hit){
        if (!isDead){
            isHurt = true;
//            FXGL.play("getHurt.wav");
            hurtTimer.capture();
        }
        physics.setLinearVelocity(hit.normalize().multiply(720));
    }

    public void die(){
        isDead = true;
        entity.removeComponent(PhysicsComponent.class);
        entity.getComponent(EnergyComponent.class).die();
    }

    public Point2D aimDir(){
        Point2D cursorPoint = new Point2D(FXGL.getInput().getMouseXWorld(), FXGL.getInput().getMouseYWorld());
        Point2D player1 = entity.getCenter();
        return cursorPoint.subtract(player1).normalize();
    }

    public M_Component() {

        Image image_idle = FXGL.image("hero/M_idle.png");
        Image image_run = FXGL.image("hero/M_run.png");
        Image image_hurt = FXGL.image("hero/M_hurt.png");

        idle = new AnimationChannel(
                image_idle,
                4,
                96,
                96,
                Duration.seconds(4*0.1),
                0,
                3);

        move = new AnimationChannel(
                image_run,
                6,
                96,
                96,
                Duration.seconds(6*0.1),
                0,
                5);

        hurt = new AnimationChannel(
                image_hurt,
                1,
                96,
                96,
                Duration.seconds(1),
                0,
                0);

        dead = new AnimationChannel(
                FXGL.image("hero/M_dead.png"),
                1,
                96,
                96,
                Duration.seconds(1),
                0,
                0);

        anim = new AnimatedTexture(idle);
        anim.loop();
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(position);
        entity.getViewComponent().addChild(anim);
    }

    private LocalTimer energyTimer = FXGL.newLocalTimer();

    @Override
    public void onUpdate(double tpf) {
        if (isDead){
            if(anim.getAnimationChannel() != dead)
                anim.playAnimationChannel(dead);
        }
        else {
            if (energyTimer.elapsed(Duration.seconds(5))){
                entity.getComponent(EnergyComponent.class).inc();
                energyTimer.capture();
            }
            if (aimDir().getX() < 0){
                entity.setScaleX(-1);
            }
            else{
                entity.setScaleX(1);
            }

            if (isHurt) {
                anim.loopAnimationChannel(hurt);
                physics.setLinearVelocity(physics.getLinearVelocity().multiply(0.9));
                if (hurtTimer.elapsed(Duration.seconds(0.2))){
                    isHurt = false;
                }
            } else {
                if (physics.isMoving()) {
                    if (anim.getAnimationChannel() != move) {
                        anim.loopAnimationChannel(move);
                    }
                } else {
                    if (anim.getAnimationChannel() != idle) {
                        anim.loopAnimationChannel(idle);
                    }
                }
            }
        }
    }
}


