package ru.javarush.martyshin.cryptoanalyzer.resources;

import java.util.Arrays;

public class Symbols {
    private static final char[] RU_ALPHABET = "абвгдежзийклмнопрстуфхцчшщъыьэюя".toCharArray();
    public static final char[] ALL_SYMBOLS;
    public static final int POS_RU_ALPHABET;

    static {
        int firstASCII = ' ';
        int lastASCII = '~';
        int length = RU_ALPHABET.length + lastASCII - firstASCII + 1 - ('Z' - 'A' + 1);
        ALL_SYMBOLS = new char[length];
        int j = 0;
        for (int i = firstASCII; i <= lastASCII; i++) {
            if (i >= 'A' && i <= 'Z') {
                continue;
            }
            ALL_SYMBOLS[j] = (char) i;
            j++;
        }
        POS_RU_ALPHABET = j;
        for (int i = j; i < ALL_SYMBOLS.length; i++) {
            ALL_SYMBOLS[i] = RU_ALPHABET[i - j];
        }
    }

    public static int indexOf(char c) {
        return Arrays.binarySearch(ALL_SYMBOLS, c);
    }
    public static char charAt(int index) {
        return ALL_SYMBOLS[index];
    }
}
