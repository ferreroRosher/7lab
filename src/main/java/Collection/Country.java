package Collection;
/**
 * Перечисление возможных национальностей Person.
 */
public enum Country {
    GERMANY,
    FRANCE,
    JAPAN;

    public boolean isLessThan(Country other) {
        return this.compareTo(other) < 0;

    }
}