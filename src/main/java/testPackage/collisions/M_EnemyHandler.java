package testPackage.collisions;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;
import testPackage.Config;
import testPackage.McDonalismType;
import testPackage.components.M_Component;

//enable enemy to hurt McDonald

public class M_EnemyHandler extends CollisionHandler {

    public M_EnemyHandler() {super(McDonalismType.PLAYER_M,McDonalismType.ENEMY);}
    @Override
    protected void onCollisionBegin(Entity player, Entity enemy) {
        super.onCollisionBegin(player, enemy);
        M_Component component = player.getComponent(M_Component.class);
        if (!component.isHurt()){
            HealthIntComponent hp = player.getComponent(HealthIntComponent.class);
            hp.damage(Config.ENEMY1_DAMAGE);
            FXGL.getGameScene().getViewport().shake(3*Config.ENEMY1_DAMAGE, 0.5);
            component.getHurt(player.getCenter().subtract(enemy.getCenter()));
            if (!hp.isZero()) FXGL.play("M_Hurt.wav");
            else{
                FXGL.play("M_die.wav");
                LocalTimer timer = FXGL.newLocalTimer();
                timer.capture();
                timer.elapsed(Duration.seconds(0.2));
                component.die();
                if(!FXGL.getGameWorld().getEntitiesByType(McDonalismType.WEAPON).isEmpty())
                    FXGL.getGameWorld().getSingleton(McDonalismType.WEAPON).removeFromWorld();
            }
        }
    }
}
