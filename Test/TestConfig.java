import java.io.IOException;
import java.util.Properties;
import java.util.zip.InflaterInputStream;

/**
 * @Auther: ZHAO
 * @Date: 2022/1/13 - 01 - 13 - 16:30
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class TestConfig {
    public static void main(String[] args) {
        Properties pops = new Properties();
        try {
            pops.load(TestConfig.class.getClassLoader().getResourceAsStream("Config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = (String)pops.get("initTankCount");
        System.out.println(s);
    }
}
