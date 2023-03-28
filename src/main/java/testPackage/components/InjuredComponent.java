package testPackage.components;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.component.Component;

public class InjuredComponent extends Component {

    @Override
    public void onAdded(){
        HealthIntComponent hic = new HealthIntComponent();
        hic.setMaxValue(100);

    }
}
