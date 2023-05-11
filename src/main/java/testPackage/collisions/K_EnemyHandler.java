package testPackage.collisions;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;
import testPackage.Config;
import testPackage.McDonalismType;
import testPackage.components.K_Component;

//enable enemy to hurt Sanders

public class K_EnemyHandler extends CollisionHandler {
    public K_EnemyHandler() {super(McDonalismType.PLAYER_K,McDonalismType.ENEMY);}
    @Override
    protected void onCollisionBegin(Entity player, Entity enemy) {
        super.onCollisionBegin(player, enemy);
        K_Component component = player.getComponent(K_Component.class);
        if (!component.isHurt()){
            if (!component.hasPunched() || player.getPosition().subtract(enemy.getPosition()).getX()* component.det() >= 0)
            {
                HealthIntComponent hp = player.getComponent(HealthIntComponent.class);
                hp.damage(Config.ENEMY1_DAMAGE);
                FXGL.getGameScene().getViewport().shake(3*Config.ENEMY1_DAMAGE, 0.5);
                component.getHurt(player.getCenter().subtract(enemy.getCenter()));
                if (!hp.isZero()) FXGL.play("K_hurt.wav");
                else{
                    FXGL.play("K_die.wav");
                    LocalTimer timer = FXGL.newLocalTimer();
                    timer.capture();
                    timer.elapsed(Duration.seconds(0.2));
                    component.die();
                }
            }
        }
    }
}
