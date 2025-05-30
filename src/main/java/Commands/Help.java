package Commands;

import Util.Manager;
/**
 * Команда Help.
 * Проявляет список всех доступных команд и их описание.
 */
public class Help extends AbstractCommand {
    public Help() {
        super("help", "Вывести список доступных команд");
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Доступные команды:");
        boolean hasHiddenCommand = false;

        for (Command command : Manager.getCommands().values()) {
            if (command.getName().equals("insert") && !Manager.getCommands().containsKey("INSERT")) {
                hasHiddenCommand = true;
                continue; // Пропускаем insert, если она не разблокирована
            }
            System.out.println("- " + command.getName() + ": " + command.getDescription());
        }

        if (hasHiddenCommand && Manager.getCommands().containsKey("WORDLE")) {
            System.out.println("Есть скрытая команда, которую можно разблокировать! Введите WORDLE, чтобы начать игру.");
        }
    }
}


