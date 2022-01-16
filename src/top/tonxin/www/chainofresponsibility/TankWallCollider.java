package top.tonxin.www.chainofresponsibility;

import top.tonxin.www.AbstractGameObject;
import top.tonxin.www.Bullet;
import top.tonxin.www.Tank;
import top.tonxin.www.Wall;

/**
 * @Auther: ZHAO
 * @Date: 2022/1/15 - 01 - 15 - 15:11
 * @Description: top.tonxin.www.chainofresponsibility
 * @version: 1.0
 */
public class TankWallCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 instanceof Tank && go2 instanceof Wall){
//            System.out.println(go1);
//            System.out.println(go2);
            Tank t = (Tank) go1;
            Wall w = (Wall) go2;
            if (t.isLive()){
                if (t.getRect().intersects(w.getRect())){
                    t.back();
                }
            }
        }else if (go1 instanceof Wall && go2 instanceof Bullet){
            collide(go2,go1);
        }
        return true;
    }
}
