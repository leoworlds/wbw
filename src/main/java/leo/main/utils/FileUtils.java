package leo.main.utils;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@UtilityClass
public class FileUtils {

    private static final String PATH = "src/main/resources/";

    @SneakyThrows
    public static List<String> read(String fileName) {
        return Files.readAllLines(Paths.get(PATH, fileName));
    }

    @SneakyThrows
    public static void save(String fileName, List<String> lines) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(PATH + fileName), UTF_8))) {
            lines.forEach(pw::println);
        }
    }

    public void playWord(String word) {
        new Thread(() -> {
            try (InputStream is = new BufferedInputStream(new FileInputStream(PATH + "sound/" + word + ".mp3"))) {
                new Player(is).play();
            } catch (Exception e) {}
        }).start();
    }
}
