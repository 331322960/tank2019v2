package NettyCodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Auther: S10711
 * @Date: 2022/1/21 - 01 - 21 - 15:24
 * @Description: NettyCodec
 * @version: 1.0
 */
public class TankMsgEncoder extends MessageToByteEncoder<TankMsg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, TankMsg tankMsg, ByteBuf byteBuf) throws Exception {
        //System.out.println("Encoder:" + tankMsg);
        byteBuf.writeInt(tankMsg.x);
        byteBuf.writeInt(tankMsg.y);
    }
}
