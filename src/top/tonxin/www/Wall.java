package top.tonxin.www;

import java.awt.*;

/**
 * @Auther: ZHAO
 * @Date: 2022/1/14 - 01 - 14 - 14:50
 * @Description: top.tonxin.www
 * @version: 1.0
 */
public class Wall extends AbstractGameObject{
    private int x,y,w,h;
    private Rectangle rect;

    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        rect = new Rectangle(x,y,w,h);
    }

    public void paint(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect(x,y,w,h);
        g.setColor(c);
    }

    @Override
    public boolean isLive() {
        return true;
    }
    public Rectangle getRect() {
        return rect;
    }

    @Override
    public String toString() {
        return "Wall{" +
                "x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                ", rect=" + rect +
                '}';
    }
}
