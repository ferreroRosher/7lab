package Commands;
/**
 * Абстрактный базовый класс для всех команд.
 * Содержит имя и описание команды.
 */
public abstract class AbstractCommand implements Command {
    private final String name;
    private final String description;

    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract void execute(String[] args);
}
