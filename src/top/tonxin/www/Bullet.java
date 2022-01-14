package top.tonxin.www;

import java.awt.*;

/**
 * @Auther: ZHAO
 * @Date: 2021/12/23 - 12 - 23 - 15:07
 * @Description: top.tonxin.tank
 * @version: 1.0
 */
public class Bullet extends AbstractGameObject{
    public static final int SPEED = 10;
    private int x,y;
    private Dir dir;
    private Group group;
    private boolean live = true; //子弹状态


    public Bullet(int x, int y, Dir dir, Group group) {
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

    public void paint(Graphics g) {
        switch (dir) {
            case R:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case L:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
        }
        move();
    }

    private void move() {
        switch (dir){
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
        boundsCheck();

    }

    private void boundsCheck() {
        if (x < 0 || y < 30 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            live = false;
        }
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    /*碰撞检测*/
    public void collidesWithTank(Tank tank){
        /*如果坦克挂了就直接return*/
        if (!this.isLive() || !tank.isLive()) return;
        /*防止自己打自己*/
        if (this.group == tank.getGroup()) return;
        /*碰撞检测*/
        Rectangle rect = new Rectangle(x,y,ResourceMgr.bulletD.getWidth(),ResourceMgr.badTankD.getHeight());
        Rectangle rectTank = new Rectangle(tank.getX(),tank.getY(),ResourceMgr.goodTankD.getWidth(),ResourceMgr.goodTankD.getHeight());
        /*撞上就把子弹和坦克销毁*/
        if(rect.intersects(rectTank)){
            this.die();
            tank.die();
        }
    }

    /*子弹消亡*/
    public void die(){
        this.setLive(false);
    }
}
