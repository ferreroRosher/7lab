package ru.se.ifmo.lab.model;

import ru.se.ifmo.db.entity.Entity;

import java.util.Objects;

public class Location implements Entity {
    private long id;
    private double x;
    private Double y;
    private Integer z;
    private String name;

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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        if (y == null) {
            throw new IllegalArgumentException("y cannot be null");
        }
        this.y = y;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        if (z == null) {
            throw new IllegalArgumentException("z cannot be null");
        }
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be empty");
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Location location)) return false;
        return Double.compare(x, location.x) == 0 &&
                Objects.equals(y, location.y) &&
                Objects.equals(z, location.z) &&
                Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }
}
