package Commands;
import Collection.Person;
import Util.CollectionManager;
import Util.CommandScanner;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
/**
 * Команда RemoveGreaterKey.
 * Удаляет все элементы коллекции, ключи которых больше заданного.
 */
public class RemoveGreaterKey extends AbstractCommand {

    public RemoveGreaterKey() {
        super("RemoveGreater", "Удалить из коллекции все элементы, ключ которых превышает заданный");
    }

    @Override
    public void execute(String[] args) {
        if (CollectionManager.isEmpty()) {
            System.out.println("Коллекция пуста.");
            return;
        }

        try {
            int key = (args != null && args.length > 0)
                    ? Integer.parseInt(args[0])
                    : Integer.parseInt(Objects.requireNonNull(CommandScanner.readLine("Введите ключ:")));

            Iterator<Map.Entry<Integer, Person>> iterator = CollectionManager.getCollection().entrySet().iterator();
            int removedCount = 0;

            while (iterator.hasNext()) {
                Map.Entry<Integer, ?> entry = iterator.next();
                if (entry.getKey() > key) {
                    iterator.remove();
                    removedCount++;
                }
            }

            System.out.println("Удалено элементов: " + removedCount);

        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат ключа. Ожидалось целое число.");
        }
    }
}

