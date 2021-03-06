package top.tonxin.www;

import java.awt.*;
import java.io.Serializable;

/**
 * @Auther: ZHAO
 * @Date: 2022/1/14 - 01 - 14 - 15:26
 * @Description: top.tonxin.www
 * @version: 1.0
 */
public abstract class AbstractGameObject implements Serializable {
    public abstract void paint(Graphics g);
    public abstract boolean isLive();
}
