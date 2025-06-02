package ru.se.ifmo.lab.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.se.ifmo.cli.CommandAction;
import ru.se.ifmo.cli.DefaultCommand;
import ru.se.ifmo.db.entity.Entity;
import ru.se.ifmo.lab.model.Country;
import ru.se.ifmo.lab.model.Person;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class PrintUniqueNationalityCommand extends DefaultCommand {
    private static final Logger log = LoggerFactory.getLogger(PrintUniqueNationalityCommand.class);

    @CommandAction
    public void printUniqueNationality() {
        Map<Long, Entity> map = getProject().getCollectionManager().getMap();

        if (map.isEmpty()) {
            log.info("Collection is empty.");
            return;
        }

        Set<Country> uniqueNationalities = map.values().stream()
                .map(e -> (Person) e)
                .map(Person::getNationality)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (uniqueNationalities.isEmpty()) {
            log.info("No nationalities found.");
        } else {
            log.info("Unique nationalities:");
            uniqueNationalities.forEach(n -> log.info("- {}", n));
        }
    }

    @Override
    public String getCaption() {
        return "Print unique values of the 'nationality' field.";
    }
}
