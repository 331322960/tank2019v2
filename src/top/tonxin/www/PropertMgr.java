package top.tonxin.www;

import java.io.IOException;
import java.util.Properties;

/**
 * @Auther: ZHAO
 * @Date: 2022/1/13 - 01 - 13 - 16:43
 * @Description: top.tonxin.tank
 * @version: 1.0
 * 配置文件目录
 */
public class PropertMgr {
    private static Properties props ;
    static {
        try {
            props = new Properties();
            props.load(PropertMgr.class.getClassLoader().getResourceAsStream("Config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String get(String key){
        return (String)props.get(key);
    }

}
