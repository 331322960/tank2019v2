package top.tonxin.tank;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {
    public static final int SPEED = 5;     //移动速度
    private int x;
    private int y;
    private Dir dir;
    private boolean bL,bR,bU,bD;
    private boolean moving = false;   //移动状态
    private boolean live = true;      //坦克存活状态
    private Group group;

    public Player(int x, int y, Dir dir,Group group){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void paint(Graphics g) {
        if (!live) return;
        if(group == Group.GOOD) {
            switch (dir) {
                case R:
                    g.drawImage(ResourceMgr.goodTankR, x, y, null);
                    break;
                case L:
                    g.drawImage(ResourceMgr.goodTankL, x, y, null);
                    break;
                case U:
                    g.drawImage(ResourceMgr.goodTankU, x, y, null);
                    break;
                case D:
                    g.drawImage(ResourceMgr.goodTankD, x, y, null);
                    break;
            }
        }
        if(group == Group.BAD) {
            switch (dir) {
                case R:
                    g.drawImage(ResourceMgr.badTankR, x, y, null);
                    break;
                case L:
                    g.drawImage(ResourceMgr.badTankL, x, y, null);
                    break;
                case U:
                    g.drawImage(ResourceMgr.badTankU, x, y, null);
                    break;
                case D:
                    g.drawImage(ResourceMgr.badTankD, x, y, null);
                    break;
            }
        }
        move();

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        setMainDir();
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_CONTROL:
                fire();
                break;
        }
        setMainDir();
    }

    private void setMainDir() {
        //没有按键按下为flase
        if (!bL && !bU && !bD && !bR)
            moving = false;
        else {
            moving = true;
            if (bL && !bU && !bD && !bR)
                dir = Dir.L;
            if (!bL && bU && !bD && !bR)
                dir = Dir.U;
            if (!bL && !bU && bD && !bR)
                dir = Dir.D;
            if (!bL && !bU && !bD && bR)
                dir = Dir.R;
        }

    }

    private void move() {
        if (!moving) return;
        switch (dir) {
            case L:
                x -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case D:
                y += SPEED;
                break;
            case U:
                y -= SPEED;
                break;
        }
    }

    private void fire(){
        int bX = x + ResourceMgr.goodTankD.getWidth()/2 - ResourceMgr.bulletD.getWidth()/2;
        int bY = y + ResourceMgr.goodTankD.getHeight()/2 - ResourceMgr.bulletD.getHeight()/2;
        TankFrame.INSTANCE.add(new Bullet(bX,bY,dir,group));
    }

    /*坦克消亡*/
    public void die(){
        this.setLive(false);
    }
}
