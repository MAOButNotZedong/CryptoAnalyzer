package ru.javarush.martyshin.cryptoanalyzer;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileManager {
    public static final String PATH_RESOURCES = "src\\ru\\javarush\\martyshin\\cryptoanalyzer\\resources";
    public static final String SOURCE = "source";
    public static final String DECODED = "decoded";
    public static final String ENCODED = "encoded";
    public static final String BRUTE_FORCED = "brute_forced";
    public static ArrayList<Path> getFilesFromDirectory(Path path) {
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(path)) {
            ArrayList<Path> files = new ArrayList<>();
            for (Path path1 : paths) {
                if (Files.isRegularFile(path1)) {
                    files.add(path1);
                }
            }
            return files;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
