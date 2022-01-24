package top.tonxin.www.net;
import top.tonxin.www.*;

import java.io.*;
import java.util.UUID;

/**
 * @Auther: S10711
 * @Date: 2022/1/22 - 01 - 22 - 15:04
 * @Description: top.tonxin.www.net
 * @version: 1.0
 */
public class TankJoinMsg extends Msg{
    private int x,y;
    private Dir dir;
    private boolean moving;
    private Group group;

    private UUID id;


    public TankJoinMsg() {
    }
    public TankJoinMsg(Player p) {
        this.x = p.getX();
        this.y = p.getY();
        this.moving = p.isMoving();
        this.dir = p.getDir();
        this.group = p.getGroup();
        this.id = p.getId();
    }

    public byte[] toBytes(){
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());        //下标值位于什么位置
            dos.writeBoolean(moving);
            dos.writeInt(group.ordinal());      //下标
            dos.writeLong(id.getMostSignificantBits());     //写高位数据
            dos.writeLong(id.getLeastSignificantBits());    //写低位数据
            dos.flush();
            bytes = baos.toByteArray();
            /*for (byte aByte : bytes) {
                System.out.print(aByte +",");
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (baos != null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return bytes;
    }

    @Override
    public String toString() {
        return "TankJoinMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", moving=" + moving +
                ", group=" + group +
                ", id=" + id +
                '}';
    }

    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.moving = dis.readBoolean();
            this.group = Group.values()[dis.readInt()];
            this.id = new UUID(dis.readLong(),dis.readLong());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void handle() {
        //if this msg's id equals my tank's id return
        //otherwise add new tank to my collection
        if (this.id.equals(TankFrame.INSTANCE.getGm().getMyTank().getId())) return;

        Tank t = new Tank(this);
        if (TankFrame.INSTANCE.getGm().findTankByUUID(this.id) != null) return;
        TankFrame.INSTANCE.getGm().add(t);

        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getGm().getMyTank()));
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankJoin;
    }
}
