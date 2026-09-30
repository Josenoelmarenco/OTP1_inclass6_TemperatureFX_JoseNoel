package fi.metropolia.tempfx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides a JDBC connection to the MariaDB "tempfx" database.
 *
 * <p>The host is read from the {@code db.host} system property so the same code
 * works locally ("localhost") and inside Jenkins/Docker ("host.docker.internal").
 */
public class DBConnection {

    private static final String DB_HOST = System.getProperty("db.host", "localhost");
    private static final String URL = "jdbc:mariadb://" + DB_HOST + ":3306/tempfx";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    /**
     * @return an open connection, or {@code null} if the database is unreachable
     *         (the error is logged, never thrown, so the GUI can still start).
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Connection failed! " + e.getMessage());
            return null;
        }
    }
}
