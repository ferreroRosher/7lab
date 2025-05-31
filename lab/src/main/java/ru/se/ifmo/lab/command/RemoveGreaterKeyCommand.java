package ru.se.ifmo.lab.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.se.ifmo.cli.CommandAction;
import ru.se.ifmo.cli.DefaultCommand;
import ru.se.ifmo.db.entity.Entity;

import java.util.Iterator;
import java.util.Map;

public class RemoveGreaterKeyCommand extends DefaultCommand {
    private static final Logger log = LoggerFactory.getLogger(RemoveGreaterKeyCommand.class);

    @CommandAction
    public void removeGreaterKey(Long thresholdKey) {
        Map<Long, Entity> map = getProject().getCollectionManager().getMap();

        Iterator<Map.Entry<Long, Entity>> it = map.entrySet().iterator();
        int removed = 0;

        while (it.hasNext()) {
            Map.Entry<Long, Entity> entry = it.next();
            if (entry.getKey() > thresholdKey) {
                try {
                    getProject().getCollectionManager().remove(entry.getKey());
                    it.remove();
                    removed++;
                } catch (Exception e) {
                    log.warn("Cannot remove key {}: {}", entry.getKey(), e.getMessage());
                }
            }
        }

        log.info("Removed {} elements with key greater than {}", removed, thresholdKey);
    }

    @Override
    public String getCaption() {
        return "Remove all elements whose key is greater than the specified key.";
    }

    @Override
    public String getMask() {
        return "remove_greater_key <key>";
    }
}
