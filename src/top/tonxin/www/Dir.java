package top.tonxin.www;

import java.util.Random;

public enum Dir {
    //public static final top.tonxin.www.Dir L;

    R,L,U,D;
    private static Random r = new Random();
    public static Dir randomDir(){
        return values()[r.nextInt(values().length)];
    }
}
