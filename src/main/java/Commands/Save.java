package Commands;
import java.io.IOException;
/**
 * Команда Save.
 * Сохраняет коллекцию в файл.
 */
public class Save extends AbstractCommand {

    public Save() {
        super("save", "Сохранить коллекцию в файл");
    }

    @Override
    public void execute(String[] args) {
            System.out.println("Ошибка при сохранении: ");
        }
    }
