package Collection;
import Util.IdGenerator;

import java.time.ZonedDateTime;

/**
 * Класс Person, который хранится в коллекции.
 * Реализует Comparable для сортировки по ID.
 */
public class Person implements Comparable<Person> {
    private final Integer id;
    private final String name;
    private final Coordinates coordinates;
    private final java.time.LocalDate creationDate;
    private final long height;
    private final ZonedDateTime birthday;
    private final String passportID;
    private final Country nationality;
    private final Location location;

    public Person(Integer id, String name, Coordinates coordinates, long height, ZonedDateTime birthday,
                  String passportID, Country nationality, Location location) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = java.time.LocalDate.now();
        this.height = height;
        this.birthday = birthday;
        this.passportID = passportID;
        this.nationality = nationality;
        this.location = location;

    }

    @Override
    public int compareTo(Person other) {
        return Integer.compare(this.id, other.id);
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public java.time.LocalDate getCreationDate() {
        return creationDate;
    }

    public long getHeight() {
        return height;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public String getPassportID() {
        return passportID;
    }

    public Country getNationality() {
        return nationality;
    }

    public Location getLocation() {
        return location;
    }
    public Integer getId() {
        return id;
    } //Найти в коде, где-то уже был
    //геттеры

}

