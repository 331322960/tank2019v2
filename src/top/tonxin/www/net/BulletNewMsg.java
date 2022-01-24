package top.tonxin.www.net;

import top.tonxin.www.Bullet;
import top.tonxin.www.Dir;
import top.tonxin.www.Group;
import top.tonxin.www.TankFrame;

import java.io.*;
import java.util.UUID;

/**
 * @Auther: S10711
 * @Date: 2022/1/23 - 01 - 23 - 10:13
 * @Description: top.tonxin.www.net
 * @version: 1.0
 */
public class BulletNewMsg extends Msg{
    private UUID playerId;
    private UUID id;
    private int x, y;
    private Dir dir;
    private Group group;

    public UUID getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Dir getDir() {
        return dir;
    }

    public Group getGroup() {
        return group;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public BulletNewMsg() {
    }

    public BulletNewMsg(Bullet bullet) {
        this.playerId = bullet.getPlayerId();
        this.id = bullet.getId();
        this.x = bullet.getX();
        this.y = bullet.getY();
        this.dir = bullet.getDir();
        this.group = bullet.getGroup();
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            dos.writeLong(playerId.getMostSignificantBits());     //写高位数据
            dos.writeLong(playerId.getLeastSignificantBits());    //写低位数据

            dos.writeLong(id.getMostSignificantBits());     //写高位数据
            dos.writeLong(id.getLeastSignificantBits());    //写低位数据

            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());

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
            this.playerId = new UUID(dis.readLong(),dis.readLong());
            this.id = new UUID(dis.readLong(),dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.group = Group.values()[dis.readInt()];
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
        if (this.playerId.equals(TankFrame.INSTANCE.getGm().getMyTank().getId()))
            return;
        Bullet bullet = new Bullet(this.playerId,x,y,dir,group);
        bullet.setId(id);
        TankFrame.INSTANCE.getGm().add(bullet);

    }

    @Override
    public MsgType getMsgType() {
        return MsgType.BulletNew;
    }

    @Override
    public String toString() {
        return "BulletNewMsg{" +
                "playerId=" + playerId +
                ", id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", group=" + group +
                '}';
    }
}
