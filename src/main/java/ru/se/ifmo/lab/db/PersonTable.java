package ru.se.ifmo.lab.db;

import jakarta.inject.Inject;
import ru.se.ifmo.db.DatabaseUtils;
import ru.se.ifmo.db.entity.User;
import ru.se.ifmo.db.table.SqlTable;
import ru.se.ifmo.db.table.UserTable;
import ru.se.ifmo.lab.model.Coordinates;
import ru.se.ifmo.lab.model.Country;
import ru.se.ifmo.lab.model.Location;
import ru.se.ifmo.lab.model.Person;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class PersonTable extends SqlTable {
    private final UserTable userTable;
    private final CoordinatesTable coordinatesTable;
    private final LocationTable locationTable;

    @Inject
    public PersonTable(Connection connection, UserTable userTable, CoordinatesTable coordinatesTable, LocationTable locationTable) {
        super(connection);
        this.userTable = userTable;
        this.coordinatesTable = coordinatesTable;
        this.locationTable = locationTable;
    }

    public Person insertPerson(long key, Person person) {
        return inTransaction(conn -> {
            Coordinates coordinates = coordinatesTable.insertCoordinates(person.getCoordinates());
            Location location = null;
            if (person.getLocation() != null) {
                location = locationTable.insertLocation(person.getLocation());
            }

            String sql = """
            INSERT INTO persons (
                key, name, coordinates_id, creation_date, height, birthday,
                passport_id, location_id, country_id, owner_id
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, key); // Внешний ключ
                ps.setString(2, person.getName());
                ps.setLong(3, coordinates.getId());
                ps.setDate(4, Date.valueOf(person.getCreationDate()));
                ps.setLong(5, person.getHeight());
                ps.setTimestamp(6, Timestamp.valueOf(person.getBirthday().toLocalDateTime()));
                ps.setString(7, person.getPassportID());
                if (location == null) {
                    ps.setNull(8, Types.NULL);
                } else {
                    ps.setLong(8, location.getId());
                }
                ps.setInt(9, person.getNationality().getId());
                ps.setLong(10, person.getOwner().getId());

                int affected = ps.executeUpdate();
                if (affected > 0) {
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) {
                            person.setId(keys.getLong(1));
                        }
                    }
                }
            }
            return person;
        });
    }


    public Person updatePerson(Person person) {
        return inTransaction(conn -> {
            coordinatesTable.updateCoordinates(person.getCoordinates());

            locationTable.updateLocation(person.getLocation());

            String sql = "UPDATE persons SET name = ?, coordinates_id = ?, creation_date = ?, height = ?, birthday = ?, " +
                    "passport_id = ?, location_id = ?, country_id = ?, owner_id = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, person.getName());
                ps.setLong(2, person.getCoordinates().getId());
                ps.setDate(3, Date.valueOf(person.getCreationDate()));
                ps.setLong(4, person.getHeight());
                ps.setTimestamp(5, Timestamp.valueOf(person.getBirthday().toLocalDateTime()));
                ps.setString(6, person.getPassportID());
                ps.setLong(7, person.getLocation().getId());
                ps.setInt(8, person.getNationality().getId());
                ps.setLong(9, person.getOwner().getId());
                ps.setLong(10, person.getId());

                ps.executeUpdate();
            }
            return person;
        });
    }

    public Map<Long, Person> getPersonMap() {
        Map<Long, Person> persons = new LinkedHashMap<>();
        String sql = "SELECT * FROM persons";

        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                long key = rs.getLong("key"); // 👈 ключ из БД
                Person person = new Person();

                person.setId(rs.getLong("id"));
                person.setName(rs.getString("name"));
                person.setCoordinates(coordinatesTable.getById(rs.getLong("coordinates_id")));
                person.setCreationDate(rs.getDate("creation_date").toLocalDate());
                person.setHeight(rs.getLong("height"));
                person.setBirthday(rs.getTimestamp("birthday").toLocalDateTime().atZone(java.time.ZoneId.systemDefault()));
                person.setPassportID(rs.getString("passport_id"));
                person.setLocation(locationTable.getById(rs.getLong("location_id")));
                person.setNationality(Country.values()[rs.getInt("country_id") - 1]);
                person.setOwner(userTable.getById(rs.getLong("owner_id")));

                persons.put(key, person); // 👈 сохраняем по ключу
            }

        } catch (SQLException e) {
            DatabaseUtils.logSQLException(e);
        }

        return persons;
    }

    public void cleanPersons() {
        String sql = "DELETE FROM persons";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DatabaseUtils.logSQLException(e);
        }
    }

    public void deleteById(long id) {
        inTransaction(conn -> {
            String selectSql = "SELECT coordinates_id, location_id FROM persons WHERE id = ?";
            long coordinatesId = -1;
            long locationId = -1;

            try (PreparedStatement select = conn.prepareStatement(selectSql)) {
                select.setLong(1, id);
                try (ResultSet rs = select.executeQuery()) {
                    if (rs.next()) {
                        coordinatesId = rs.getLong("coordinates_id");
                        locationId = rs.getLong("location_id");
                    } else {
                        return null;
                    }
                }
            }

            // Удаляем person
            String deletePersonSql = "DELETE FROM persons WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deletePersonSql)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            }

            coordinatesTable.deleteById(coordinatesId);
            locationTable.deleteById(locationId);

            return null;
        });
    }

}
