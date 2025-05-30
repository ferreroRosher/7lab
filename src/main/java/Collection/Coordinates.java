package Collection;

/*public class Coordinates {
    private Integer x; //Максимальное значение поля: 369, Поле не может быть null
    private float y; //Значение поля должно быть больше -983
}*/
/**
 * Класс, представляющий координаты объекта.
 */
public record Coordinates(Integer x, float y) {
    public Coordinates {
        if (x == null || x > 369) throw new IllegalArgumentException("Координата x должна быть ≤ 369.");
        if (y <= -983) throw new IllegalArgumentException("Координата y должна быть больше -983.");
    }

    @Override
    public String toString() {
        return "Coordinates{x=" + x + ", y=" + y + "}";
    }
    public Integer getX() { return x; }
    public float getY() { return y; }
}


