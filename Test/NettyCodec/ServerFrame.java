package NettyCodec;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Auther: S10711
 * @Date: 2022/1/20 - 01 - 20 - 16:17
 * @Description: Nettychat
 * @version: 1.0
 */
public class ServerFrame extends Frame {
    public static final ServerFrame INSTANCE = new ServerFrame();

    TextArea taServer = new TextArea();
    TextArea taClient = new TextArea();

    private Server server = new Server();

    public ServerFrame(){
        this.setSize(800,600);
        this.setLocation(200,200);
        Panel p = new Panel(new GridLayout(1,2));
        p.add(taServer);
        p.add(taClient);
        this.add(p);

        taServer.setFont(new Font("Consolas",Font.PLAIN,25));
        taClient.setFont(new Font("Consolas",Font.PLAIN,25));

        this.updateClientMsg("Client:");
        this.updateServerMsg("Server:");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    public void updateServerMsg(String str){
        this.taServer.setText(taServer.getText() + str + System.getProperty("line.separator"));
    }
    public void updateClientMsg(String str){
        this.taClient.setText(taClient.getText() + str + System.getProperty("line.separator"));
    }
    public static void main(String[] args) {
        ServerFrame.INSTANCE.setVisible(true);
        //启动(可以另外起一个线程)
        ServerFrame.INSTANCE.server.serverStart();
    }
}
