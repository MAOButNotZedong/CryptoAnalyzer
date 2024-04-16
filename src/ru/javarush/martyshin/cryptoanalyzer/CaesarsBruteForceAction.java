package ru.javarush.martyshin.cryptoanalyzer;

import ru.javarush.martyshin.cryptoanalyzer.resources.Dictionary;
import ru.javarush.martyshin.cryptoanalyzer.resources.Symbols;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

public class CaesarsBruteForceAction {
    private final int BUFFER_SIZE = 8192;
    private int bestKey = 0;
    private int numOfMatches = 0;
    private int firstKey = -1 * Symbols.ALL_SYMBOLS.length;
    private int lastKey = Symbols.ALL_SYMBOLS.length - 1;

    public void run(Path inputFilePath) {
        Path outputFilePath = FileManager.getResolvedOutputFilePath(inputFilePath, FileManager.BRUTE_FORCED);
        try {
            String readString = Files.readString(inputFilePath);
            CaesarsEncoderAction cea = new CaesarsEncoderAction();
            for (int encodeDecodeKey = firstKey; encodeDecodeKey < lastKey; encodeDecodeKey++) {
                String decodedString = cea.encodeDecode(readString, encodeDecodeKey);
                int num = countMatches(decodedString);
                if (num > numOfMatches) {
                    bestKey = encodeDecodeKey;
                    numOfMatches = num;
                }
            }
            Files.writeString(outputFilePath, cea.encodeDecode(readString, bestKey));
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
