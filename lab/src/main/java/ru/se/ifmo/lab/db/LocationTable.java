package ru.se.ifmo.lab.db;

import jakarta.inject.Inject;
import ru.se.ifmo.db.DatabaseUtils;
import ru.se.ifmo.db.table.SqlTable;
import ru.se.ifmo.lab.model.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LocationTable extends SqlTable {

    @Inject
    public LocationTable(Connection connection) {
        super(connection);
    }

    public Location insertLocation(Location location) {
        String sql = "INSERT INTO locations (name) VALUES (?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, location.getName());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    location.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            DatabaseUtils.logSQLException(e);
        }
        return location;
    }

    public void updateLocation(Location l) {
        String sql = "UPDATE locations SET x = ?, y = ?, z = ?, name = ? WHERE id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setDouble(1, l.getX());
            ps.setDouble(2, l.getY());
            ps.setInt(3, l.getZ());
            ps.setString(4, l.getName());
            ps.setLong(5, l.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            DatabaseUtils.logSQLException(e);
        }
    }


    public Location getById(long id) {
        String sql = "SELECT * FROM locations WHERE id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Location l = new Location();
                    l.setId(id);
                    l.setX(rs.getDouble("x"));
                    l.setY(rs.getDouble("y"));
                    l.setZ(rs.getInt("z"));
                    l.setName(rs.getString("name"));
                    return l;
                }
            }
        } catch (SQLException e) {
            DatabaseUtils.logSQLException(e);
        }
        return null;
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM locations WHERE id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            DatabaseUtils.logSQLException(e);
        }
    }
}
