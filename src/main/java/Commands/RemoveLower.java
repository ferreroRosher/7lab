package Commands;

import Collection.Person;
import Util.CollectionManager;
import Util.CommandScanner;
import Util.CreatePerson;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Команда RemoveLowerKey.
 * Удаляет все элементы коллекции, ключи которых меньше заданного.
 */
public class RemoveLower extends AbstractCommand {

    public RemoveLower() {
        super("RemoweLower", "Удалить из коллекции все элементы, меньшие, чем заданный");
    }

    @Override
    public void execute(String[] args) {
        if (CollectionManager.isEmpty()) {
            System.out.println("Коллекция пуста.");
            return;
        }

        System.out.println("Введите эталонный элемент для сравнения:");
        CommandScanner.enableInputMode();
        Person reference = CreatePerson.createFromInput();
        CommandScanner.disableInputMode();

        int removedCount = 0;
        Iterator<Person> iterator = CollectionManager.getCollection().values().iterator();

        while (iterator.hasNext()) {
            Person p = iterator.next();
            if (p.compareTo(reference) < 0) {
                iterator.remove();
                removedCount++;
            }
        }

        System.out.println("Удалено элементов: " + removedCount);
    }
}