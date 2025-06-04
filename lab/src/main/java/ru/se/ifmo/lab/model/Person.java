package ru.se.ifmo.lab.model;

import ru.se.ifmo.db.entity.Entity;
import ru.se.ifmo.db.entity.User;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Person implements Entity, Comparable<Person> {
    private long id;
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
    private long height;
    private ZonedDateTime birthday;
    private String passportID;
    private Country nationality;
    private Location location;
    private User owner;

    @Override
    public int compareTo(Person other) {
        return Long.compare(this.id, other.id);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be greater than 0");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("coordinates cannot be null");
        }
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        if (creationDate == null) {
            throw new IllegalArgumentException("creationDate cannot be null");
        }
        this.creationDate = creationDate;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        if (height <= 0) {
            throw new IllegalArgumentException("height must be greater than 0");
        }
        this.height = height;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(ZonedDateTime birthday) {
        if (birthday == null) {
            throw new IllegalArgumentException("birthday cannot be null");
        }
        this.birthday = birthday;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        if (passportID == null) {
            throw new IllegalArgumentException("passportID cannot be null");
        }
        if (passportID.length() < 6 || passportID.length() > 33) {
            throw new IllegalArgumentException("passportID length must be between 6 and 33 characters");
        }
        this.passportID = passportID;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        if (nationality == null) {
            throw new IllegalArgumentException("nationality cannot be null");
        }
        this.nationality = nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        if (owner == null) {
            throw new IllegalArgumentException("owner cannot be null");
        }
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person person)) return false;
        return height == person.height && Objects.equals(id, person.id) &&
                Objects.equals(name, person.name) && Objects.equals(coordinates, person.coordinates) &&
                Objects.equals(creationDate, person.creationDate) && Objects.equals(birthday, person.birthday) &&
                Objects.equals(passportID, person.passportID) && nationality == person.nationality &&
                Objects.equals(location, person.location) && Objects.equals(owner, person.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                name,
                coordinates,
                creationDate,
                height,
                birthday,
                passportID,
                nationality,
                location,
                owner
        );
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", height=" + height +
                ", birthday=" + birthday +
                ", passportID='" + passportID + '\'' +
                ", nationality=" + nationality +
                ", location=" + location +
                ", owner=" + owner +
                '}';
    }
}
