package ru.se.ifmo.lab.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.se.ifmo.cli.CommandAction;
import ru.se.ifmo.cli.DefaultCommand;

public class ClearCommand extends DefaultCommand {
    private static final Logger log = LoggerFactory.getLogger(ClearCommand.class);

    @CommandAction
    public void clear() {
        getProject().getCollectionManager().clear();
        log.info("Collection has been cleared.");
    }

    @Override
    public String getCaption() {
        return "Clear the entire collection.";
    }
}
