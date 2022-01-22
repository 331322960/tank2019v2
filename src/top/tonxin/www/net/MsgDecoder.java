package top.tonxin.www.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @Auther: S10711
 * @Date: 2022/1/22 - 01 - 22 - 16:12
 * @Description: top.tonxin.www.net
 * @version: 1.0
 */
public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        if (buf.readableBytes() < 37) return;

        int length = buf.readInt();
        byte[] bytes = new byte[length];

        buf.readBytes(bytes);
        TankJoinMsg tjm = new TankJoinMsg();
        tjm.parse(bytes);
        list.add(tjm);
    }
}
