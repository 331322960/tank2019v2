package top.tonxin.www.chainofresponsibility;

import top.tonxin.www.AbstractGameObject;
import top.tonxin.www.Bullet;
import top.tonxin.www.ResourceMgr;
import top.tonxin.www.Tank;

import java.awt.*;

/**
 * @Auther: ZHAO
 * @Date: 2022/1/15 - 01 - 15 - 11:47
 * @Description: top.tonxin.www.chainofresponsibility
 * @version: 1.0
 */
public class BulletTankCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if(go1 instanceof Bullet && go2 instanceof Tank){
            Bullet b = (Bullet)go1;
            Tank t = (Tank)go2;
                /*碰撞检测：*/
                {
                    /*如果坦克挂了就直接return*/
                    if (!b.isLive() || !t.isLive()) return false;
                    /*防止自己打自己*/
                    if (b.getGroup() == t.getGroup()) return false;
                    /*碰撞检测*/
                    //Rectangle rect = new Rectangle(x,y,ResourceMgr.bulletD.getWidth(),ResourceMgr.badTankD.getHeight());
                    Rectangle rectTank = t.getRect();
                    /*撞上就把子弹和坦克销毁*/
                    if (b.getRect().intersects(rectTank)) {
                        b.die();
                        t.die();
                        return false;
                    }
                }
        }else if (go2 instanceof Bullet && go1 instanceof Tank){
            collide(go2,go1);
        }
        return true;
    }
}
