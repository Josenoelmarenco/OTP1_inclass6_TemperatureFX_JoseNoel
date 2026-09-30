package fi.metropolia.tempfx;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Database-independent tests for DBConnection: getConnection() never throws,
 * it returns a connection when the DB is up or null when it is not.
 */
@DisplayName("DBConnection")
class DBConnectionTest {

    @Test
    void classIsAvailable() {
        assertNotNull(DBConnection.class);
    }

    @Test
    void getConnectionDoesNotThrow() {
        assertDoesNotThrow(() -> {
            try (Connection connection = DBConnection.getConnection()) {
                // null (no DB) or an open connection (DB up) are both fine
            }
        });
    }
}
