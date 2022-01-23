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
class TankStopMsgTest {

    @Test
    void encode() {
        EmbeddedChannel ch = new EmbeddedChannel();

        ch.pipeline().addLast(new MsgEncoder());        //加入编码器

        TankStopMsg msg = new TankStopMsg(UUID.randomUUID(),50,100);

        ch.writeOutbound(msg);

        ByteBuf buf = ch.readOutbound();
        MsgType msgType = MsgType.values()[buf.readInt()];
        int length = buf.readInt();

        UUID uuid = new UUID(buf.readLong(),buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();
        assertEquals(MsgType.TankStop,msgType);
        assertEquals(24,length);
        assertEquals(50,x);
        assertEquals(100,y);
        assertEquals(msg.getId(),uuid);

    }

    @Test
    void deCoder(){
        EmbeddedChannel ch = new EmbeddedChannel();
        ch.pipeline().addLast(new MsgDecoder());        //加入解码器

        UUID id = UUID.randomUUID();

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankStop.ordinal());
        buf.writeInt(24);
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());
        buf.writeInt(5);
        buf.writeInt(8);

        ch.writeInbound(buf);

        TankStopMsg msg = ch.readInbound();

        assertEquals(id,msg.getId());
        assertEquals(5,msg.getX());
        assertEquals(8,msg.getY());

    }
}