package Commands;

import Collection.Country;
import Collection.Person;
import Util.CollectionManager;
import java.util.Map;

public class FilterLessThanNationality extends AbstractCommand {

    public FilterLessThanNationality() {
        super("filter_less_than_nationality", "Вывести элементы, у которых поле nationality меньше заданного");
    }

    @Override
    public void execute(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Укажите страну  (GERMANY, FRANCE, JAPAN)");
            return;
        }

        try {
            Country target = Country.valueOf(args[0].toUpperCase());

            boolean found = false;
            for (Map.Entry<Integer, Person> entry : CollectionManager.getCollection().entrySet()) {
                Person p = entry.getValue();
                if (p.getNationality().compareTo(target) < 0) {
                    System.out.println(p);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Нет элементов с nationality меньше чем " + target);
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Неверное значение. Используйте GERMANY, FRANCE, JAPAN");
        }
    }
}
