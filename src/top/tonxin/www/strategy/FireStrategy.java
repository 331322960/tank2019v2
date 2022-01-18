package top.tonxin.www.strategy;

import top.tonxin.www.Player;

import java.io.Serializable;

/**
 * @Auther: ZHAO
 * @Date: 2022/1/14 - 01 - 14 - 10:29
 * @Description: top.tonxin.tank.strategy
 * @version: 1.0
 */
public interface FireStrategy extends Serializable {
    public void fire(Player p);
}
