package leo.main.config;

import leo.main.utils.FileUtils;
import lombok.SneakyThrows;

import java.io.FileOutputStream;
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

    @SneakyThrows
    private Config() {
        props = new Props();
        props.load(this.getClass().getResourceAsStream(FILE_NAME));
    }

    @SneakyThrows
    public void store() {
        props.store(new FileOutputStream(FileUtils.PATH + FILE_NAME), "Store before exit");
        System.out.println("props has been stored");
    }

    public Props getProps() {
        return props;
    }

    public static Integer getComplete() {
        return Integer.valueOf(Config.config().getProps().getProperty("complete", "100"));
    }

    public static Integer getMistakeMax() {
        return Integer.valueOf(Config.config().getProps().getProperty("mistake.max", "10"));
    }
}
