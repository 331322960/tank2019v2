package top.tonxin.www.net;

import top.tonxin.www.Dir;
import top.tonxin.www.Tank;
import top.tonxin.www.TankFrame;

import java.io.*;
import java.util.UUID;

/**
 * @Auther: S10711
 * @Date: 2022/1/23 - 01 - 23 - 10:13
 * @Description: top.tonxin.www.net
 * @version: 1.0
 */
public class TankStopMsg extends Msg{
    private UUID id;
    private int x, y;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public TankStopMsg() {
    }

    public TankStopMsg(UUID id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            dos.writeLong(id.getMostSignificantBits());     //写高位数据
            dos.writeLong(id.getLeastSignificantBits());    //写低位数据
            dos.writeInt(x);
            dos.writeInt(y);
            dos.flush();
            bytes = baos.toByteArray();

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
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.id = new UUID(dis.readLong(),dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
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

    @Override
    public void handle() {
        if (this.id.equals(TankFrame.INSTANCE.getGm().getMyTank().getId()))
            return;
        Tank t = TankFrame.INSTANCE.getGm().findTankUUID(this.id);
        if (t != null){
            t.setMoving(false);
            t.setX(this.x);
            t.setY(this.y);
        }

    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStop;
    }

    @Override
    public String toString() {
        return "TankStopMsg{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
