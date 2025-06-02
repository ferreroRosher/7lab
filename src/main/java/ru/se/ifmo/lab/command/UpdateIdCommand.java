package ru.se.ifmo.lab.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.se.ifmo.cli.CommandAction;
import ru.se.ifmo.cli.DefaultCommand;
import ru.se.ifmo.lab.model.Coordinates;
import ru.se.ifmo.lab.model.Country;
import ru.se.ifmo.lab.model.Location;
import ru.se.ifmo.lab.model.Person;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

public class UpdateIdCommand extends DefaultCommand {
    private static final Logger log = LoggerFactory.getLogger(UpdateIdCommand.class);

    @CommandAction
    public void update(Long id) {
        var collection = getProject().getCollectionManager().getMap().values().stream()
                .map(e -> (Person) e)
                .toList();

        Person person = collection.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (person == null) {
            log.warn("No Person with id = {}", id);
            return;
        }

        Field field = promptForField(Person.class);
        field.setAccessible(true);

        try {
            switch (field.getName()) {
                case "name" ->
                        validatedInput("Enter name: ", "Invalid name.", getScanner()::nextCommand, person::setName);
                case "height" ->
                        validatedInput("Enter height (> 0): ", "Invalid height.", getScanner()::nextLong, person::setHeight);
                case "passportID" ->
                        validatedInput("Enter passport ID (6–33 chars): ", "Invalid passport ID.", getScanner()::nextCommand, person::setPassportID);
                case "birthday" -> person.setBirthday(ZonedDateTime.now());
                case "creationDate" -> person.setCreationDate(LocalDate.now());

                case "coordinates" -> {
                    Coordinates coords = person.getCoordinates() == null ? new Coordinates() : person.getCoordinates();
                    validatedInput("Enter X (≤ 369): ", "Invalid X.", getScanner()::nextInt, coords::setX);
                    validatedInput("Enter Y (> -983): ", "Invalid Y.", getScanner()::nextFloat, coords::setY);
                    person.setCoordinates(coords);
                }

                case "location" -> {
                    Location loc = person.getLocation() == null ? new Location() : person.getLocation();
                    validatedInput("Enter X: ", "Invalid X.", getScanner()::nextDouble, loc::setX);
                    validatedInput("Enter Y: ", "Invalid Y.", getScanner()::nextDouble, loc::setY);
                    validatedInput("Enter Z: ", "Invalid Z.", getScanner()::nextInt, loc::setZ);
                    validatedInput("Enter name: ", "Invalid name.", getScanner()::nextCommand, loc::setName);
                    person.setLocation(loc);
                }

                case "nationality" -> {
                    log.info("Available nationalities: {}", enumOptions(Country.class));
                    validatedInput(
                            "Enter nationality: ",
                            "Invalid nationality.",
                            () -> getScanner().parseEnum(Country.class, getScanner().nextCommand()),
                            person::setNationality
                    );
                }

                default -> log.warn("Unsupported field: {}", field.getName());
            }
            try {
                Long key = Objects.requireNonNull(getProject().getCollectionManager().getMap()
                                .entrySet()
                                .stream()
                                .filter(es -> es.getValue().getId() == id)
                                .findFirst()
                                .orElse(null))
                        .getKey();
                getProject().getCollectionManager().update(key, person);
            } catch (SecurityException e) {
                log.error("Security exception: {}", e.getMessage());
                return;
            }
            log.info("Person with id = {} successfully updated.", person.getId());

        } catch (Exception e) {
            log.error("Failed to update field: {}", field.getName(), e);
        }
    }

    @Override
    public String getCaption() {
        return "Update a specific field of a Person by ID.";
    }

    @Override
    public String getMask() {
        return "update <id>";
    }
}


