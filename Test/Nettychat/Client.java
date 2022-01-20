package Nettychat;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

/**
 * @Auther: S10711
 * @Date: 2022/1/20 - 01 - 20 - 11:29
 * @Description: Nettychat
 * @version: 1.0
 */
public class Client {
    private static Channel channel = null;

    public static void connect(){
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        Bootstrap b = new Bootstrap();
        try {
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    channel = socketChannel;
                    socketChannel.pipeline().addLast(new MyHandler());
                }
            });

            ChannelFuture future = b.connect("127.0.0.1", 8888).sync();

            //等待关闭
            future.channel().closeFuture().sync();
            System.out.println("go on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void send(String text) {
        channel.writeAndFlush(Unpooled.copiedBuffer(text.getBytes()));
    }

    public void closeConnection() {
        send("__bye__");
        channel.close();
    }

    static class MyHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = null;
            try {
                buf = (ByteBuf)msg;
                byte[] bytes = new byte[buf.readableBytes()];
                buf.getBytes(buf.readerIndex(), bytes);
                String str = new String(bytes);
                ClientFrame.INSTANCE.updateText(str);
                /*System.out.println(str);
                //查看多少个引用指向
                System.out.println(buf.refCnt());*/
            }finally {
                if (buf != null){
                    //释放buf
                    ReferenceCountUtil.release(buf);
                }
            }

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
