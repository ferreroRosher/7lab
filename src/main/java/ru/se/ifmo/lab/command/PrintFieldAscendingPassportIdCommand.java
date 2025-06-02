package ru.se.ifmo.lab.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.se.ifmo.cli.CommandAction;
import ru.se.ifmo.cli.DefaultCommand;
import ru.se.ifmo.lab.model.Person;

import java.util.Comparator;

public class PrintFieldAscendingPassportIdCommand extends DefaultCommand {
    private static final Logger log = LoggerFactory.getLogger(PrintFieldAscendingPassportIdCommand.class);

    @CommandAction
    public void printFieldAscendingPassportId() {
        var values = getProject().getCollectionManager().getMap().values();

        if (values.isEmpty()) {
            log.info("Collection is empty.");
            return;
        }

        values.stream()
                .map(e -> ((Person) e).getPassportID())
                .sorted(Comparator.naturalOrder())
                .forEach(log::info);
    }

    @Override
    public String getCaption() {
        return "Print passportID field of all elements in ascending order.";
    }

    @Override
    public String getMask() {
        return "print_field_ascending_passport_id";
    }
}
