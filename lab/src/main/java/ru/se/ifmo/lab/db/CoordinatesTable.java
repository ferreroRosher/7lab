package ru.se.ifmo.lab.db;

import jakarta.inject.Inject;
import ru.se.ifmo.db.DatabaseUtils;
import ru.se.ifmo.db.table.SqlTable;
import ru.se.ifmo.lab.model.Coordinates;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CoordinatesTable extends SqlTable {

    @Inject
    public CoordinatesTable(Connection connection) {
        super(connection);
    }

    public Coordinates insertCoordinates(Coordinates coordinates) {
        String sql = "INSERT INTO coordinates (x, y) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, coordinates.getX());
            preparedStatement.setFloat(2, coordinates.getY());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    coordinates.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            DatabaseUtils.logSQLException(e);
        }
        return coordinates;
    }

    public void updateCoordinates(Coordinates c) {
        String sql = "UPDATE coordinates SET x = ?, y = ? WHERE id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, c.getX());
            ps.setFloat(2, c.getY());
            ps.setLong(3, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            DatabaseUtils.logSQLException(e);
        }
    }


    public Coordinates getById(long id) {
        String sql = "SELECT * FROM coordinates WHERE id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Coordinates c = new Coordinates();
                    c.setId(id);
                    c.setX(rs.getInt("x"));
                    c.setY(rs.getFloat("y"));
                    return c;
                }
            }
        } catch (SQLException e) {
            DatabaseUtils.logSQLException(e);
        }
        return null;
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM coordinates WHERE id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            DatabaseUtils.logSQLException(e);
        }
    }

}
