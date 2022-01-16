package top.tonxin.www.chainofresponsibility;

import top.tonxin.www.AbstractGameObject;

/**
 * @Auther: ZHAO
 * @Date: 2022/1/15 - 01 - 15 - 11:44
 * @Description: top.tonxin.www
 * @version: 1.0
 */
public interface Collider {
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2);
}
