package testPackage;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import testPackage.components.*;


public class McDonalismFactory implements EntityFactory {

    @Spawns("player1")
    public Entity newPlayer(SpawnData data) {
        HealthIntComponent hp = new HealthIntComponent(100);
            hp.setValue(100);
            ProgressBar hpBar = new ProgressBar();
            hpBar.maxValueProperty().bind(hp.maxValueProperty());
            hpBar.currentValueProperty().bind(hp.valueProperty());
        return FXGL.entityBuilder(data)
                .type(McDonalismType.PLAYER)
                .at(400, 300)
                .viewWithBBox(new Rectangle(30,30, Color.PINK))
                .collidable()
                .with(hp)
                .with(new PlayerMoveComponent())
                .with(new InjuredComponent())
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
                .with(new FollowingPlayerComponent())
                .build();
    }

    @Spawns("weapon1")
    public Entity newWeapon(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(McDonalismType.WEAPON)

                .build();
    }

    @Spawns("bullet1")
    public Entity newBullet(SpawnData data) {
        Point2D direction = data.get("direction");
        return FXGL.entityBuilder(data)
                .type(McDonalismType.BULLET)
                .viewWithBBox(new Rectangle(10,10,Color.WHITE))
                .collidable()
                .with(new ProjectileComponent(direction, 1000))
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
