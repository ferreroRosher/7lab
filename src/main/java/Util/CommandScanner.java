package Util;

import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * Класс для управления режимами ввода команд и объектов.
 * Отвечает за интерактивный режим работы программы.
 */
public class CommandScanner {
    private static final Scanner defaultScanner = new Scanner(System.in);
    private static Scanner scanner = defaultScanner;
    private static boolean wordleMode = false;
    private static boolean inputMode = false;
    /**
     * Запускает основной цикл интерактивного режима ввода команд.
     */
    public static void startInteractiveMode() {

        if (scanner == null) scanner = defaultScanner;
        if (scanner == defaultScanner) {
            System.out.println("Хотите включить режим Wordle для разблокировки скрытой команды? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("y")) {
                Manager.enableWordleMode();
                wordleMode = true;
                System.out.println("Режим Wordle активирован! Введите WORDLE, чтобы начать игру.");
            } else {
                Manager.unlockInsert();
            }
        }

        while (true) {
            System.out.print("> ");
            try {
                if (!scanner.hasNextLine()) {
                    System.out.println("\n Завершение: получен EOF (Ctrl+D)");
                    break;
                }

                String commandLine = scanner.nextLine().trim();
                if (commandLine.isEmpty()) {
                    if (!isInputMode() && Manager.getCommands().containsKey("")) {
                        Manager.executeCommand("");
                    }
                    continue;
                }

                // Если сейчас режим ввода — команды не обрабатываем
                if (isInputMode()) {
                    continue;
                }

                String[] inputs = commandLine.split("\\s+");
                boolean recognized = false;

                for (String input : inputs) {
                    String upper = cleanCommand(input);

                    if (Manager.getCommands().containsKey(upper)) {

                        if (upper.equals("WORDLE") && wordleMode) {
                            Manager.executeCommand("WORDLE");
                            Manager.removeCommand("WORDLE");
                            wordleMode = false;
                            Manager.unlockInsert();
                            recognized = true;
                            break;
                        }

                        Manager.executeCommand(upper);
                        recognized = true;
                        break;
                    }
                }

                if (!recognized) {
                    System.out.println("Неизвестная команда. Введите 'help' для списка команд.");
                }

            } catch (NoSuchElementException e) {
                System.out.println("Конец ввода. Возвращаемся к стандартному сканеру.");
                setScanner(new Scanner(System.in));
                break;
            }
        }
    }

    private static String cleanCommand(String input) {
        return input.toUpperCase();
    }
    /**
     * Считывает строку ввода пользователя с обработкой экранированных символов.
     *
     * @param prompt строка-приглашение к вводу
     * @return введённая строка пользователя
     */
    public static String readLine(String prompt) {
        if (!scanner.hasNextLine()) return null;
        System.out.print(prompt + " ");
        return scanner.nextLine()
                .replace("\\n", " ")
                .replace("\\t", " ")
                .replace("\\r", " ")
                .replaceAll("[\";]", " ")
                .replace("KEY", "")
                .replace("_", "")
                .trim();
    }
    /**
     * Возвращает активный сканер ввода.
     */
    public static Scanner getScanner() {
        return scanner;
    }
    /**
     * Устанавливает активный сканер ввода.
     *
     * @param newScanner новый сканер
     */
    public static void setScanner(Scanner newScanner) {
        scanner = newScanner;
    }
    /**
     * Проверяет, активен ли режим ввода объекта.
     */
    public static boolean isInputMode() {
        return inputMode;
    }
    /**
     * Включает режим ввода объекта.
     */
    public static void enableInputMode() {
        inputMode = true;
    }
    /**
     * Выключает режим ввода объекта.
     */
    public static void disableInputMode() {
        inputMode = false;
    }

}


