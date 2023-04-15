package testPackage;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import testPackage.components.*;


public class McDonalismFactory implements EntityFactory {

    @Spawns("camera")
    public Entity newCamera(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(McDonalismType.CAMERA)
                .at(400, 400)
                .with(new CameraComponent())
                .with(new Player1Component())

                .build();
    }

    ;

    @Spawns("player1")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        HealthIntComponent hp = new HealthIntComponent(100);
            hp.setValue(100);
            ProgressBar hpBar = new ProgressBar();
            hpBar.maxValueProperty().bind(hp.maxValueProperty());
            hpBar.currentValueProperty().bind(hp.valueProperty());

        return FXGL.entityBuilder(data)
                .type(McDonalismType.PLAYER)
                .at(400, 400)
                .viewWithBBox(new Rectangle(48,54, Color.PINK))
                .collidable()
                .with(hp)
                .with(new DynamicDepthComponent())
                .with(new InjuredComponent())
                .with(new Player1Component())
                .with(physics)


                //.neverUpdated()
                .build();
    }

    @Spawns("enemy1")
    public Entity newEnemy(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(McDonalismType.ENEMY)
                .viewWithBBox("brick.png")
                .collidable()
                .with(new DynamicDepthComponent())
                .with(new FollowingPlayerComponent())
                .build();
    }

    @Spawns("weapon1")
    public Entity newWeapon(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(McDonalismType.WEAPON)
                .view(new Rectangle(2,20, Color.GREEN))
                .with(new WeaponComponent())


                .build();
    }

    @Spawns("bullet1")
    public Entity newBullet(SpawnData data) {
        Point2D direction = data.get("direction");
        return FXGL.entityBuilder(data)
                .type(McDonalismType.BULLET)
                .viewWithBBox(new Rectangle(10,10,Color.WHITE))
                .collidable()
                .with(new ProjectileComponent(direction.add(
                        Config.BULLET_ACCURACY*FXGL.random(-1,1),
                        Config.BULLET_ACCURACY*FXGL.random(-1,1)),
                        Config.BULLET_SPEED))

                .build();
    }

    @Spawns("wall")
    public Entity newWall(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(McDonalismType.WALL)
                .bbox(BoundingShape.box(data.get("width"), data.get("height")))

                .collidable()
                .neverUpdated()
                .build();
    }

    @Spawns("border")
    public Entity newBorder(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(McDonalismType.BORDER)
                .bbox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height")))

                .with(new PhysicsComponent())
                .neverUpdated()
                .build();
    }

    @Spawns("ghostWall")
    public Entity newGhostWall(SpawnData data) {
        int i = 0;
        if(!data.get("name").equals("")) i = Integer.parseInt(data.get("name"));
        return FXGL.entityBuilder(data)
                .type(McDonalismType.GHOSTWALL)
                .zIndex((int)data.getY() + data.<Integer>get("height") + i)
                .neverUpdated()


                .build();
    }


}
