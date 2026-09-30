package fi.metropolia.tempfx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access for the temperature_unit table.
 */
public class TemperatureUnitDAO {

    /** @return all units, or an empty list if the database is unreachable. */
    public List<TemperatureUnit> getAll() {
        List<TemperatureUnit> units = new ArrayList<>();
        String sql = "SELECT id, code, name FROM temperature_unit ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                units.add(new TemperatureUnit(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("name")));
            }
        } catch (Exception e) {
            System.err.println("Error loading units: " + e.getMessage());
        }
        return units;
    }

    /** @return the unit with the given code, or {@code null} if not found. */
    public TemperatureUnit findByCode(String code) {
        String sql = "SELECT id, code, name FROM temperature_unit WHERE code = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, code);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TemperatureUnit(
                            rs.getInt("id"),
                            rs.getString("code"),
                            rs.getString("name"));
                }
            }
        } catch (Exception e) {
            System.err.println("Error finding unit: " + e.getMessage());
        }
        return null;
    }
}
