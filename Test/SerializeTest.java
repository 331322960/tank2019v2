import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//序列化存盘
public class SerializeTest {
    @Test
    public void testSave(){
        try {
            T t = new T();
            File f = new File("c:/test/a.dat");
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(t);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoad() {
        try {

            File f = new File("c:/test/a.dat");
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            T t = (T)(ois.readObject());

            Assertions.assertEquals(3, t.a.weight);
            Assertions.assertEquals(0, t.n);
            Assertions.assertEquals(3, t.a.weight);

            ois.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class T implements Serializable {
    int m = 4;
    transient int n = 8; //password
    Apple a = new Apple();
}

class Apple implements Serializable {
    int weight = 3;
}
