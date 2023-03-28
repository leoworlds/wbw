package leo.main.config;

import java.util.Properties;

public class Props extends Properties {
    
    public int getProperty(String key, int defaultValue) {
        return Integer.parseInt(getProperty(key, Integer.toString(defaultValue)));
    }
}
