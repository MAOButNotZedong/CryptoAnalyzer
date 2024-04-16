package ru.javarush.martyshin.cryptoanalyzer;

import ru.javarush.martyshin.cryptoanalyzer.resources.Symbols;

import java.io.*;
import java.nio.file.Path;

public class CaesarsEncoderAction{
    private static final int BUFFER_SIZE = 8192;
    public void run(Path inputFilePath, int encodeDecodeKey, boolean isEncoding) {
        Path outputFilePath;
        if (isEncoding) {
            outputFilePath = FileManager.getResolvedOutputFilePath(inputFilePath, FileManager.ENCODED);
        } else {
            encodeDecodeKey *= -1;
            outputFilePath = FileManager.getResolvedOutputFilePath(inputFilePath, FileManager. DECODED);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath.toFile()));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath.toFile()));
        ) {
            char[] buffer = new char[BUFFER_SIZE];
            while (br.ready()) {
                int validLength = br.read(buffer);
                bw.write(encode(buffer, validLength, encodeDecodeKey));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public char[] encode(char[] buffer, int validLength, int encodeDecodeKey) {
        char[] output = new char[validLength];
        for (int i = 0; i < validLength; i++) {
            char c = buffer[i];
            c = Character.toLowerCase(c);
            int index = Symbols.indexOf(c);
            if (index >= 0) {
                int length = Symbols.ALL_SYMBOLS.length;
                int newIndex = (length + (index + encodeDecodeKey) % length) % length;
                output[i] = Symbols.charAt(newIndex);
            } else {
                output[i] = c;
            }

        }
        return output;
    }

}