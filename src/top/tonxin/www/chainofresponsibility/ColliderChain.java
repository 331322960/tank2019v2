package top.tonxin.www.chainofresponsibility;

import top.tonxin.www.AbstractGameObject;
import top.tonxin.www.PropertyMgr;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: ZHAO
 * @Date: 2022/1/16 - 01 - 16 - 12:04
 * @Description: top.tonxin.www.chainofresponsibility
 * @version: 1.0
 */
public class ColliderChain implements Collider{
    private List<Collider> colliders;
    public ColliderChain() {
        initColliders();
    }

    /*配置文件读策略模式*/
    private void initColliders() {
        colliders = new ArrayList<>();
        String[] colliderNames = PropertyMgr.get("colliders").split(",");
        try {
            for(String name : colliderNames) {
                Class clazz = Class.forName("top.tonxin.www.chainofresponsibility." + name);
                Collider c = (Collider)(clazz.getConstructor().newInstance());
                colliders.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        for(Collider collider : colliders) {
            if(!collider.collide(go1, go2)) {
                return false;
            }
        }
        return true;
    }
}
