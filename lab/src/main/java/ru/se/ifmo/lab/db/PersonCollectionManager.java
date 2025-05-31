package ru.se.ifmo.lab.db;

import ru.se.ifmo.db.CollectionManager;
import ru.se.ifmo.db.entity.User;
import ru.se.ifmo.lab.model.Person;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class PersonCollectionManager implements CollectionManager<Person> {
    private final LinkedHashMap<Long, Person> persons = new LinkedHashMap<>();
    private final LocalDate initDate = LocalDate.now();
    private User owner;

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
        persons.put(key, person);
    }


    @Override
    public void remove(Long key) {
        Person entity = persons.get(key);
        if (entity != null && owner.equals(entity.getOwner())) {
            persons.remove(key);
        } else {
            throw new SecurityException("Cannot remove: you are not the owner of this Person.");
        }
    }

    @Override
    public Person get(long id) {
        return persons.get(id);
    }

    @Override
    public void update(long key, Person entity) {
        checkOwner(entity);
        Person existing = persons.get(key);
        if (existing != null && owner.equals(existing.getOwner())) {
            persons.put(key, entity);
        } else {
            throw new SecurityException("Cannot update: you are not the owner of this Person.");
        }
    }

    @Override
    public Map<Long, Person> getMap() {
        return (Map<Long, Person>) persons.clone();
    }

    @Override
    public void clear() {
        persons.values().removeIf(person -> owner.equals(person.getOwner()));
    }

    @Override
    public void addAll(Map<Long, Person> entities) {
        for (Map.Entry<Long, Person> entry : entities.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public User getOwner() {
        return owner;
    }

    private void checkOwner(Person person) {
        if (!owner.equals(person.getOwner())) {
            throw new SecurityException("You cannot modify this Person: not the owner.");
        }
    }


}
