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
import javafx.scene.image.Image;
import javafx.util.Duration;
import testPackage.Config;
import testPackage.McDonalismApp;
import testPackage.Transfer;

//component for enemy chicken

public class Enemy1Component extends Component {

    private final Point2D zero = new Point2D(0,0);
    private Point2D v = new Point2D(0,0);
    private final Point2D position = new Point2D(24,24);
    private AnimatedTexture anim;
    private AnimationChannel
            idle, move, hurt, dead;
    private boolean isHurt = false;
    private boolean isDead = false;
    private PhysicsComponent physics;

    private boolean isSingle = new Transfer().isSingle();

    public void move(Point2D v){
        if (!isDead && hurtTimer.elapsed(Duration.seconds(0.2))) {
            if (!v.equals(zero)) {
                this.v = v.normalize().multiply(Config.ENEMY1_SPEED);
            }
            else this.v = zero;
            physics.setLinearVelocity(this.v);
        }
        if (isDead)
            physics.setLinearVelocity(zero);
    }

    private final LocalTimer hurtTimer = FXGL.newLocalTimer();
    public boolean isHurt(){
        return isHurt;
    }
    public void getHurt(){

        isHurt = true;
        hurtTimer.capture();
    }

    public void die(){
        FXGL.play("enemy_die.wav");
        isDead = true;
        SpawnData data = new SpawnData(entity.getCenter().subtract(position).add(0,6));
        if (v.getX() < 0){
            data.put("scale", 1.0);
        }
        else {
            data.put("scale", -1.0);
        }
        FXGL.getWorldProperties().increment("score", 10);
        entity.removeFromWorld();
        FXGL.spawn("body", data);
    }

    public Enemy1Component() {

        Image sheet = FXGL.image("enemy/kun_white.png");

        idle = new AnimationChannel(
                sheet,
                4,
                48,
                48,
                Duration.seconds(4*0.1),
                4,
                7);

        move = new AnimationChannel(
                sheet,
                4,
                48,
                48,
                Duration.seconds(0.1),
                13,
                15);

        hurt = new AnimationChannel(
                sheet,
                4,
                48,
                48,
                Duration.seconds(0.4),
                1,
                1);

        dead = new AnimationChannel(
                sheet,
                4,
                48,
                48,
                Duration.seconds(1),
                11,
                11);

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
        if (isSingle)
            move(McDonalismApp.getPlayer1().getCenter().subtract(entity.getCenter()));
        else{
            if (entity.distanceBBox(McDonalismApp.getPlayer1()) < entity.distanceBBox(McDonalismApp.getPlayer2())){
                move(McDonalismApp.getPlayer1().getCenter().subtract(entity.getCenter()));
            } else {
                move(McDonalismApp.getPlayer2().getCenter().subtract(entity.getCenter()));
            }
        }

        if (!isDead && entity.getComponent(HealthIntComponent.class).isZero()){
            die();
        }
        else if (isDead){
            if(anim.getAnimationChannel() != dead)
                anim.playAnimationChannel(dead);
        }
        else {
            if (v.getX() < 0){
                entity.setScaleX(1);
            }
            else{
                entity.setScaleX(-1);
            }

            if (isHurt) {
                anim.loopAnimationChannel(hurt);
                physics.setLinearVelocity(physics.getLinearVelocity().multiply(0.5));
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
