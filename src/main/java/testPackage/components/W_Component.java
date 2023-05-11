package testPackage.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import testPackage.Config;

public class W_Component extends Component {

    private final Point2D zero = new Point2D(0,0);
    private Point2D dir = new Point2D(0,0);
    private double xDir = 1.0;
    private final Point2D position = new Point2D(42,90);
    private AnimatedTexture anim;
    private AnimationChannel
            idle, move, hurt, dead, summon, recover;
    private boolean isHurt = false;
    private boolean isDead = false;
    private boolean isSummoning = false;
    private boolean isRecovering = false;
    private PhysicsComponent physics;


    public void move(Point2D v){
        if (!isDead && hurtTimer.elapsed(Duration.seconds(0.2))) {
            if (isRecovering || isSummoning){
                dir = zero;
                physics.setLinearVelocity(dir);
            }
            else if (!v.equals(zero)) {
                dir = v.normalize().multiply(Config.W_SPEED);
            }
            else dir = zero;
            physics.setLinearVelocity(dir);
        }
        if (isDead)
            physics.setLinearVelocity(zero);
    }

    private final LocalTimer hurtTimer = FXGL.newLocalTimer();
    private final LocalTimer summonTimer = FXGL.newLocalTimer();
    private final LocalTimer recoverTimer = FXGL.newLocalTimer();
    private final LocalTimer energyTimer = FXGL.newLocalTimer();
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

    public void die(){
        isDead = true;
        entity.removeComponent(PhysicsComponent.class);
        entity.getComponent(EnergyComponent.class).die();
    }

    public void summon(boolean b){
        energyTimer.capture();
        summonTimer.capture();
        isSummoning = b;
     }

    public void recover(boolean b){
        recoverTimer.capture();
        isRecovering = b;
    }
    public W_Component() {

        Image image_idle = FXGL.image("hero/W_idle.png");
        Image image_run = FXGL.image("hero/W_run.png");
        Image image_hurt = FXGL.image("hero/W_hurt.png");
        Image image_attack = FXGL.image("hero/W_attack.png");
        Image image_recover = FXGL.image("hero/W_recover.png");

        idle = new AnimationChannel(
                image_idle,
                5,
                96,
                96,
                Duration.seconds(5*0.1),
                0,
                4);

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
                2,
                96,
                96,
                Duration.seconds(1),
                0,
                1);

        dead = new AnimationChannel(
                FXGL.image("hero/W_dead.png"),
                1,
                96,
                96,
                Duration.seconds(1),
                0,
                0);

        summon = new AnimationChannel(
                image_attack,
                4,
                96,
                96,
                Duration.seconds(0.4),
                0,
                3);

        recover = new AnimationChannel(
                image_recover,
                3,
                96,
                96,
                Duration.seconds(0.2),
                0,
                1);

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
        if (isDead){
            if(anim.getAnimationChannel() != dead)
                anim.playAnimationChannel(dead);
        }
        else {
            if (dir.getX() < 0){
                entity.setScaleX(-1);
                xDir = -1.0;
            }
            else if (dir.getX() > 0){
                entity.setScaleX(1);
                xDir = 1.0;
            }

            if (isHurt) {
                anim.loopAnimationChannel(hurt);
                physics.setLinearVelocity(physics.getLinearVelocity().multiply(0.9));
                if (hurtTimer.elapsed(Duration.seconds(0.2))){
                    isHurt = false;
                }
            } else {
                if(isSummoning){
                    if (energyTimer.elapsed(Duration.seconds(0.4))){
                        entity.getComponent(EnergyComponent.class).dec();
                        energyTimer.capture();
                    }
                    if (entity.getComponent(EnergyComponent.class).isReady())
                        FXGL.getGameWorld().getEntitiesInRange(
                                new Rectangle2D(
                                        entity.getX()-100,
                                        entity.getY()-100,
                                        entity.getRightX()+100,
                                        entity.getBottomY()+100))
                                .forEach(entity1 -> {
                                    if (summonTimer.elapsed(Duration.seconds(0.2))
                                            && entity1.hasComponent(Enemy1Component.class)
                                            && (entity.distanceBBox(entity1) <= 100)){
                                        summonTimer.capture();
                                        FXGL.spawn("magic", new SpawnData(entity1.getCenter().add(-48, -48)));
                                        entity1.getComponent(Enemy1Component.class).getHurt();
                                        entity1.getComponent(HealthIntComponent.class).damage(Config.W_DAMAGE);
                                        FXGL.play("W_summon.wav");
                                    }
                                });

                    if (anim.getAnimationChannel() != summon)
                        anim.loopAnimationChannel(summon);
                }
                else if(isRecovering){
                    if (recoverTimer.elapsed(Duration.seconds(0.2))){
                        entity.getComponent(EnergyComponent.class).inc();
                        recoverTimer.capture();
                    }
                    if (anim.getAnimationChannel() != recover){
                        anim.loopAnimationChannel(recover);
                    }
                }else {
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