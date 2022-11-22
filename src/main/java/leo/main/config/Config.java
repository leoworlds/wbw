package leo.main.config;

import java.io.IOException;
import java.util.Properties;

public class Config {

    private static final String FILE_NAME = "/config.properties";

    private static Config config;

    private Properties props;

    public static Config config() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    private Config() {
        try {
            props = new Properties();
            props.load(this.getClass().getResourceAsStream(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Config can't be loaded from the file " + FILE_NAME);
        }
    }

    public Properties getProps() {
        return props;
    }
}
