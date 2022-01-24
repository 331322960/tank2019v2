package top.tonxin.www.net;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;
import top.tonxin.www.Dir;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Auther: S10711
 * @Date: 2022/1/22 - 01 - 22 - 15:31
 * @Description: top.tonxin.www.net
 * @version: 1.0
 */
class TankMoveOrDirChangeMsgTest {

    @Test
    void encode() {
        EmbeddedChannel ch = new EmbeddedChannel();

        ch.pipeline().addLast(new MsgEncoder());        //加入编码器

        TankMoveOrDirChangeMsg msg = new TankMoveOrDirChangeMsg(UUID.randomUUID(),50,100, Dir.D);

        ch.writeOutbound(msg);

        ByteBuf buf = ch.readOutbound();
        MsgType msgType = MsgType.values()[buf.readInt()];
        int length = buf.readInt();
        UUID uuid = new UUID(buf.readLong(),buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];

        assertEquals(MsgType.TankMoveOrDirChange,msgType);
        assertEquals(28,length);
        assertEquals(50,x);
        assertEquals(100,y);
        assertEquals(msg.getId(),uuid);
        assertEquals(Dir.D,dir);

    }

    @Test
    void decode() {
        EmbeddedChannel ch = new EmbeddedChannel();

        ch.pipeline().addLast(new MsgDecoder());   //加入解码器

        UUID id = UUID.randomUUID();

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankMoveOrDirChange.ordinal());
        buf.writeInt(28);
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());
        buf.writeInt(5);
        buf.writeInt(8);
        buf.writeInt(Dir.D.ordinal());


        ch.writeInbound(buf);

        TankMoveOrDirChangeMsg tm = ch.readInbound();

        assertEquals(id, tm.getId());
        assertEquals(5, tm.getX());
        assertEquals(8, tm.getY());
        assertEquals(Dir.D, tm.getDir());
    }
}