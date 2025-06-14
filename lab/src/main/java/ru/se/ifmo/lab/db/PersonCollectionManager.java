package ru.se.ifmo.lab.db;

import jakarta.inject.Inject;
import ru.se.ifmo.db.CollectionManager;
import ru.se.ifmo.db.entity.User;
import ru.se.ifmo.lab.model.Person;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class PersonCollectionManager implements CollectionManager<Person> {
    private final LinkedHashMap<Long, Person> persons = new LinkedHashMap<>();
    private final LocalDate initDate = LocalDate.now();
    private User owner;
    private PersonTable personTable;

    @Inject
    public void setPersonTable(PersonTable personTable) {
        this.personTable = personTable;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public LocalDate getInitializeDate() {
        return initDate;
    }

    @Override
    public void add(Long key, Person person) {
        checkOwner(person);
        Person saved = personTable.insertPerson(key, person);
        persons.put(key, saved);
    }

    @Override
    public void remove(Long key) {
        Person existing = persons.get(key);
        if (existing != null && owner.equals(existing.getOwner())) {
            personTable.deleteById(existing.getId());
            persons.remove(key);
        } else {
            throw new SecurityException("Cannot remove: not the owner.");
        }
    }

    @Override
    public Person get(long key) {
        if (persons.containsKey(key)) {
            return persons.get(key);
        }
        // если нет в кеше, подгружаем из БД
        return personTable.getPersonMap().get(key);
    }

    @Override
    public void update(long key, Person entity) {
        checkOwner(entity);
        Person existing = persons.get(key);
        if (existing != null && owner.equals(existing.getOwner())) {
            personTable.updatePerson(entity);
            persons.put(key, entity);
        } else {
            throw new SecurityException("Cannot update: not the owner.");
        }
    }

    @Override
    public Map<Long, Person> getMap() {
        Map<Long, Person> dbMap = personTable.getPersonMap();
        persons.clear();
        persons.putAll(dbMap);
        return new LinkedHashMap<>(persons);
    }

    @Override
    public void clear() {
        Iterator<Map.Entry<Long, Person>> it = persons.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Long, Person> e = it.next();
            if (owner.equals(e.getValue().getOwner())) {
                personTable.deleteById(e.getValue().getId());
                it.remove();
            }
        }
    }

    @Override
    public void addAll(Map<Long, Person> entities) {
        for (Map.Entry<Long, Person> e : entities.entrySet()) {
            add(e.getKey(), e.getValue());
        }
    }

    @Override
    public User getOwner() {
        return owner;
    }

    private void checkOwner(Person person) {
        if (!owner.equals(person.getOwner())) {
            throw new SecurityException("Cannot modify: not the owner.");
        }
    }
}
