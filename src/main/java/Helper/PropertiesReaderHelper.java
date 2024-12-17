package Helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReaderHelper {
    private static Properties properties = new Properties();
    static {
        try {
            var input = new FileInputStream("src/main/resources/application.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getUrl() {
        return properties.getProperty("url");
    }

    public static String getUrlWs() {
        return properties.getProperty("urlws");
    }

    public static String getAuthHeader() {
        return properties.getProperty("authorization");
    }

}