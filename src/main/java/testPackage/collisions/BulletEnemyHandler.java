package testPackage.collisions;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import testPackage.Config;
import testPackage.McDonalismType;
import testPackage.components.Enemy1Component;

//enable bullet to hurt enemy
public class BulletEnemyHandler  extends CollisionHandler {

    public BulletEnemyHandler() {
        super(McDonalismType.BULLET, McDonalismType.ENEMY );
    }

    @Override
    protected void onCollision(Entity bullet, Entity enemy) {
        super.onCollisionBegin(bullet, enemy);

        HealthIntComponent hp = enemy.getComponent(HealthIntComponent.class);
        Enemy1Component component = enemy.getComponent(Enemy1Component.class);
        bullet.removeFromWorld();
        hp.damage(Config.BULLET_DAMAGE);
        component.getHurt();
        if (hp.isZero()){
            component.die();
        }
    }
}
