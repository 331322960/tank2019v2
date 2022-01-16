package top.tonxin.www.chainofresponsibility;

import top.tonxin.www.AbstractGameObject;
import top.tonxin.www.Bullet;
import top.tonxin.www.Wall;

/**
 * @Auther: ZHAO
 * @Date: 2022/1/15 - 01 - 15 - 15:11
 * @Description: top.tonxin.www.chainofresponsibility
 * @version: 1.0
 */
public class BulletWallCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 instanceof Bullet && go2 instanceof Wall){
            Bullet b = (Bullet)go1;
            Wall w = (Wall) go2;
            if (b.isLive()){
                if (b.getRect().intersects(w.getRect())){
                    b.die();
                    return false;
                }
            }
        }else if (go1 instanceof Wall && go2 instanceof Bullet){
            return collide(go2,go1);
        }
        return true;
    }

}
