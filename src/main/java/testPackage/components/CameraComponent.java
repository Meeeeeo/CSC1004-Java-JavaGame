package testPackage.components;

import com.almasb.fxgl.animation.AnimationBuilder;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.component.Component;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CameraComponent extends Component {

    private TranslateTransition ttB = new TranslateTransition();
    private TranslateTransition ttF = new TranslateTransition();

    private AnimationBuilder rb;


    public void recoilBack(int d, Duration t, Point2D v){

        Entity e = getEntity();
        

        FXGL.animationBuilder()
                .interpolator(Interpolators.EXPONENTIAL.EASE_IN())
                .duration(t)
                .translate(e)
                .from(new Point2D(0,0))
                .to(v)
                .build();


        ttB.setInterpolator(Interpolators.EXPONENTIAL.EASE_IN());
        ttB.setFromX(entity.getX());
        ttB.setFromY(entity.getY());
        ttB.setDuration(t);
        ttB.setToX(entity.getX() - v.getX()*d);
        ttB.setToY(entity.getY() - v.getY()*d);
        ttB.setOnFinished(actionEvent -> recoilForward(d, t, v));
        ttB.play();

        ttB.getNode();
    }

    public void recoilForward(int d, Duration t, Point2D v){

    }

    @Override
    public void onUpdate(double tpf) {

    }
}
