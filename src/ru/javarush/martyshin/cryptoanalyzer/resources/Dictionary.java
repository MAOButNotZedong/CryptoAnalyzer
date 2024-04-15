package ru.javarush.martyshin.cryptoanalyzer.resources;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Dictionary {
    public static HashSet<String> RU_WORDS = new HashSet<>(Set.of(
            "он", "она", "оно", "они", "ты", "мы", "вы", "нас", "вас", "их", "твой", "свой", "наш",
            "ваш", "не", "ни", "да", "нет", "но", "или", "потому", "что", "кто", "где", "когда", "куда",
            "откуда", "нибудь", "даже", "ведь", "вот", "вон", "под", "над", "по", "который", "почему", "какой",
            "какая", "которая", "на"));

    public static HashSet<String> EN_WORDS = new HashSet<>(Set.of(
            "he", "she", "it", "is", "am", "are", "has", "have", "had", "do", "did", "does", "was",
            "were", "the", "who", "where", "when", "why", "what", "which", "that", "then", "than", "and", "but",
            "because", "between", "as", "his", "her", "for", "on", "at", "this", "from", "to", "by",
            "or", "can"));
}
