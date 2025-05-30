package Commands;

import Util.CommandScanner;
import Util.CollectionManager;
/**
 * Команда Info.
 * Выводит информацию о коллекции: тип, дату инициализации, количество элементов.
 */
public class Info extends AbstractCommand {
    public Info() {
        super("info", "Вывести информацию о коллекции");
    }

    @Override
    public void execute(String[] args) {
        CommandScanner.disableInputMode(); // Отключаем режим ввода, если был включен
        System.out.println("Информация о коллекции:");
        System.out.println("   - Тип коллекции: " + CollectionManager.getCollectionType());
        System.out.println("   - Дата инициализации: " + CollectionManager.getInitializationDate());
        System.out.println("   - Количество элементов: " + CollectionManager.getSize());
    }
}
