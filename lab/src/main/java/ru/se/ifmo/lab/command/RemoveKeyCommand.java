package ru.se.ifmo.lab.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.se.ifmo.cli.CommandAction;
import ru.se.ifmo.cli.DefaultCommand;

public class RemoveKeyCommand extends DefaultCommand {
    private static final Logger log = LoggerFactory.getLogger(RemoveKeyCommand.class);

    @CommandAction
    public void removeKey(Long key) {
        var map = getProject().getCollectionManager().getMap();

        if (!map.containsKey(key)) {
            log.warn("No element found with key = {}", key);
            return;
        }

        try {
            getProject().getCollectionManager().remove(key);
            log.info("Element with key = {} successfully removed.", key);
        } catch (SecurityException e) {
            log.warn("You are not allowed to remove this element: {}", e.getMessage());
        }
    }

    @Override
    public String getCaption() {
        return "Remove an element from the collection by its key.";
    }

    @Override
    public String getMask() {
        return "remove_key <key>";
    }
}
