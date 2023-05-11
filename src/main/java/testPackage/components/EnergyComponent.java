package testPackage.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

public class EnergyComponent extends Component {
    int max;
    int inc;
    int dec;
    int rec;
    String number;
    public EnergyComponent(int max, int inc, int dec, int rec){
        this.max = max;
        this.inc = inc;
        this.dec = dec;
        this.rec = rec;
    }

    public void setPlayer(String s){
        number = s;
    }

    public void inc(){
        if (FXGL.getWorldProperties().getInt("mp" + number) + inc >= max)
            FXGL.set("mp" + number, max);
        else FXGL.inc("mp" + number, inc);
    }
    public void inc(int efficiency){
        if (FXGL.getWorldProperties().getInt("mp" + number) + efficiency >= max)
            FXGL.set("mp" + number, max);
        else FXGL.inc("mp" + number, efficiency);
    }

    public void dec(){
        if (FXGL.getWorldProperties().getInt("mp" + number) - dec <= 0)
            FXGL.set("mp" + number, 0);
        else FXGL.inc("mp" + number, -dec);
    }

    public int getMax(){
        setMax();
        return max;
    }

    public boolean isReady(){
        if (FXGL.geti("mp" + number) - dec >= 0)
            return true;
        else return false;
    }

    public void die(){
        FXGL.getWorldProperties().setValue("isAlive" + number, false);
    }

    public void setMax() {
        FXGL.getWorldProperties().setValue("mp" + number, max);
    }
}
