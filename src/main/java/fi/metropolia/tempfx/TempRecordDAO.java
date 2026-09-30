package fi.metropolia.tempfx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access for the temp_record table.
 */
public class TempRecordDAO {

    /**
     * Inserts a new conversion record.
     *
     * @return the generated id, or -1 if the insert failed.
     */
    public int create(TempRecord record) {
        String sql = "INSERT INTO temp_record (input_value, unit_id, celsius_value) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, record.getInputValue());
            stmt.setInt(2, record.getUnitId());
            stmt.setDouble(3, record.getCelsiusValue());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (Exception e) {
            System.err.println("Error creating record: " + e.getMessage());
        }
        return -1;
    }

    /** @return all records (newest first), joined with their unit code for display. */
    public List<TempRecord> getAll() {
        List<TempRecord> records = new ArrayList<>();
        String sql = "SELECT r.id, r.input_value, r.unit_id, u.code AS unit_code, "
                + "r.celsius_value, r.created_at "
                + "FROM temp_record r JOIN temperature_unit u ON r.unit_id = u.id "
                + "ORDER BY r.id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("created_at");
                records.add(new TempRecord(
                        rs.getInt("id"),
                        rs.getDouble("input_value"),
                        rs.getInt("unit_id"),
                        rs.getString("unit_code"),
                        rs.getDouble("celsius_value"),
                        ts != null ? ts.toString() : ""));
            }
        } catch (Exception e) {
            System.err.println("Error loading records: " + e.getMessage());
        }
        return records;
    }
}
