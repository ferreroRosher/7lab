package ru.se.ifmo.lab.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.se.ifmo.cli.CommandAction;
import ru.se.ifmo.cli.DefaultCommand;
import ru.se.ifmo.db.entity.Entity;
import ru.se.ifmo.lab.model.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Map;

public class InsertCommand extends DefaultCommand {
    private static final Logger log = LoggerFactory.getLogger(InsertCommand.class);

    @CommandAction
    public void insert(Long key) {
        log.info("Starting 'insert' command with key={}", key);

        Map<Long, Entity> map = getProject().getCollectionManager().getMap();
        if (map.containsKey(key)) {
            log.warn("Key {} already exists. Aborting insert.", key);
            return;
        }

        Person person = new Person();

        validatedInput("Enter name: ", "Invalid name.",
                getScanner()::nextCommand, person::setName);

        Coordinates coordinates = new Coordinates();
        validatedInput("Enter coordinate X (<= 369): ", "Invalid X.",
                getScanner()::nextInt, coordinates::setX);
        validatedInput("Enter coordinate Y (> -983): ", "Invalid Y.",
                getScanner()::nextFloat, coordinates::setY);
        person.setCoordinates(coordinates);

        person.setCreationDate(LocalDate.now());

        validatedInput("Enter height (> 0): ", "Invalid height.",
                getScanner()::nextLong, person::setHeight);

        validatedInput("Enter birthday (yyyy-mm-ddThh:mm:ss): ", "Invalid datetime.",
                () -> ZonedDateTime.parse(getScanner().nextCommand()), person::setBirthday);

        validatedInput("Enter passport ID (6-33 chars): ", "Invalid passport ID.",
                getScanner()::nextCommand, person::setPassportID);

        log.info("Available countries: {}", enumOptions(Country.class));
        validatedInput("Enter nationality: ", "Invalid nationality.",
                () -> getScanner().parseEnum(Country.class, getScanner().nextCommand()), person::setNationality);

        boolean hasLocation = requestInput(
                () -> {
                    String s = getScanner().nextCommand().trim().toLowerCase();
                    if (!s.equals("yes") && !s.equals("no")) throw new IllegalArgumentException();
                    return s.equals("yes");
                },
                "Add location? (yes/no): ",
                "Please enter 'yes' or 'no'."
        );

        if (hasLocation) {
            Location location = new Location();
            validatedInput("Enter location X: ", "Invalid X.", getScanner()::nextDouble, location::setX);
            validatedInput("Enter location Y: ", "Invalid Y.", getScanner()::nextDouble, location::setY);
            validatedInput("Enter location Z: ", "Invalid Z.", getScanner()::nextInt, location::setZ);
            validatedInput("Enter location name: ", "Invalid name.", getScanner()::nextCommand, location::setName);
            person.setLocation(location);
        }

        person.setOwner(getProject().getCollectionManager().getOwner());

        getProject().getCollectionManager().add(key, person);
        log.info("Inserted person with key {}: {}", key, person);
    }

    @Override
    public String getCaption() {
        return "Insert a new Person at the specified key.";
    }

    @Override
    public String getMask() {
        return "insert <key>";
    }
}
