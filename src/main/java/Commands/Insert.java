package Commands;

import Collection.Person;
import Util.CollectionManager;
import Util.CommandScanner;
import Util.CreatePerson;
/**
 * Команда для добавления нового элемента в коллекцию.
 */
public class Insert extends AbstractCommand {

    public Insert() {
        super("insert", "Добавить новый элемент в коллекцию");
    }

    @Override
    public void execute(String[] args) {
        CommandScanner.enableInputMode(); // включаем защиту от случайных команд

        Person person = CreatePerson.createFromInput();

        CommandScanner.disableInputMode(); // выключаем ТОЛЬКО после успешного ввода
        CollectionManager.addPerson(person);
        System.out.println("Person успешно добавлен в коллекцию.");
    }
}