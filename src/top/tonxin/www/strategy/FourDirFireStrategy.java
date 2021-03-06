package top.tonxin.www.strategy;

import top.tonxin.www.*;

/**
 * @Auther: ZHAO
 * @Date: 2022/1/14 - 01 - 14 - 12:46
 * @Description: top.tonxin.tank.strategy
 * @version: 1.0
 */
public class FourDirFireStrategy implements FireStrategy{
    @Override
    public void fire(Player p) {
        int bX = p.getX() + ResourceMgr.goodTankD.getWidth()/2 - ResourceMgr.bulletD.getWidth()/2;
        int bY = p.getY() + ResourceMgr.goodTankD.getHeight()/2 - ResourceMgr.bulletD.getHeight()/2;
        Dir[] dirs = Dir.values();
        for(Dir d : dirs)
            TankFrame.INSTANCE.getGm().add(new Bullet(p.getId(),bX,bY,d,p.getGroup()));
    }
}
