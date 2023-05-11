package testPackage.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import testPackage.Config;

public class K_Component extends Component {

    private final Point2D zero = new Point2D(0,0);
    private Point2D dir = new Point2D(0,0);
    private final Point2D position = new Point2D(39,192);
    private AnimatedTexture anim;
    private AnimationChannel
            idle, move, hurt, dead, hit1, hit2;
    private boolean isHurt = false;
    private boolean isDead = false;
    private PhysicsComponent physics;
    private boolean hasPunched;
    public boolean hasPunched(){
        return hasPunched;
    }
    private double det;
    public double det(){
        return det;
    }
    private LocalTimer punchTimer = FXGL.newLocalTimer();

    public void move(Point2D v){
        if (!isDead && hurtTimer.elapsed(Duration.seconds(0.2))) {
            if (hasPunched){
                dir = v.normalize().multiply(0.2).add(det/4, 0).multiply(Config.K_SPEED);
                physics.setLinearVelocity(dir);
            }
            else if (!v.equals(zero)) {
                dir = v.normalize().multiply(Config.K_SPEED);
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
            hurtTimer.capture();
        }
        physics.setLinearVelocity(hit.normalize().multiply(720));
    }

    private boolean isHit1 = true;
    public void punch(double b){
        hasPunched = true;
        if (punchTimer.elapsed(Duration.seconds(0.35))){
            det = b;
            punchTimer.capture();
            if ( FXGLMath.random(0.0,1.0) < 0.3)
                isHit1 = true;
            else isHit1 = false;
            SpawnData data = new SpawnData(entity.getCenter().add(-det*15,-52));
            data.put("xDir", det);
            entity.getComponent(EnergyComponent.class).dec();
            FXGL.spawn("meleeArea", data);
        }
    }

    public void die(){
        isDead = true;
        entity.removeComponent(PhysicsComponent.class);
        entity.getComponent(EnergyComponent.class).die();
    }

    public K_Component() {

        Image image_idle = FXGL.image("hero/K_idle.png");
        Image image_run = FXGL.image("hero/K_run.png");
        Image image_hurt = FXGL.image("hero/K_hurt.png");
        Image image_hit1 = FXGL.image("hero/K_hit1.png");
        Image image_hit2 = FXGL.image("hero/K_hit2.png");

        idle = new AnimationChannel(
                image_idle,
                5,
                288,
                288,
                Duration.seconds(0.5),
                0,
                4);

        move = new AnimationChannel(
                image_run,
                8,
                288,
                288,
                Duration.seconds(0.8),
                0,
                7);

        hurt = new AnimationChannel(
                image_hurt,
                2,
                288,
                288,
                Duration.seconds(0.1),
                1,
                1);

        dead = new AnimationChannel(
                FXGL.image("hero/K_dead.png"),
                3,
                288,
                288,
                Duration.seconds(0.3),
                0,
                2);

        hit1 = new AnimationChannel(
                image_hit1,
                5,
                288,
                288,
                Duration.seconds(0.3),
                1,
                4);

        hit2 = new AnimationChannel(
                image_hit2,
                5,
                288,
                288,
                Duration.seconds(0.3),
                1,
                4);

        anim = new AnimatedTexture(idle);
        anim.loop();
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(position);
        entity.getViewComponent().addChild(anim);
        anim.setTranslateX(0);
        anim.setTranslateY(-96);
    }

    private LocalTimer energyTimer = FXGL.newLocalTimer();
    @Override
    public void onUpdate(double tpf) {
        if (isDead){
            if(anim.getAnimationChannel() != dead)
                anim.playAnimationChannel(dead);
        }
        else {
            if (energyTimer.elapsed(Duration.seconds(2))){
                entity.getComponent(EnergyComponent.class).inc();
                energyTimer.capture();
            }
            if (dir.getX() < 0){
                entity.setScaleX(-1);
            }
            else if (dir.getX() > 0){
                entity.setScaleX(1);
            }

            if (isHurt) {
                anim.loopAnimationChannel(hurt);
                physics.setLinearVelocity(physics.getLinearVelocity().multiply(0.9));
                if (hurtTimer.elapsed(Duration.seconds(0.2))){
                    isHurt = false;
                }
            } else {
                if (hasPunched){
                    if (isHit1) {
                        if (anim.getAnimationChannel() != hit1 && anim.getAnimationChannel() != hit2)
                            anim.playAnimationChannel(hit1);
                    }
                    else{
                        if(anim.getAnimationChannel() != dead && anim.getAnimationChannel() != hit2)
                            anim.playAnimationChannel(hit2);
                    }
                    if (punchTimer.elapsed(Duration.seconds(0.3))){
                        hasPunched = false;
                    }
                }
                else {
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
}


