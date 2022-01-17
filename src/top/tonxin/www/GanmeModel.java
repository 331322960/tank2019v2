package top.tonxin.www;

import top.tonxin.www.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: ZHAO
 * @Date: 2022/1/17 - 01 - 17 - 14:38
 * @Description: top.tonxin.www
 * @version: 1.0
 */
public class GanmeModel {
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
        for (int i = 0; i < objects.size(); i++) {
            if(!objects.get(i).isLive()){
                objects.remove(i);
                break;
            }

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

        /*for (int i = 0; i < tanks.size(); i++) {
            if (!tanks.get(i).isLive()) {
                tanks.remove(i);
            } else {
                tanks.get(i).paint(g);
            }
        }
        for (int i = 0; i < bullets.size(); i++) {
            *//*碰撞检测*//*
            for (int j = 0; j < tanks.size(); j++) {
                bullets.get(i).collidesWithTank(tanks.get(j));
            }
            if (!bullets.get(i).isLive()) {
                bullets.remove(i);
            } else {
                bullets.get(i).paint(g);
            }
        }
        *//*爆炸*//*
        for (int i = 0; i < explodes.size(); i++) {
            if (!explodes.get(i).isLive()) {
                explodes.remove(i);
            } else {
                explodes.get(i).paint(g);
            }
        }*/
    }
    public Player getMyTank(){
        return myTank;
    }
}
