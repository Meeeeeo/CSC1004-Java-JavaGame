package testPackage.components;

import com.almasb.fxgl.entity.component.Component;

//show if the dead body has been touched to avoid repeats.
public class BodyComponent extends Component {
    private boolean isTouched;
    public BodyComponent(){
    }
    public void setTouched(){
        isTouched = true;
    }
    public boolean isTouched(){
        return isTouched;
    }
    @Override
    public void onAdded() {
        isTouched = false;
    }
}
