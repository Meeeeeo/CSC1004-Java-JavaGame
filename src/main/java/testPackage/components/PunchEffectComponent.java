package testPackage.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class PunchEffectComponent extends Component {

    private AnimatedTexture anim;
    private AnimationChannel effect;

    public PunchEffectComponent() {
        effect = new AnimationChannel(
                FXGL.image("hero/effect.png"),
                3,
                24,
                24,
                Duration.seconds(0.2),
                0,
                2);

        anim = new AnimatedTexture(effect);
        anim.loop();
    }
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(anim);
    }
}
