package Commands;

import Collection.Person;
import Util.CollectionManager;
import Util.IdGenerator;

import java.util.Map;
/**
 * Команда Show.
 * Выводит все элементы коллекции.
 */
public class Show extends AbstractCommand {

    public Show() {
        super("Show", "Вывести в стандартный поток вывода все элементы коллекции");
    }

    @Override
    public void execute(String[] args) {
        if (CollectionManager.isEmpty()) {
            System.out.println("Коллекция пуста.");
            return;
        }

        for (Map.Entry<Integer, Person> entry : CollectionManager.getCollection().entrySet()) {
            Person p = entry.getValue();
            System.out.println("==== Элемент #" + entry.getKey() + " ====");
            System.out.println("ID: " + IdGenerator.generateId());
            System.out.println("Имя: " + p.getName());
            System.out.println("Координаты: " + p.getCoordinates());
            System.out.println("Рост: " + p.getHeight());
            System.out.println("Дата создания: " + p.getCreationDate());
            System.out.println("День рождения: " + p.getBirthday());
            System.out.println("Паспорт: " + p.getPassportID());
            System.out.println("Национальность: " + p.getNationality());
            System.out.println("Локация: " + p.getLocation());
            System.out.println();
        }
    }
}
