package top.tonxin.www;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {
    private Player myTank;
    List<AbstractGameObject> objects;
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
        initGameObjects();

    }

    /*初始化*/
    private void initGameObjects() {
        myTank = new Player(100, 100, Dir.D, Group.GOOD);
        objects =new ArrayList<>();
        //从配置文件读取
        int tankCount =Integer.parseInt(PropertMgr.get("initTankCount"));
        for (int i = 0; i < tankCount; i++) {
            this.add(new Tank(200 + 50 * i, 200, Dir.D, Group.BAD));
        }
        this.add(new Wall(200,200,200,300));
    }

    public void add(AbstractGameObject go) {
        objects.add(go);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        /*g.drawString("bullets：" + bullets.size(), 10, 50);
        g.drawString("enemies：" + tanks.size(), 10, 70);
        g.drawString("explodes：" + explodes.size(), 10, 90);*/
        g.setColor(c);
        myTank.paint(g);
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).paint(g);
        }
        /*for (int i = 0; i < tanks.size(); i++) {
            if (!tanks.get(i).isLive()) {
                tanks.remove(i);
            } else {
                tanks.get(i).paint(g);
            }
        }
        for (int i = 0; i < bullets.size(); i++) {
            *//*碰撞检测*//*
            for (int j = 0; j < tanks.size(); j++) {
                bullets.get(i).collidesWithTank(tanks.get(j));
            }
            if (!bullets.get(i).isLive()) {
                bullets.remove(i);
            } else {
                bullets.get(i).paint(g);
            }
        }
        *//*爆炸*//*
        for (int i = 0; i < explodes.size(); i++) {
            if (!explodes.get(i).isLive()) {
                explodes.remove(i);
            } else {
                explodes.get(i).paint(g);
            }
        }*/
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
            myTank.keyPressed(e);
        }

        //按键抬起
        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }
}

