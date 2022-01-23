package top.tonxin.www.net;

/**
 * @Auther: S10711
 * @Date: 2022/1/23 - 01 - 23 - 10:15
 * @Description: top.tonxin.www.net
 * @version: 1.0
 */
public abstract class Msg {
    public abstract byte[] toBytes();
    public abstract void parse(byte[] bytes);
    public abstract void handle();
    public abstract MsgType getMsgType();

}
