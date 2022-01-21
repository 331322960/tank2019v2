package NettyCodec;

import javax.sound.midi.Soundbank;

/**
 * @Auther: S10711
 * @Date: 2022/1/21 - 01 - 21 - 15:14
 * @Description: NettyCodec
 * @version: 1.0
 */
public class TankMsg {
    public int x,y;

    public TankMsg(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return "TankMsg{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
