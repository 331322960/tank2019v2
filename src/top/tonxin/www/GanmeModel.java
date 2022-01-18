package top.tonxin.www;

import org.junit.jupiter.api.Assertions;
import top.tonxin.www.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: ZHAO
 * @Date: 2022/1/17 - 01 - 17 - 14:38
 * @Description: top.tonxin.www
 * @version: 1.0
 */
public class GanmeModel implements Serializable{
    private Player myTank;
    ColliderChain chain = new ColliderChain();
    List<AbstractGameObject> objects;

    public GanmeModel() {
        initGameObjects();
    }
    /*初始化*/
    private void initGameObjects() {
        //玩家
        myTank = new Player(100, 100, Dir.D, Group.GOOD);
        //从配置文件读取
        objects = new ArrayList<>();
        //this.add(myTank);
        int tankCount =Integer.parseInt(PropertyMgr.get("initTankCount"));

        for (int i = 0; i < tankCount; i++) {
            this.add(new Tank(200 + 80 * i, 200, Dir.D, Group.BAD));
        }
        this.add(myTank);
        //this.add(new Wall(150,150,400,50));
    }
    public void add(AbstractGameObject go) {
        objects.add(go);
    }

    public void paint(Graphics g) {
        //System.out.println(objects.size());
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("objects：" + objects.size(), 10, 50);
        //g.drawString("bullets：" + bullets.size(), 10, 50);
        //g.drawString("enemies：" + tanks.size(), 10, 70);
        //g.drawString("explodes：" + explodes.size(), 10, 90);*//*
        g.setColor(c);


        myTank.paint(g);
        /*修复闪烁 : 死掉的先删除再开始碰撞逻辑*/
        for (int i = 0; i < objects.size(); i++) {
            if(!objects.get(i).isLive()){
                objects.remove(i);
                break;
            }
        }

        /*碰撞逻辑*/
        for (int i = 0; i < objects.size(); i++) {
            AbstractGameObject go1 = objects.get(i);
            for (int j = 0; j < objects.size(); j++) {
                AbstractGameObject go2 = objects.get(j);
                chain.collide(go1,go2);
            }

            //判断是是否活着
            if (objects.get(i).isLive()){
                objects.get(i).paint(g);
            }

        }
    }
    public Player getMyTank(){
        return myTank;
    }

}
