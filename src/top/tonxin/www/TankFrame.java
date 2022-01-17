package top.tonxin.www;
import top.tonxin.www.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {

    private GanmeModel gm = new GanmeModel();
    public static final TankFrame INSTANCE = new TankFrame();
    public static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;            //游戏窗口高度和宽度

    private TankFrame() {
        //设置窗口是否显示
        this.setVisible(true);
        //设置窗口偏移和大小
        this.setLocation(200, 200);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        //监听键盘
        this.addKeyListener(new TankKeyListener());

    }







    @Override
    public void paint(Graphics g) {
        gm.paint(g);
    }

    /*双缓冲解决闪烁*/
    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    private class TankKeyListener extends KeyAdapter {

        //按键被按下
        @Override
        public void keyPressed(KeyEvent e) {
            //交给坦克自己处理
            gm.getMyTank().keyPressed(e);
        }

        //按键抬起
        @Override
        public void keyReleased(KeyEvent e) {
            gm.getMyTank().keyReleased(e);
        }
    }
    public GanmeModel getGm(){
        return this.gm;
    }
}

