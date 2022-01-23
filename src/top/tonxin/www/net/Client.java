package top.tonxin.www.net;

import NettyCodec.TankMsg;
import NettyCodec.TankMsgEncoder;
import Nettychat.ClientFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;
import top.tonxin.www.GanmeModel;
import top.tonxin.www.Tank;
import top.tonxin.www.TankFrame;

import javax.xml.stream.FactoryConfigurationError;

/**
 * @Auther: S10711
 * @Date: 2022/1/20 - 01 - 20 - 11:29
 * @Description: Nettychat
 * @version: 1.0
 */
public class Client {
    public static final Client INSTANCE = new Client();

    private static Channel channel = null;

    public Client() {
    }

    public void connect(){
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        Bootstrap b = new Bootstrap();
        try {
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    channel = socketChannel;
                    socketChannel.pipeline()
                            .addLast(new MsgEncoder())    //加一个管道过滤器转换Encode
                            .addLast(new MsgDecoder())
                            .addLast(new MyHandler());
                }
            });

            ChannelFuture future = b.connect("127.0.0.1", 8888).sync();
            System.out.println("connected to server!");
            //等待关闭
            future.channel().closeFuture().sync();
            System.out.println("go on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void send(Msg msg) {
        channel.writeAndFlush(msg);
    }

    public void closeConnection() {
        channel.close();
    }

    static class MyHandler extends SimpleChannelInboundHandler<Msg> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
            System.out.println(msg);
            msg.handle();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getGm().getMyTank()));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static void main(String[] args) {
        Client c = new Client();
        c.connect();
    }
}
