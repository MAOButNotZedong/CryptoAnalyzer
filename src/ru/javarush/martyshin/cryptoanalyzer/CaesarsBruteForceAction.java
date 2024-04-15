package ru.javarush.martyshin.cryptoanalyzer;

import ru.javarush.martyshin.cryptoanalyzer.resources.Dictionary;
import ru.javarush.martyshin.cryptoanalyzer.resources.Symbols;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

public class CaesarsBruteForceAction {
    private final int BUFFER_SIZE = 8192;
    private int bestKey = 0;
    private int numOfMatches = 0;
    private int firstKey = -1 * Symbols.ALL_SYMBOLS.length;
    private int lastKey = Symbols.ALL_SYMBOLS.length - 1;

    public void start(Path path) {
        Path fileName = path.getFileName();
        Path outputFile;
        Path outputPath = path.getParent().getParent().resolve(FileManager.BRUTE_FORCED);
        try {
            if (!Files.exists(outputPath)) {
                Files.createDirectory(outputPath);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        outputFile = outputPath.resolve(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile.toFile()));
        ) {
            char[] buffer = new char[BUFFER_SIZE];
            int validLength = br.read(buffer);
            CaesarsEncoderAction cea = new CaesarsEncoderAction();
            for (int key = firstKey; key < lastKey; key++) {
                String str = new String(cea.encode(buffer, validLength, key));
                int num = countMatches(str);
                if (num > numOfMatches) {
                    bestKey = key;
                    numOfMatches = num;
                }
            }
            bw.write(cea.encode(buffer, validLength, bestKey));
            while (br.ready()) {
                bw.write(cea.encode(buffer, validLength, bestKey));
            }
            bw.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int countMatches(String str) {
        int num = 0;
        num += countMatchesWithDictionary(Dictionary.RU_WORDS, str);
        num += countMatchesWithDictionary(Dictionary.EN_WORDS, str);
        return num;
    }

    private int countMatchesWithDictionary(HashSet<String> set, String str) {
        int num = 0;
        for (String word : set) {
            if (str.contains(word)) {
                num++;
            }
        }
        return num;
    }
}
