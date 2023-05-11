package testPackage;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import testPackage.components.*;


public class McDonalismFactory implements EntityFactory {

    @Spawns("camera")
    public Entity newCamera(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(McDonalismType.CAMERA)
                .at(400, 400)
                .with(new CameraComponent())
                .build();
    }
    @Spawns("recoil")
    public Entity newRecoil(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(McDonalismType.RECOIL)
                .at(0,0)
                .with(new RecoilComponent())
                .with(new ExpireCleanComponent(Duration.seconds(2)))
                .build();
    }

    @Spawns("M")
    public Entity newM(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        return FXGL.entityBuilder(data)
                .type(McDonalismType.PLAYER_M)
                .at(412, 400)
                .collidable()
                .with(physics)
                .with(new DynamicDepthComponent())
                .with(new HealthIntComponent(Config.M_HP))
                .with(new M_Component())
                .with(new EnergyComponent(100, 10,1,1))
                .bbox(new HitBox(new Point2D(39,41), BoundingShape.box(27, 55)))
                .scaleOrigin(50,45)
                .build();
    }

    @Spawns("K")
    public Entity newK(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        return FXGL.entityBuilder(data)
                .type(McDonalismType.PLAYER_K)
                .at(400, 400)
                .collidable()
                .with(physics)
                .with(new DynamicDepthComponent())
                .with(new HealthIntComponent(Config.K_HP))
                .with(new K_Component())
                .with(new EnergyComponent(100, 10, 10, 5))
                .bbox(new HitBox(new Point2D(12,33), BoundingShape.box(51, 60)))
                .scaleOrigin(39,96)
                .build();
    }

    @Spawns("W")
    public Entity newW(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        return FXGL.entityBuilder(data)
                .type(McDonalismType.PLAYER_W)
                .at(412, 400)
                .collidable()
                .with(physics)
                .with(new DynamicDepthComponent())
                .with(new HealthIntComponent(Config.W_HP))
                .with(new W_Component())
                .with(new EnergyComponent(100,2,10,1))
                .bbox(new HitBox(new Point2D(39,41), BoundingShape.box(27, 55)))
                .scaleOrigin(50,45)
                .build();
        }

    @Spawns("enemy1")
    public Entity newEnemy(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        return FXGL.entityBuilder(data)
                .type(McDonalismType.ENEMY)
                .collidable()
                .with(physics)
                .with(new DynamicDepthComponent())
                .with(new HealthIntComponent(Config.ENEMY1_HP))
                .with(new Enemy1Component())
                .bbox(new HitBox(new Point2D(0,16), BoundingShape.box(48, 28)))
                .scaleOrigin(24,24)
                .build();
    }

    @Spawns("body")
    public Entity newBody(SpawnData data) {
        return FXGL.entityBuilder(data)
                .collidable()
                .type(McDonalismType.BODY)
                .viewWithBBox(FXGL.texture("enemy/kun_white_dead.png"))
                .scaleOrigin(24, 0)
                .scale(data.get("scale"),1)
                .zIndex((int)data.getY())
                .build();
    }


    @Spawns("weapon")
    public Entity newWeapon(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(McDonalismType.WEAPON)
                .with(new WeaponComponent())
                .rotationOrigin(32,48)
                .scaleOrigin(32,48)
                .build();
    }

    @Spawns("bullet")
    public Entity newBullet(SpawnData data) {
        Point2D direction = data.get("direction");
        FXGL.play("gunfire.wav");
        return FXGL.entityBuilder(data)
                .type(McDonalismType.BULLET)
                .viewWithBBox(FXGL.texture("weapon/McNugget.png"))
                .scaleOrigin(9,8)
                .rotationOrigin(9,8)
                .rotate(FXGLMath.atan2Deg(direction.getY(),direction.getX())-39.8)
                .scale(data.get("scaleX"),data.get("scaleY"))
                .collidable()
                .with(new ProjectileComponent(direction.add(
                        Config.BULLET_ACCURACY*FXGL.random(-1,1),
                        Config.BULLET_ACCURACY*FXGL.random(-1,1)),
                        Config.BULLET_SPEED))
                .with(new DynamicDepthComponent())
                .build();
    }

    @Spawns("meleeArea")
    public Entity newMeleeArea(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(McDonalismType.MELEE_AREA)
                .viewWithBBox(new Rectangle(3,90, Color.TRANSPARENT))
                .scaleOrigin(0,42)
                .rotationOrigin(0,42)
                .collidable()
                .with(new ProjectileComponent(new Point2D(data.get("xDir"),0), 700))
                .with(new ExpireCleanComponent(Duration.seconds(0.15)))
                .build();
    }

    @Spawns("magic")
    public Entity newMagic(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(McDonalismType.MAGIC)
                .with(new MagicComponent())
                .with(new ExpireCleanComponent(Duration.seconds(0.2)))
                .zIndex(10000)
                .build();
    }

    @Spawns("effect")
    public Entity newEffect(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(McDonalismType.MAGIC)
                .with(new PunchEffectComponent())
                .with(new ExpireCleanComponent(Duration.seconds(0.2)))
                .zIndex(10000)
                .build();
    }

    @Spawns("border")
    public Entity newBorder(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();

        return FXGL.entityBuilder(data)
                .type(McDonalismType.BORDER)
                .bbox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height")))
                .with(physics)
                .with(new BorderComponent(new Point2D(data.getX(), data.getY())))
                .collidable()
                .build();
    }

    @Spawns("ghostWall")
    public Entity newGhostWall(SpawnData data) {
        int i = 0;
        if(!data.get("name").equals(""))
            i = Integer.parseInt(data.get("name"));
        return FXGL.entityBuilder(data)
                .type(McDonalismType.GHOSTWALL)
                .zIndex((int)data.getY() + data.<Integer>get("height") + i)
                .neverUpdated()


                .build();
    }


}
