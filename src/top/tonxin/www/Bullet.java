package top.tonxin.www;

import java.awt.*;
import java.util.UUID;

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
    public static final int w = ResourceMgr.bulletU.getWidth();
    public static final int h = ResourceMgr.bulletU.getHeight();
    private Rectangle rect;  //碰撞rect
    private UUID id = UUID.randomUUID();
    private UUID playerId;

    public Bullet(UUID playerId,int x, int y, Dir dir, Group group) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        rect = new Rectangle(x, y, w, h);
    }

    public static int getSPEED() {
        return SPEED;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Dir getDir() {
        return dir;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPlayerId() {
        return playerId;
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
        //update the rect
        rect.x = x;
        rect.y = y;
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


    public Rectangle getRect(){
        return rect;
    }
    /*子弹消亡*/
    public void die(){
        this.setLive(false);
    }
}
