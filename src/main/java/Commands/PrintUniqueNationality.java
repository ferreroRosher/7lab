package Commands;

import Collection.Person;
import Collection.Country;
import Util.CollectionManager;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/**
 * Команда PrintUniqueNationality.
 * Выводит уникальные значения поля nationality всех элементов коллекции.
 */
public class PrintUniqueNationality extends AbstractCommand {

    public PrintUniqueNationality() {
        super("PrintUniqueNationality", "Вывести уникальные значения поля nationality всех элементов");
    }

    @Override
    public void execute(String[] args) {
        Set<Country> uniqueNationalities = new HashSet<>();

        for (Map.Entry<Integer, Person> entry : CollectionManager.getCollection().entrySet()) {
            uniqueNationalities.add(entry.getValue().getNationality());
        }

        if (uniqueNationalities.isEmpty()) {
            System.out.println("Коллекция пуста — уникальных nationalities нет.");
        } else {
            System.out.println("Уникальные значения поля nationality:");
            for (Country nationality : uniqueNationalities) {
                System.out.println("- " + nationality);
            }
        }
    }
}

