package testPackage;

import com.almasb.fxgl.core.math.FXGLMath;

//class used to transfer some data, making each class clearer.

public final class Transfer {
    private static boolean isSingle;
    public void setSingle(boolean b) {isSingle = b;}
    public boolean isSingle() {return isSingle;}

    private static int heroId;
    public int getHeroId() {return convertSingle(heroId);}
    public void setHeroId(int i) {heroId = i;}

    private static int heroId1;
    public int getHeroId1() {return convertPlayer1(heroId1);}
    public void setHeroId1(int i) {heroId1 = i;}

    private static int heroId2;
    public int getHeroId2() {return convertPlayer2(heroId2);}
    public void setHeroId2(int i) {heroId2 = i;}

    public int convertSingle(int i){
        int n;
        n = i % 4;
        if (n == 0) return FXGLMath.random(1,3);
        else if (n == 1 ||n == -3) return 1;
        else if (n == 2 ||n == -2) return 2;
        else return 3;
    }

    public int convertPlayer1(int i){
        int n;
        n = i % 2;
        if (n == 0) return 1;
        else return 1;
    }

    public int convertPlayer2(int i){
        int n;
        n = i % 3;
        if (n == 0) return FXGLMath.random(1,2);
        else if (n == 1 ||n == -2) return 1;
        else return 2;
    }

}
