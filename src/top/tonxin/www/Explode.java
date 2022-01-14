package top.tonxin.www;

import java.awt.*;

/**
 * @Auther: ZHAO
 * @Date: 2021/12/23 - 12 - 23 - 15:07
 * @Description: top.tonxin.tank
 * @version: 1.0
 * 爆炸效果
 */
public class Explode extends AbstractGameObject{
    public static final int SPEED = 10;
    private int x,y;
    private int width,height;  //爆炸宽度高度
    private int step = 0;
    private boolean live = true;  //爆炸状态

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        this.height = ResourceMgr.explodes[0].getHeight();
        this.width = ResourceMgr.explodes[0].getWidth();
        /*爆炸声音*/
        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    public void paint(Graphics g) {
        if (!live) return;
        g.drawImage(ResourceMgr.explodes[step], x, y, null);
        step++;
        if (step >= ResourceMgr.explodes.length) {
            this.die();
        }
    }

    private void die() {
        this.live = false;
    }
}
