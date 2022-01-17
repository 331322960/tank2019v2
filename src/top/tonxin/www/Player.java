package top.tonxin.www;

import top.tonxin.www.strategy.FireStrategy;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends AbstractGameObject{
    public static final int SPEED = 5;     //移动速度
    private int x,y;
    private Dir dir;
    private Rectangle rect;
    private boolean bL,bR,bU,bD;
    private boolean moving = false;   //移动状态
    private boolean live = true;      //坦克存活状态
    private int oldX,oldY;
    private Group group;
    private int width,height;

    public Player(int x, int y, Dir dir,Group group){
        this.x = x;
        this.y = y;

        oldX = x;
        oldY = y;

        this.dir = dir;
        this.group = group;

        this.width = ResourceMgr.goodTankD.getWidth();
        this.height = ResourceMgr.goodTankD.getHeight();

        rect = new Rectangle(x,y,width,height);
        this.initFireStrategy();
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void back() {
        this.x = oldX;
        this.y = oldY;
    }

    public void paint(Graphics g) {

        /*Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("x：" + this.rect.x, 10, 70);
        g.drawString("y：" + this.rect.y, 10, 90);
        g.setColor(c);*/

        if (!this.live) return;
        switch (dir) {
            case L:
                g.drawImage(ResourceMgr.goodTankL, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.goodTankU, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.goodTankR, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.goodTankD, x, y, null);
                break;
        }
        move();

        rect.x = x;
        rect.y = y;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        setMainDir();
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_CONTROL:
                fire();
                break;
        }
        setMainDir();
    }

    private void setMainDir() {
        //没有按键按下为flase
        if (!bL && !bU && !bD && !bR)
            moving = false;
        else {
            moving = true;
            if (bL && !bU && !bD && !bR)
                dir = Dir.L;
            if (!bL && bU && !bD && !bR)
                dir = Dir.U;
            if (!bL && !bU && bD && !bR)
                dir = Dir.D;
            if (!bL && !bU && !bD && bR)
                dir = Dir.R;
        }

    }

    private void move() {
        if (!moving) return;
        oldX = x;
        oldY = y;
        switch (dir) {
            case L:
                x -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case D:
                y += SPEED;
                break;
            case U:
                y -= SPEED;
                break;
        }
        boundsCheck();          //边界检测
    }

    //读取策略模式
    FireStrategy strategy = null;
    private void initFireStrategy(){
        /*ClassLoader loader = Player.class.getClassLoader();*/
        String className = PropertyMgr.get("tankFireStrategy");
        try {
            /*Class clazz = loader.loadClass("top.tonxin.www.strategy."+className);    //效果等同于Class.forName */
            Class clazz = Class.forName("top.tonxin.www.strategy."+className);
            strategy = (FireStrategy) (clazz.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void fire(){
        strategy.fire(this);
    }

    /*坦克消亡*/
    public void die(){
        this.setLive(false);
    }

    public Rectangle getRect() {
        return rect;
    }

    /*边界检测*/
    private void boundsCheck() {
        if (x < 0 || y < 30 || x + width > TankFrame.GAME_WIDTH || y + height > TankFrame.GAME_HEIGHT) {
            this.back();
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", rect=" + rect +
                ", bL=" + bL +
                ", bR=" + bR +
                ", bU=" + bU +
                ", bD=" + bD +
                ", moving=" + moving +
                ", live=" + live +
                ", oldX=" + oldX +
                ", oldY=" + oldY +
                ", group=" + group +
                ", width=" + width +
                ", height=" + height +
                ", strategy=" + strategy +
                '}';
    }
}
