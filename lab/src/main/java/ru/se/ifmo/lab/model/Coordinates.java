package ru.se.ifmo.lab.model;

import ru.se.ifmo.db.entity.Entity;

import java.util.Objects;

public class Coordinates implements Entity {
    private long id;
    private Integer x;
    private float y;

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

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        if (x == null) {
            throw new IllegalArgumentException("x cannot be null");
        }
        if (x > 369) {
            throw new IllegalArgumentException("x must be less than or equal to 369");
        }
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        if (y <= -983) {
            throw new IllegalArgumentException("y must be greater than -983");
        }
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coordinates that)) return false;
        return Float.compare(y, that.y) == 0 && Objects.equals(x, that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
