package testPackage.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import testPackage.McDonalismType;

//remove bullet when it hits border
public class BulletBorderHandler  extends CollisionHandler {

    public BulletBorderHandler() {
        super(McDonalismType.BULLET,McDonalismType.BORDER );
    }

    @Override
    protected void onCollision(Entity bullet, Entity border) {
        super.onCollisionBegin(bullet, border);
        bullet.removeFromWorld();
    }
}
