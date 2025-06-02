package ru.se.ifmo.lab.command;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.se.ifmo.cli.CommandAction;
import ru.se.ifmo.cli.DefaultCommand;

public class SaveCommand extends DefaultCommand {
    private static final Logger log = LoggerFactory.getLogger(SaveCommand.class);

    @CommandAction
    public void save() {
        boolean result = getProject().getDatabaseManager().save(getProject().getCollectionManager());
        if (result) {
            log.info("Collection successfully saved to the database.");
        } else {
            log.error("Failed to save collection to the database.");
        }
    }

    @Override
    public String getCaption() {
        return "Save the current state of the collection to the database.";
    }

    @Override
    public String getMask() {
        return "save";
    }
}
