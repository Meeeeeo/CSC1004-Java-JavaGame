package testPackage.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class MagicComponent extends Component {

    private AnimatedTexture anim;
    private AnimationChannel magic;

    public MagicComponent() {
        magic = new AnimationChannel(
                FXGL.image("hero/magic.png"),
                4,
                96,
                96,
                Duration.seconds(0.2),
                0,
                3);

        anim = new AnimatedTexture(magic);
        anim.loop();
    }
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(anim);
    }
}
