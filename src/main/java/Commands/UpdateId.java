package Commands;

import Collection.Person;
import Util.CollectionManager;
import Util.CommandScanner;
import Util.CreatePerson;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * Обновляет элемент коллекции по id.
 */
public class UpdateId extends AbstractCommand {

    public UpdateId() {
        super("UpdateId", "Обновить значение элемента коллекции, id которого равен заданному");
    }

    @Override
    public void execute(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Пожалуйста, укажите ID для обновления.");
            return;
        }

        try {
            int id = Integer.parseInt(args[0]);
            LinkedHashMap<Integer, Person> collection = (LinkedHashMap<Integer, Person>) CollectionManager.getCollection();
            Person toUpdate = null;
            Integer foundKey = null;

            for (Map.Entry<Integer, Person> entry : collection.entrySet()) {
                if (entry.getValue().getId() == id) {
                    toUpdate = entry.getValue();
                    foundKey = entry.getKey();
                    break;
                }
            }

            if (toUpdate == null) {
                System.out.println("Элемент с таким ID не найден.");
                return;
            }

            CommandScanner.enableInputMode();
            Person updatedPerson = CreatePerson.createFromInput();
            CommandScanner.disableInputMode();

            collection.put(foundKey, updatedPerson);
            System.out.println("Элемент успешно обновлён.");

        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID. Укажите целое число.");
        }
    }
}
