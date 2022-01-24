package top.tonxin.www.net;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;
import top.tonxin.www.Bullet;
import top.tonxin.www.Dir;
import top.tonxin.www.Group;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Auther: S10711
 * @Date: 2022/1/22 - 01 - 22 - 15:31
 * @Description: top.tonxin.www.net
 * @version: 1.0
 */
class BulletNewMsgTest {

    @Test
    void encode() {
        EmbeddedChannel ch = new EmbeddedChannel();

        ch.pipeline().addLast(new MsgEncoder());        //加入编码器

        UUID pid = UUID.randomUUID();
        Bullet b = new Bullet(pid,50,100,Dir.D, Group.BAD);
        System.out.println(pid);
        BulletNewMsg msg = new BulletNewMsg(b);

        ch.writeOutbound(msg);

        ByteBuf buf = ch.readOutbound();
        MsgType msgType = MsgType.values()[buf.readInt()];
        int length = buf.readInt();

        UUID PlayerId = new UUID(buf.readLong(),buf.readLong());
        UUID id = new UUID(buf.readLong(),buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];
        Group group = Group.values()[buf.readInt()];

        assertEquals(MsgType.BulletNew,msgType);
        assertEquals(48,length);
        assertEquals(b.getPlayerId(),PlayerId);
        assertEquals(b.getId(),id);
        assertEquals(50,x);
        assertEquals(100,y);
        assertEquals(Dir.D,dir);
        assertEquals(Group.BAD,group);

    }

    @Test
    void deCoder(){
        EmbeddedChannel ch = new EmbeddedChannel();
        ch.pipeline().addLast(new MsgDecoder());        //加入解码器

        UUID pid = UUID.randomUUID();
        UUID id = UUID.randomUUID();

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.BulletNew.ordinal());
        buf.writeInt(48);
        buf.writeLong(pid.getMostSignificantBits());
        buf.writeLong(pid.getLeastSignificantBits());
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());
        buf.writeInt(5);
        buf.writeInt(8);
        buf.writeInt(Dir.D.ordinal());
        buf.writeInt(Group.BAD.ordinal());
        ch.writeInbound(buf);

        BulletNewMsg msg = ch.readInbound();

        assertEquals(pid,msg.getPlayerId());
        assertEquals(id,msg.getId());
        assertEquals(5,msg.getX());
        assertEquals(8,msg.getY());
        assertEquals(Dir.D,msg.getDir());
        assertEquals(Group.BAD,msg.getGroup());

    }
}