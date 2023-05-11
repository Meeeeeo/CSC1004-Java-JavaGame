package testPackage.collisions;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;
import testPackage.Config;
import testPackage.McDonalismType;
import testPackage.components.W_Component;

//enable enemy to hurt Wiz
public class W_EnemyHandler extends CollisionHandler {
    public W_EnemyHandler() {super(McDonalismType.PLAYER_W,McDonalismType.ENEMY);}
    @Override
    protected void onCollisionBegin(Entity player, Entity enemy) {
        super.onCollisionBegin(player, enemy);
        W_Component component = player.getComponent(W_Component.class);
        if (!component.isHurt()){
            HealthIntComponent hp = player.getComponent(HealthIntComponent.class);
            hp.damage(Config.ENEMY1_DAMAGE);
            FXGL.getGameScene().getViewport().shake(3*Config.ENEMY1_DAMAGE, 0.5);
            component.getHurt(player.getCenter().subtract(enemy.getCenter()));
            if (!hp.isZero()) FXGL.play("W_hurt.wav");
            else{
                FXGL.play("W_die.wav");
                LocalTimer timer = FXGL.newLocalTimer();
                timer.capture();
                timer.elapsed(Duration.seconds(0.2));
                component.die();
            }
        }
    }
}
