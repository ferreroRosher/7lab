package Commands;

/**
 * Интерфейс, представляющий команду.
 */
public interface Command {
    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    void execute(String[] args);

    /**
     * Возвращает название команды.
     */
    String getName();

    /**
     * Возвращает описание команды.
     */
    String getDescription();
}
