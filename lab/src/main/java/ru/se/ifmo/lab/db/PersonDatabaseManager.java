package ru.se.ifmo.lab.db;

import jakarta.inject.Inject;
import ru.se.ifmo.db.DatabaseManager;
import ru.se.ifmo.lab.model.Person;

import java.util.Map;

public class PersonDatabaseManager implements DatabaseManager<PersonCollectionManager> {

    private PersonTable personTable;

    @Inject
    public void setPersonTable(PersonTable personTable) {
        this.personTable = personTable;
    }

    @Override
    public boolean load(PersonCollectionManager manager) {
        try {
            manager.clear();
            Map<Long, Person> persons = personTable.getPersonMap();
            for (var entry : persons.entrySet()) {
                manager.add(entry.getKey(), entry.getValue());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean save(PersonCollectionManager collectionController) {
        try {
            personTable.cleanPersons();
            for (Map.Entry<Long, Person> entry: collectionController.getMap().entrySet()) {
                personTable.insertPerson(entry.getKey(), entry.getValue());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
