package Nettychat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Auther: S10711
 * @Date: 2022/1/20 - 01 - 20 - 11:29
 * @Description: Nettychat
 * @version: 1.0
 */
public class Server {
    public  ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public void serverStart(){
        //负责接客
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        //负责服务
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);

        try {
            //Server启动辅助类
            ServerBootstrap b = new ServerBootstrap();
            //异步全双工
            ChannelFuture future = b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //netty 帮我们内部处理了accept的过程
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new MyChildHandler());
                        }
                    })
                    .bind(8888).sync();
            ServerFrame.INSTANCE.updateServerMsg("Server started!");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    class MyChildHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //已经链接的时候拿到对应的channel进行保存
            clients.add(ctx.channel());
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
                System.out.println(clients.size());           //查看多少个引用指向
                clients.remove(ctx.channel());
                ctx.close();
            }else {
                clients.writeAndFlush(buf);
            }
            /*System.out.println(str);
            //查看多少个引用指向
            System.out.println(buf.refCnt());*/
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            //出异常回收链接
            clients.remove(ctx.channel());
            ctx.close();
        }
    }
}
