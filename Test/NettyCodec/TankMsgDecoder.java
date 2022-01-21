package NettyCodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * @Auther: S10711
 * @Date: 2022/1/21 - 01 - 21 - 15:29
 * @Description: NettyCodec
 * @version: 1.0
 */
public class TankMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        /*拆包*/
        if (buf.readableBytes() < 8) return;
        /*先读x*/
        int x = buf.readInt();
        int y = buf.readInt();

        out.add(new TankMsg(x,y));
    }
}
