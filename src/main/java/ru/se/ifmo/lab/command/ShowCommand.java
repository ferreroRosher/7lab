package ru.se.ifmo.lab.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.se.ifmo.cli.CommandAction;
import ru.se.ifmo.cli.DefaultCommand;
import ru.se.ifmo.lab.model.Person;

public class ShowCommand extends DefaultCommand {
    private static final Logger logger = LoggerFactory.getLogger(ShowCommand.class);

    @CommandAction
    public void show() {
        var map = getProject().getCollectionManager().getMap();
        if (map.isEmpty()) {
            logger.info("Collection is empty.");
        } else {
            logger.info("All Persons in collection:");
            map.forEach((key, person) -> logger.info("Key: {}, Value: {}", key, person));
        }
    }

    @Override
    public String getCaption() {
        return "Show all elements in the SpaceMarine collection.";
    }

    @Override
    public String getMask() {
        return "show";
    }
}
