package Commands;

import Util.CollectionManager;
/**
 * Команда Clear.
 * Очищает коллекцию.
 */
public class Clear extends AbstractCommand {

    public Clear() {
        super("clear", "Очистить коллекцию");
    }

    @Override
    public void execute(String[] args) {
        if (CollectionManager.isEmpty()) {
            System.out.println("Коллекция уже пуста.");
        } else {
            CollectionManager.clear();
            System.out.println("Коллекция успешно очищена.");
        }
    }
}
