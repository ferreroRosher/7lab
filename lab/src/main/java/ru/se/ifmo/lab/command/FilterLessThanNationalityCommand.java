package ru.se.ifmo.lab.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.se.ifmo.cli.CommandAction;
import ru.se.ifmo.cli.DefaultCommand;
import ru.se.ifmo.lab.model.Country;
import ru.se.ifmo.lab.model.Person;

public class FilterLessThanNationalityCommand extends DefaultCommand {
    private static final Logger logger = LoggerFactory.getLogger(FilterLessThanNationalityCommand.class);

    @CommandAction
    public void filter_less_than_nationality() {
        logger.info("Available nationalities: {}", enumOptions(Country.class));

        Country reference = requestInput(
                () -> getScanner().parseEnum(Country.class, getScanner().nextCommand()),
                "Enter nationality to compare against: ",
                "Invalid nationality."
        );

        getProject().getCollectionManager().getMap().values().stream()
                .map(e -> (Person) e)
                .filter(p -> p.getNationality().compareTo(reference) < 0)
                .forEach(p -> logger.info("{}", p));
    }

    @Override
    public String getCaption() {
        return "Display elements whose nationality field is less than the specified value.";
    }
}
