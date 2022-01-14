package top.tonxin.www;

import java.util.Random;

public enum Dir {
    R,L,U,D;
    private static Random r = new Random();
    public static Dir randomDir(){
        return values()[r.nextInt(values().length)];
    }
}
