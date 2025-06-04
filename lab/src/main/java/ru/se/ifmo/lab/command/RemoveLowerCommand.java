package ru.se.ifmo.lab.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.se.ifmo.cli.CommandAction;
import ru.se.ifmo.cli.DefaultCommand;
import ru.se.ifmo.lab.model.Person;

import java.util.List;
import java.util.Map;

public class RemoveLowerCommand extends DefaultCommand {
    private static final Logger log = LoggerFactory.getLogger(RemoveLowerCommand.class);

    @CommandAction
    public void remove_lower(Long idThreshold) {
        var map = getProject().getCollectionManager().getMap();
        List<Long> toRemove = map.entrySet().stream()
                .filter(entry -> {
                    Person p = (Person) entry.getValue();
                    return p.getId() < idThreshold && getProject().getCollectionManager().getOwner().equals(p.getOwner());
                })
                .map(Map.Entry::getKey)
                .toList();

        if (toRemove.isEmpty()) {
            log.info("No matching elements to remove.");
            return;
        }

        for (Long key : toRemove) {
            try {
                getProject().getCollectionManager().remove(key);
                log.info("Removed element with key = {}", key);
            } catch (Exception e) {
                log.warn("Failed to remove element with key = {}: {}", key, e.getMessage());
            }
        }
    }

    @Override
    public String getCaption() {
        return "Remove all elements with id lower than the specified value.";
    }

    @Override
    public String getMask() {
        return "remove_lower <id>";
    }
}
