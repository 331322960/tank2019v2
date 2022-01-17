package top.tonxin.www.chainofresponsibility;

import top.tonxin.www.AbstractGameObject;
import top.tonxin.www.Bullet;
import top.tonxin.www.Tank;

import java.awt.*;

/**
 * @Auther: ZHAO
 * @Date: 2022/1/15 - 01 - 15 - 11:47
 * @Description: top.tonxin.www.chainofresponsibility
 * @version: 1.0
 * 坦克和坦克碰撞逻辑
 */
public class TankTankCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if(go1 != go2 && go1 instanceof Tank && go2 instanceof Tank){
            Tank t1 = (Tank) go1;
            Tank t2 = (Tank) go2;
            /*碰撞检测：*/
            if (t1.isLive() && t2.isLive()){
                if (t1.getRect().intersects(t2.getRect())){
                    t1.back();
                    t2.back();
                }
            }
        }
        return true;
    }
}
