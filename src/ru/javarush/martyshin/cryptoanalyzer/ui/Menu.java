package ru.javarush.martyshin.cryptoanalyzer.ui;

import ru.javarush.martyshin.cryptoanalyzer.CaesarsBruteForceAction;
import ru.javarush.martyshin.cryptoanalyzer.FileManager;
import ru.javarush.martyshin.cryptoanalyzer.CaesarsEncoderAction;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import static ru.javarush.martyshin.cryptoanalyzer.FileManager.*;
import static ru.javarush.martyshin.cryptoanalyzer.resources.StringValues.*;


public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Path> files;
    private static int mainMenuAction;

    public static void start() {
        menu:
        while (true) {
            showMainMenu();
            mainMenuAction = Integer.parseInt(scanner.nextLine());
            if (mainMenuAction > 0 && mainMenuAction < 4) {
                showSubMenu();
                if (isBruteForceAction()) {
                    runCaesarsBruteForceOrReturn();
                } else {
                    runCaesarsEncoderActionOrReturn();
                }
                showMessage(ACTION_EXECUTED_OK);
            } else if (mainMenuAction == 4) {
                break menu;
            }
            else {
                throw new IllegalArgumentException(String.format(MENU_NUM_NOT_EXIST, mainMenuAction));
            }
        }
    }

    private static boolean isEncoderAction() {
        return mainMenuAction == 1;
    }

    private static boolean isBruteForceAction () {
        return mainMenuAction == 3;
    }

    private static void showMainMenu() {
        showMessage(PROGRAM_NAME +
                "\n1. " + ENCODE_MENU_ITEM +
                "\n2. " + DECODE_MENU_ITEM +
                "\n3. " + BRUTE_FORCE_MENU_ITEM +
                "\n4. " + CLOSE_PROGRAM + "\n"
                + CHOOSE_MENU_ITEM_NUM);
    }

    private static void showMessage(String str) {
        System.out.println(str);
    }

    private static void runCaesarsEncoderActionOrReturn() {
        String str = scanner.nextLine();
        int indexOfSpace = str.indexOf(' ');
        int menuNum;
        int key = 0;
        if (indexOfSpace == -1) {
            menuNum = Integer.parseInt(str);
        } else {
            menuNum = Integer.parseInt(str.substring(0, indexOfSpace));
            key = Integer.parseInt(str.substring(str.indexOf(' ') + 1));
        }
        if (menuNum == 1) {
        } else if (menuNum > 1 && menuNum < files.size() + 2) {
           new CaesarsEncoderAction().run(files.get(menuNum - 2), key, isEncoderAction());
        } else {
            throw new IllegalArgumentException(String.format(MENU_NUM_NOT_EXIST, menuNum));
        }
    }

    private static void runCaesarsBruteForceOrReturn() {
        int menuNum = Integer.parseInt(scanner.nextLine());
        if (menuNum == 1) {
        } else if (menuNum > 1 && menuNum < files.size() + 2) {
            new CaesarsBruteForceAction().run(files.get(menuNum - 2));
        } else {
            throw new IllegalArgumentException(String.format(MENU_NUM_NOT_EXIST, menuNum));
        }
    }

    private static void showSubMenu() {
        buildSubMenu();
    }

    private static void buildSubMenu() {
        StringBuilder message = new StringBuilder();
        Path path = Path.of(PATH_RESOURCES);
        if (isEncoderAction()) {
            path = path.resolve(SOURCE);
        } else {
            path = path.resolve(ENCODED);
        }
        files = FileManager.getFilesFromDirectory(path);

        message.append(ENCODE_MENU_ITEM);
        String forSometh = switch (mainMenuAction) {
            case 1 -> FOR_ENCODE;
            case 2 -> FOR_DECODE;
            case 3 -> FOR_BRUTE_FORCE;
            default -> throw new RuntimeException(NO_ACTION);
        };
        message.append(String.format(" " + CHOOSE_FILE_FOR + "\n", forSometh));
        message.append(RETURN_PREV + "\n");
        for (int i = 0; i < files.size(); i++) {
            message.append(String.format("%d. %s\n", i + 2, files.get(i).getFileName().toString()));
        }
        if (files.isEmpty()) {
            throw new RuntimeException(FILES_NOT_FOUND);
        }
        if (isBruteForceAction()) {
            message.append(CHOOSE_MENU_ITEM_NUM);
        } else {
            message.append(CHOOSE_MENU_ITEM_NUM + " " + ENTER_KEY);
        }
        showMessage(message.toString());
    }

}
