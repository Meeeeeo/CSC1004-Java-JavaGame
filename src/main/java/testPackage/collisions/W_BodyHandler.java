package testPackage.collisions;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import testPackage.McDonalismType;
import testPackage.components.EnergyComponent;

//enable Wiz to recover by touching the bodies
public class W_BodyHandler extends CollisionHandler {

    public W_BodyHandler() {super(McDonalismType.PLAYER_W,McDonalismType.BODY);}
    @Override
    protected void onCollisionBegin(Entity player, Entity body) {
        super.onCollisionBegin(player, body);
        FXGL.play("pick.wav");
        body.getViewComponent().clearChildren();
        body.getViewComponent().addChild(FXGL.texture("enemy/kun_white_dead.png").darker());
        body.removeComponent(PhysicsComponent.class);
        player.getComponent(EnergyComponent.class).inc(10);
        FXGL.getWorldProperties().increment("score", 5);
    }
}
