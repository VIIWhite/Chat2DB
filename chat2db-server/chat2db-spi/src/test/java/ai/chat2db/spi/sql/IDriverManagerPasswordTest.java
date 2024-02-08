package ai.chat2db.spi.sql;


import java.sql.Connection;
import java.sql.SQLException;

import ai.chat2db.spi.config.DriverConfig;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IDriverManagerPasswordTest {

    private DriverConfig driverConfig;

    @BeforeEach
    public void setUp() {
        driverConfig = new DriverConfig();
        driverConfig.setJdbcDriver("mysql-connector-java-8.0.30.jar");
        driverConfig.setJdbcDriverClass("com.mysql.cj.jdbc.Driver");
        driverConfig.setUrl("jdbc:mysql://localhost:3306/");
    }

    @Test
    public void testConnectionWithCorrectPassword() {
        assertDoesNotThrow(() -> {
            // use correct password
            Connection conn = IDriverManager.getConnection(driverConfig.getUrl(), "root", "000107", driverConfig);
            assertNotNull(conn, "Connection should not be null with correct credentials");
        });
    }

    @Test
    public void testConnectionWithWrongPassword() {
        // use wrong password
        assertThrows(SQLException.class, () -> {
            Connection conn = IDriverManager.getConnection(driverConfig.getUrl(), "root", "000000", driverConfig);
        });
    }


}
