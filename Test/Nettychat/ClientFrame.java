package Nettychat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Auther: S10711
 * @Date: 2022/1/20 - 01 - 20 - 11:31
 * @Description: Nettychat
 * @version: 1.0
 */
public class ClientFrame extends Frame {
    public static final ClientFrame INSTANCE = new ClientFrame();
    private TextField ta = new TextField();
    private TextField tf = new TextField();
    Client c = null;

    public ClientFrame(){
        this.setSize(300,400);
        this.setLocation(200,200);
        /*设置显示位置*/
        this.add(ta,BorderLayout.CENTER);
        this.add(tf,BorderLayout.SOUTH);
        this.setTitle("tonxin.top");
        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.send(tf.getText());
                //ta.setText(ta.getText() + tf.getText() + "\r\n");
                tf.setText("");
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                c.closeConnection();
                System.exit(0);
            }
        });
    }
    public void connectToServer(){
        c = new Client();
        c.connect();
    }

    public static void main(String[] args) {
        ClientFrame f = ClientFrame.INSTANCE;
        f.setVisible(true);
        f.connectToServer();    //链接服务器
    }

    public void updateText(String str) {
        ta.setText(ta.getText() + str + "\r\n");
    }
}
