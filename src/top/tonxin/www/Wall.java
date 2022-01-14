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

    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void paint(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect(x,y,w,h);
        g.setColor(c);
    }
}
