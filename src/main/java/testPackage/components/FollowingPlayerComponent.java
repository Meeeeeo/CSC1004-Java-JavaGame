package testPackage.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import testPackage.McDonalismType;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class FollowingPlayerComponent extends Component {
    @Override
    public void onUpdate(double tpf){
        Entity player = getGameWorld().getSingleton(McDonalismType.PLAYER);
        if(getEntity().getX() < player.getX()){
            getEntity().translateX(20*tpf);
        }
        else if (getEntity().getX() > player.getX()){
            getEntity().translateX(-20*tpf);
        }
        if(getEntity().getY() < player.getY()){
            getEntity().translateY(20*tpf);
        }
        else if (getEntity().getY() > player.getY()){
            getEntity().translateY(-20*tpf);
        }
    }

}
