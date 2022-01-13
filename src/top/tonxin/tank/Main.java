package top.tonxin.tank;

public class Main {
    public static void main(String[] args) {
        //背景音乐
        new Thread(()->new Audio("audio/war1.wav").loop()).start();
        for (;;){
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TankFrame.INSTANCE.repaint();
        }
    }
}
