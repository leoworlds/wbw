package leo.main.utils;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import leo.main.dictionary.Dictionary;
import leo.main.dictionary.PropertyDictionary;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@UtilityClass
public class FileUtils {

    public static final String PATH = "src/main/resources/";

    @SneakyThrows
    public static List<String> read(String fileName) {
        return Files.readAllLines(Paths.get(PATH, fileName));
    }

    @SneakyThrows
    public static void save(String fileName, List<String> lines) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(PATH + fileName), UTF_8))) {
            lines.forEach(pw::println);
            System.out.println(lines.size() + " lines was saved to file " + fileName);
        }
    }

    public static void save(PropertyDictionary dictionary) {
        List<String> lines = dictionary.map.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + String.join(",", entry.getValue()))
                .sorted(String::compareTo)
                .collect(Collectors.toList());

        save("dictionary.txt", lines);
    }

    public void playWord(String word) {
        new Thread(() -> {
            try (InputStream is = new BufferedInputStream(new FileInputStream(PATH + "sound/" + word + ".mp3"))) {
                new Player(is).play();
            } catch (Exception e) {}
        }).start();
    }

//    @SneakyThrows
//    public AdvancedPlayer playWord(String word) {
//
//        InputStream is = null;
//        try {
//            is = new BufferedInputStream(new FileInputStream(PATH + "sound/" + word + ".mp3"));
//        } catch (FileNotFoundException e) {
//            is.close();
//        }
//
//        AdvancedPlayer player = new AdvancedPlayer(is);
//
//        player.setPlayBackListener(new PlaybackListener() {
//            @Override
//            public void playbackStarted(PlaybackEvent e) {
//                super.playbackStarted(e);
//            }
//
//            @Override
//            public void playbackFinished(PlaybackEvent e) {
//                e.getFrame();
//            }
//        });
//
//
//        InputStream finalIs = is;
//        new Thread(() -> {
//            try {
//                player.play(0, Integer.MAX_VALUE);
//            } catch (Exception e) {
//                try {
//                    finalIs.close();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            } finally {
//                try {
//                    finalIs.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//        return player;
//    }

    public void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
