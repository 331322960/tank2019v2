package top.tonxin.www;

import java.awt.*;
import java.util.Random;

public class Tank extends AbstractGameObject{
    public static final int SPEED = 5;     //移动速度
    private int x;
    private int y;
    private Dir dir;
    private boolean bL,bR,bU,bD;
    private boolean moving = true;   //移动状态
    private boolean live = true;      //坦克存活状态
    private int oldX,oldY;
    private int width,height;


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    private Group group;

    public Tank(int x, int y, Dir dir,Group group){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        oldX = x;
        oldY = y;

        this.width = ResourceMgr.goodTankD.getWidth();
        this.height = ResourceMgr.goodTankD.getHeight();
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

    private void move() {
        if (!moving) return;

        oldX = x;
        oldY = y;

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
        boundsCheck();          //边界检测
        randomeDir();
        if (r.nextInt(100) > 90)
            fire();
    }

    private Random r = new Random();

    private void randomeDir() {
        if (r.nextInt(100) > 90)
            this.dir = Dir.randomDir();
    }

    private void fire(){
        int bX = x + ResourceMgr.goodTankD.getWidth()/2 - ResourceMgr.bulletD.getWidth()/2;
        int bY = y + ResourceMgr.goodTankD.getHeight()/2 - ResourceMgr.bulletD.getHeight()/2;
        TankFrame.INSTANCE.add(new Bullet(bX,bY,dir,group));        //爆炸
    }

    /*坦克消亡*/
    public void die(){
        this.setLive(false);
        TankFrame.INSTANCE.add(new Explode(x,y));
    }

    /*边界检测*/
    private void boundsCheck() {
        if (x < 0 || y < 30 || x + width > TankFrame.GAME_WIDTH || y + height > TankFrame.GAME_HEIGHT) {
            this.back();
        }
    }
    private void back() {
        this.x = oldX;
        this.y = oldY;
    }
}
