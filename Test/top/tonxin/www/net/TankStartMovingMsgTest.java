package top.tonxin.www.net;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;
import top.tonxin.www.Dir;
import top.tonxin.www.Group;
import top.tonxin.www.Player;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Auther: S10711
 * @Date: 2022/1/22 - 01 - 22 - 15:31
 * @Description: top.tonxin.www.net
 * @version: 1.0
 */
class TankStartMovingMsgTest {

    @Test
    void encode() {
        EmbeddedChannel ch = new EmbeddedChannel();

        ch.pipeline().addLast(new MsgEncoder());        //加入编码器

        Player p = new Player(50,100, Dir.L, Group.BAD);
        TankJoinMsg tjm = new TankJoinMsg(p);

        ch.writeOutbound(tjm);
        ByteBuf buf = ch.readOutbound();

        MsgType msgType = MsgType.values()[buf.readInt()];
        int length = buf.readInt();

        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];
        UUID uuid = new UUID(buf.readLong(),buf.readLong());

        assertEquals(33,length);
        assertEquals(50,x);
        assertEquals(100,y);
        assertEquals(Dir.L,dir);
        assertEquals(p.getId(),uuid);

    }

    @Test
    void deCoder(){
        EmbeddedChannel ch = new EmbeddedChannel();
        ch.pipeline().addLast(new MsgDecoder());        //加入解码器

        UUID id = UUID.randomUUID();

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankJoin.ordinal());
        buf.writeInt(33);
        buf.writeInt(5);
        buf.writeInt(8);
        buf.writeInt(Dir.D.ordinal());
        buf.writeBoolean(true);
        buf.writeInt(Group.BAD.ordinal());
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());

        ch.writeInbound(buf);

        TankJoinMsg tm = ch.readInbound();

        assertEquals(5,tm.getX());
        assertEquals(8,tm.getY());
        assertEquals(Dir.D,tm.getDir());
        assertTrue(tm.isMoving());
        assertEquals(Group.BAD,tm.getGroup());
        assertEquals(id,tm.getId());

    }
}