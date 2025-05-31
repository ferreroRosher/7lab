package ru.se.ifmo.lab.model;

public enum Country {
    GERMANY(1, "GERMANY"),
    JAPAN(3, "JAPAN"),
    FRANCE(2, "FRANCE");

    private final int id;
    private final String name;

    Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {

        return id;
    }

    public String getName() {
        return name;
    }
}
