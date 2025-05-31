package ru.se.ifmo.lab.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.se.ifmo.cli.CommandAction;
import ru.se.ifmo.cli.DefaultCommand;

import java.time.format.DateTimeFormatter;

public class InfoCommand extends DefaultCommand {
    private static final Logger log = LoggerFactory.getLogger(InfoCommand.class);

    @CommandAction
    public void info() {
        var collection = getProject().getCollectionManager();
        log.info("Collection type: {}", collection.getMap().getClass().getName());
        log.info("Element type: SpaceMarine");
        log.info("Initialization date: {}", collection.getInitializeDate().format(DateTimeFormatter.ISO_DATE));
        log.info("Total elements: {}", collection.getMap().size());
    }

    @Override
    public String getCaption() {
        return "Display information about the collection.";
    }
}
