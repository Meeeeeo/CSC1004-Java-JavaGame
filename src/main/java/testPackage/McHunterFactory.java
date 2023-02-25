package testPackage;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import testPackage.components.PlayerAnimationComponent;


public class McHunterFactory implements EntityFactory {

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(McHunterType.PLAYER)
                .at(300, 300)
                .bbox(BoundingShape.box(20,20))
                .with(new CollidableComponent(true))
                .with(new PlayerAnimationComponent())
                //.neverUpdated()
                .build();
    }

    ;

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(McHunterType.ENEMY)
                .view("brick.png")
                .build();
    }


}
