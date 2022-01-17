package top.tonxin.www.chainofresponsibility;

import top.tonxin.www.*;

/**
 * @Auther: ZHAO
 * @Date: 2022/1/16 - 01 - 16 - 16:12
 * @Description: top.tonxin.www.chainofresponsibility
 * @version: 1.0
 */
public class PlayerTankCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if(go1 instanceof Player && go2 instanceof Tank){
            Player t1 = (Player) go1;
            Tank t2 = (Tank) go2;
            /*碰撞检测：*/
            if (t1.isLive() && t2.isLive()){
                if (t1.getRect().intersects(t2.getRect())){
                    t1.back();
                    t2.back();
                }
            }
        }else if (go1 instanceof Tank && go2 instanceof Player){
            collide(go2,go1);
        }
        return true;
    }
}
