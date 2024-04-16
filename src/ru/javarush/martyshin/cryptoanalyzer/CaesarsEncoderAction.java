package ru.javarush.martyshin.cryptoanalyzer;

import ru.javarush.martyshin.cryptoanalyzer.resources.Symbols;

import java.nio.file.Files;
import java.nio.file.Path;

public class CaesarsEncoderAction {
    private static final int BUFFER_SIZE = 8192;

    public void run(Path inputFilePath, int encodeDecodeKey, boolean isEncoding) {
        Path outputFilePath;
        if (isEncoding) {
            outputFilePath = FileManager.getResolvedOutputFilePath(inputFilePath, FileManager.ENCODED);
        } else {
            encodeDecodeKey *= -1;
            outputFilePath = FileManager.getResolvedOutputFilePath(inputFilePath, FileManager.DECODED);
        }
        try {
            String readString = Files.readString(inputFilePath);
            Files.writeString(outputFilePath, encodeDecode(readString, encodeDecodeKey));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String encodeDecode(String sourceString, int encodeDecodeKey) {
        StringBuffer codedString = new StringBuffer();
        for (int i = 0; i < sourceString.length(); i++) {
            char c = sourceString.charAt(i);
            c = Character.toLowerCase(c);
            int index = Symbols.indexOf(c);
            if (index >= 0) {
                int length = Symbols.ALL_SYMBOLS.length;
                int newIndex = (length + (index + encodeDecodeKey) % length) % length;
                codedString.append(Symbols.charAt(newIndex));
            } else {
                codedString.append(c);
            }

        }
        return codedString.toString();
    }

}