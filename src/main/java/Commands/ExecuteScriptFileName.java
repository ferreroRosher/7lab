package Commands;

import Util.CommandScanner;
import Util.Manager;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Команда executescript.
 * Считывает и исполняет команды из указанного файла.
 */
public class ExecuteScriptFileName extends AbstractCommand {

    private static final Set<String> executedScripts = new HashSet<>();

    public ExecuteScriptFileName() {
        super("executescript", "Считывает и исполняет команды из указанного файла.");
    }

    @Override
    public void execute(String[] args) {
        System.out.print("Укажите путь к файлу скрипта: ");
        String filePath = CommandScanner.getScanner().nextLine().trim();

        if (filePath.isEmpty()) {
            System.out.println("Путь не может быть пустым.");
            return;
        }

        File scriptFile = new File(filePath);
        if (!scriptFile.isAbsolute()) {
            scriptFile = new File(System.getProperty("user.dir"), filePath);
        }

        if (!scriptFile.exists() || !scriptFile.isFile()) {
            System.out.println("Файл не найден или недоступен: " + scriptFile.getAbsolutePath());
            return;
        }

        try {
            String canonicalPath = scriptFile.getCanonicalPath();
            if (executedScripts.contains(canonicalPath)) {
                System.out.println("Скрипт уже выполняется. Зацикливание.");
                return;
            }

            executedScripts.add(canonicalPath);

            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(scriptFile));
                 Scanner fileScanner = new Scanner(new InputStreamReader(bis))) {

                Scanner originalScanner = CommandScanner.getScanner();
                CommandScanner.setScanner(fileScanner);

                CommandScanner.startInteractiveMode();

                CommandScanner.setScanner(originalScanner);
            }

            executedScripts.remove(canonicalPath);

        } catch (IOException e) {
            System.out.println("Ошибка при выполнении скрипта: " + e.getMessage());
        }
    }
}

