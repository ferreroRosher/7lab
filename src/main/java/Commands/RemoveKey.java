package Commands;
import Util.CollectionManager;
import Util.CommandScanner;
import java.util.Map;
import java.util.Objects;
/**
 * Команда Remove_key.
 * Удаляет элемент коллекции по его ключу.
 */
public class RemoveKey extends AbstractCommand {

    public RemoveKey() {
        super("RemoveKey", "Удалить элемент из коллекции по его ключу");
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

            Map<Integer, ?> collection = CollectionManager.getCollection();
            if (collection.containsKey(key)) {
                collection.remove(key);
                System.out.println("Элемент с ключом " + key + " удалён.");
            } else {
                System.out.println("Элемент с таким ключом не найден.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ключа. Ожидалось целое число.");
        }
    }
}