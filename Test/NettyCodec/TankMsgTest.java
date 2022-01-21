package NettyCodec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.invoke.VarHandle;

/**
 * @Auther: S10711
 * @Date: 2022/1/21 - 01 - 21 - 21:15
 * @Description: NettyCodec
 * @version: 1.0
 * 编码解码测试类
 */
public class TankMsgTest {
    @Test
    void decode(){     //解码测试
        EmbeddedChannel ch = new EmbeddedChannel();
        ch.pipeline().addLast(new TankMsgDecoder());        //加入解码器
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(5);
        buf.writeInt(8);
        ch.writeInbound(buf);
        TankMsg tm = ch.readInbound();
        Assertions.assertEquals(5,tm.x);
        Assertions.assertEquals(8,tm.y);
    }
    @Test
    void encode(){      //编码测试
        EmbeddedChannel ch = new EmbeddedChannel();
        ch.pipeline().addLast(new TankMsgEncoder());        //加入编码器
        TankMsg tm = new TankMsg(5, 8);
        ch.writeOutbound(tm);
        ByteBuf buf = ch.readOutbound();
        int x = buf.readInt();
        int y = buf.readInt();
        Assertions.assertEquals(5,x);
        Assertions.assertEquals(8,y);
    }
}
