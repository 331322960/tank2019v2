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
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        if (buf.readableBytes() < 8) return;
        buf.markReaderIndex();                                      //复位读指针
        MsgType msgType = MsgType.values()[buf.readInt()];          //枚举判断消息类型
        int length = buf.readInt();

        //判断消息完整性
        if (buf.readableBytes() < length){
            buf.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[length];
        buf.readBytes(bytes);

        Msg msg = null;
        msg = (Msg) Class.forName("top.tonxin.www.net." + msgType.toString() + "Msg")
                .getDeclaredConstructor()
                .newInstance();

        msg.parse(bytes);
        out.add(msg);           //发出去
    }
}
