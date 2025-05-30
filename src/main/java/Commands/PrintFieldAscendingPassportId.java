package Commands;

import Collection.Person;
import Util.CollectionManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
/**
 * Команда PrintFieldAscendingPassport.
 * Выводит значения поля passportID всех элементов коллекции в порядке возрастания.
 */
public class PrintFieldAscendingPassportId extends AbstractCommand {

    public PrintFieldAscendingPassportId() {
        super("print_field_ascending_passport_id", "Вывести значения поля passportID всех элементов в порядке возрастания");
    }

    @Override
    public void execute(String[] args) {
        List<String> passportIds = new ArrayList<>();

        for (Map.Entry<Integer, Person> entry : CollectionManager.getCollection().entrySet()) {
            passportIds.add(entry.getValue().getPassportID());
        }

        if (passportIds.isEmpty()) {
            System.out.println("Коллекция пуста — passportID не найдены.");
            return;
        }

        Collections.sort(passportIds);

        System.out.println("Значения passportID в порядке возрастания:");
        for (String id : passportIds) {
            System.out.println(id);
        }
    }
}