package Nettychat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Auther: S10711
 * @Date: 2022/1/20 - 01 - 20 - 11:29
 * @Description: Nettychat
 * @version: 1.0
 */
public class Server {
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static void main(String[] args) throws Exception {
        //负责接客
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        //负责服务
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        //Server启动辅助类
        ServerBootstrap b = new ServerBootstrap();

        b.group(bossGroup,workerGroup);
        //异步全双工
        b.channel(NioServerSocketChannel.class);
        //netty 帮我们内部处理了accept的过程
        b.childHandler(new ChannelInitializer<>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline().addLast(new MyChildHandler());
            }
        });
        ChannelFuture future = b.bind(8888).sync();

        future.channel().closeFuture().sync();

        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    static class MyChildHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //已经链接的时候拿到对应的channel进行保存
            Server.clients.add(ctx.channel());
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = null;
            buf = (ByteBuf)msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            String str = new String(bytes);
            if (str.equals("__bye__")){
                System.out.println("Client ready to quit !");
                System.out.println(Server.clients.size());           //查看多少个引用指向
                Server.clients.remove(ctx.channel());
                ctx.close();
            }else {
                Server.clients.writeAndFlush(buf);
            }
            /*System.out.println(str);
            //查看多少个引用指向
            System.out.println(buf.refCnt());*/
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            //出异常回收链接
            Server.clients.remove(ctx.channel());
            ctx.close();
        }
    }
}
