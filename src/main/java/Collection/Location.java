package Collection;

/*public class Location {
    private double x;
    private Double y; //Поле не может быть null
    private Integer z; //Поле не может быть null
    private String name; //Строка не может быть пустой, Поле не может быть null
}*/
/**
 * Класс, представляющий местоположение объекта.
 */
public class Location {
    private final double x;
    private final Double y; // Не null
    private final Integer z; // Не null
    private final String name; // Не null, не пустая строка

    public Location(double x, Double y, Integer z, String name) {
        if (y == null) throw new IllegalArgumentException("Координата y не может быть null.");
        if (z == null) throw new IllegalArgumentException("Координата z не может быть null.");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Название не может быть пустым.");

        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public double getX() { return x; }
    public Double getY() { return y; }
    public Integer getZ() { return z; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return "Location{x=" + x + ", y=" + y + ", z=" + z + ", name='" + name + "'}";
    }
}