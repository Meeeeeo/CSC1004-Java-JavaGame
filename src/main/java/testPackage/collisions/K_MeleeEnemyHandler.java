package testPackage.collisions;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.CollisionHandler;
import testPackage.Config;
import testPackage.McDonalismType;
import testPackage.components.Enemy1Component;

public class K_MeleeEnemyHandler extends CollisionHandler {
    public K_MeleeEnemyHandler() {super(McDonalismType.MELEE_AREA,McDonalismType.ENEMY);}
    @Override
    protected void onCollisionBegin(Entity area, Entity enemy) {
        super.onCollisionBegin(area, enemy);
        HealthIntComponent hp = enemy.getComponent(HealthIntComponent.class);
        Enemy1Component component = enemy.getComponent(Enemy1Component.class);
        FXGL.spawn("effect", new SpawnData(enemy.getCenter().add(-16, -16)));
        FXGL.play("punch.wav");
        hp.damage(Config.K_DAMAGE);
        component.getHurt();
        if (hp.isZero()){
            component.die();
        }
    }
}
