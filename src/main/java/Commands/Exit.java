package Commands;

import Util.CommandScanner;
/**
 * Команда Exit.
 * Завершает выполнение программы без сохранения.
 */
public class Exit extends AbstractCommand {
    public Exit() {
        super("exit", "Выйти из команды");
    }

    @Override
    public void execute(String[] args) {
        CommandScanner.disableInputMode();
        System.out.println("Directed by.....");
        System.exit(0);
    }
}
