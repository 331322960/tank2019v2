package top.tonxin.www.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Auther: S10711
 * @Date: 2022/1/22 - 01 - 22 - 15:24
 * @Description: top.tonxin.www.net
 * @version: 1.0
 */
public class MsgEncoder extends MessageToByteEncoder<Msg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf buf) throws Exception {
        buf.writeInt(msg.getMsgType().ordinal());
        byte[] bytes = msg.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }
}
