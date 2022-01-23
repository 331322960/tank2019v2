package top.tonxin.www.net;

import NettyCodec.TankMsg;
import NettyCodec.TankMsgDecoder;
import io.netty.bootstrap.ServerBootstrap;
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
                            channel.pipeline()
                                    //.addLast(new TankMsgDecoder())          //解码
                                    .addLast(new ServerChildHandler());
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

    class ServerChildHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //已经链接的时候拿到对应的channel进行保存
            clients.add(ctx.channel());
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //System.out.println(msg.toString());
            //TankMsg tm = (TankMsg)msg;
            ServerFrame.INSTANCE.updateClientMsg(msg.toString());
            clients.writeAndFlush(msg);

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
