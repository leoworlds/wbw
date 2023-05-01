package leo.main.config;

import java.io.IOException;

public class Config {

    private static final String FILE_NAME = "/config.properties";

    private static Config config;

    private Props props;

    public static Config config() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    private Config() {
        try {
            props = new Props();
            props.load(this.getClass().getResourceAsStream(FILE_NAME));
        } catch (IOException e) {
            throw new IllegalStateException("Config can't be loaded from the file " + FILE_NAME);
        }
    }

    public Props getProps() {
        return props;
    }

    public static Integer getComplete() {
        return Integer.valueOf(Config.config().getProps().getProperty("complete", "100"));
    }
}
