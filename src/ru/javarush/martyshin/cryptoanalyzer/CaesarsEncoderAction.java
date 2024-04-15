package ru.javarush.martyshin.cryptoanalyzer;

import ru.javarush.martyshin.cryptoanalyzer.FileManager;
import ru.javarush.martyshin.cryptoanalyzer.resources.Symbols;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CaesarsEncoderAction{
    public void start(Path path, int key, boolean isEncoding) {
        Path fileName = path.getFileName();
        Path outputFile;
        Path outputPath;
        if (isEncoding) {
            outputPath = path.getParent().getParent().resolve(FileManager.ENCODED);
        } else {
            key *= -1;
            outputPath = path.getParent().getParent().resolve(FileManager.DECODED);
        }
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
            char[] buffer = new char[8192];
            while (br.ready()) {
                int validLength = br.read(buffer);
                bw.write(encode(buffer, validLength, key));
            }
            bw.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public char[] encode(char[] inputBuffer, int validLength, int key) {
        char[] output = new char[validLength];
        for (int i = 0; i < validLength; i++) {
            char c = inputBuffer[i];
            c = Character.toLowerCase(c);
            int index = Symbols.indexOf(c);
            if (index >= 0) {
                int length = Symbols.ALL_SYMBOLS.length;
                int newIndex = (length + (index + key) % length) % length;
                output[i] = Symbols.charAt(newIndex);
            } else {
                output[i] = c;
            }

        }
        return output;
    }

}