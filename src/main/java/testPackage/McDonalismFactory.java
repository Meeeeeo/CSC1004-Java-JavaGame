package testPackage;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.*;
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


                .build();
    }

    ;

    @Spawns("player1")
    public Entity newPlayer(SpawnData data) {
        HealthIntComponent hp = new HealthIntComponent(100);
            hp.setValue(100);
            ProgressBar hpBar = new ProgressBar();
            hpBar.maxValueProperty().bind(hp.maxValueProperty());
            hpBar.currentValueProperty().bind(hp.valueProperty());
        return FXGL.entityBuilder(data)
                .type(McDonalismType.PLAYER)
                .at(400, 400)
                .viewWithBBox(new Rectangle(30,30, Color.PINK))
                .collidable()
                .with(hp)
                .with(new ZIndexComponent())
                .with(new PlayerMoveComponent())
                .with(new InjuredComponent())
                .with(new AimingComponent())
                .with(new ShootComponent())


                //.neverUpdated()
                .build();
    }

    @Spawns("enemy1")
    public Entity newEnemy(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(McDonalismType.ENEMY)
                .viewWithBBox("brick.png")
                .collidable()
                .with(new ZIndexComponent())
                .with(new FollowingPlayerComponent())
                .build();
    }

    @Spawns("weapon1")
    public Entity newWeapon(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(McDonalismType.WEAPON)
                .view(new Rectangle(2,20, Color.GREEN))

                .build();
    }

    @Spawns("bullet1")
    public Entity newBullet(SpawnData data) {
        Point2D direction = data.get("direction");
        return FXGL.entityBuilder(data)
                .type(McDonalismType.BULLET)
                .viewWithBBox(new Rectangle(10,10,Color.WHITE))
                .collidable()
                .with(new ProjectileComponent(direction.add(FXGL.random(-0.1,0.1), FXGL.random(-0.1,0.1)), 1500))
//                .with(new OffscreenCleanComponent())

                .build();
    }

    @Spawns("wall")
    public Entity newWall(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(McDonalismType.WALL)

                .build();
    }

    ;


}
