package Util;

import Commands.*;

import java.util.HashMap;
import java.util.Map;
/**
 * Класс для управления всеми зарегистрированными командами.
 */
public class Manager {
    private static final Map<String, Command> commands = new HashMap<>();
    /**
     * Инициализирует и регистрирует все доступные команды.
     */
    static {
        commands.put("HELP", new Help());
        commands.put("INFO", new Info());
        commands.put("EXIT", new Exit());
        commands.put("SHOW", new Show());
        commands.put("REMOVEKEY", new RemoveKey());
        commands.put("REMOVELOWERKEY", new RemoveLower());
        commands.put("REMOVEGREATERKEY", new RemoveGreaterKey());
        commands.put("FILTERLESSTHANNATIONALITY", new FilterLessThanNationality());
        commands.put("PRINTUNIQUENATIONALITY", new PrintUniqueNationality());
        commands.put("PRINTFIELDASCENDINGPASSPORTID", new PrintFieldAscendingPassportId());
        commands.put("UPDATEID", new UpdateId());
        commands.put("", new WaitForEmptyInput());
        commands.put("CLEAR", new Clear());
        commands.put("EXECUTESCRIPT", new ExecuteScriptFileName());
        commands.put("SAVE", new Save());
    }

    /**
     * Активирует режим Wordle, добавляя скрытую команду.
     */
    public static void enableWordleMode() {
        commands.put("WORDLE", new Wordle());
    }
    /**
     * Открывает доступ к команде Insert
     */
    public static void unlockInsert() {
        if (!commands.containsKey("INSERT")) {
            commands.put("INSERT", new Insert());
        }
    }
    /**
     * Удаляет выбранную команду
    */
    public static void removeCommand(String command) {
        commands.remove(command);
    }
    /**
     * Выполняет команду по её имени.
     *
     * @param commandLine строка с именем команды
     */
    public static void executeCommand(String commandLine) {
        if (!commandLine.equalsIgnoreCase("INSERT")) {
            CommandScanner.disableInputMode(); // Отключаем ввод данных
        }

        Command command = commands.get(commandLine.toUpperCase());
        if (command != null) {
            command.execute(new String[]{});
        } else if (!CommandScanner.isInputMode()) {
            System.out.println("Неизвестная команда. Введите 'help' для списка команд.");
        }
    }
    /**
     * Возвращает список всех зарегистрированных команд.
     */
    public static Map<String, Command> getCommands() {
        return commands;
    }
}

