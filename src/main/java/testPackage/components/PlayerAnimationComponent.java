package testPackage.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class PlayerAnimationComponent extends Component {

    private int[] speed = new int[2];

    private boolean yMove = false;
    private boolean xMove = false;

    private AnimatedTexture player;
    private AnimationChannel
            idleUp, idleDown, idleLeft, idleRight,
            walkUp, walkDown, walkLeft, walkRight,
            runUp, runDown, runLeft, runRight;

    public PlayerAnimationComponent() {
        idleUp = new AnimationChannel(
                FXGL.image("player.png"),
                8,
                512/8,
                512/8,
                Duration.seconds(0.5),
                8,
                8);
        idleDown = new AnimationChannel(
                FXGL.image("player.png"),
                8,
                512/8,
                512/8,
                Duration.seconds(0.5),
                0,
                0);
        idleLeft = new AnimationChannel(
                FXGL.image("player.png"),
                8,
                512/8,
                512/8,
                Duration.seconds(0.5),
                24,
                24);
        idleRight = new AnimationChannel(
                FXGL.image("player.png"),
                8,
                512/8,
                512/8,
                Duration.seconds(0.5),
                16,
                16);

        walkDown = new AnimationChannel(
                FXGL.image("player.png"),
                8,
                512/8,
                512/8,
                Duration.seconds(0.5),
                32,
                37);
        walkUp = new AnimationChannel(
                FXGL.image("player.png"),
                8,
                512/8,
                512/8,
                Duration.seconds(0.5),
                40,
                45);
        walkLeft = new AnimationChannel(
                FXGL.image("player.png"),
                8,
                512/8,
                512/8,
                Duration.seconds(0.5),
                56,
                61);
        walkRight = new AnimationChannel(
                FXGL.image("player.png"),
                8,
                512/8,
                512/8,
                Duration.seconds(0.5),
                48,
                53);

        player = new AnimatedTexture(idleDown);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(32, 16));
        entity.getViewComponent().addChild(player);
    }

    @Override
    public void onUpdate(double tpf) {
        entity.translateX(speed[1] * tpf);
        entity.translateY(speed[0] * tpf);

        if (speed[1] == 0) {
            if (speed[0] > 0){

                if (player.getAnimationChannel() == idleDown) {
                    player.loopAnimationChannel(walkDown);
                }

                speed[0] = (int) (speed[0] * 0.5);

                if (FXGLMath.abs(speed[0]) < 1) {
                    speed[0] = 0;
                    player.loopAnimationChannel(idleDown);
                }
            }
            else if (speed[0] < 0) {

                if (player.getAnimationChannel() == idleUp) {
                    player.loopAnimationChannel(walkUp);
                }

                speed[0] = (int) (speed[0] * 0.5);

                if (FXGLMath.abs(speed[0]) < 1) {
                    speed[0] = 0;
                    player.loopAnimationChannel(idleUp);
                }
            }
        } else if (speed[1] < 0) {
            if (player.getAnimationChannel() == idleLeft) {
                player.loopAnimationChannel(walkLeft);
            }

            speed[1] = (int) (speed[0] * 0.5);

            if (FXGLMath.abs(speed[1]) < 1) {
                speed[1] = 0;
                player.loopAnimationChannel(idleLeft);
            }
        }
        else if (speed[1] > 0) {
            if (player.getAnimationChannel() == idleRight) {
                player.loopAnimationChannel(walkRight);
            }

            speed[1] = (int) (speed[1] * 0.5);

            if (FXGLMath.abs(speed[1]) < 1) {
                speed[1] = 0;
                player.loopAnimationChannel(idleRight);
            }
        }
    }

    public void walkRight() {
        speed[1] = 150;
    }

    public void walkLeft() {
        speed[1] = -150;
    }

    public void walkUp() {
        speed[0] = -150;
    }

    public void walkDown() {
        speed[0] = 150;
    }
}