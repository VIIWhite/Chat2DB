package ai.chat2db.spi.sql;


import ai.chat2db.spi.config.DriverConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class IDriverManagerPortTest {

    //change the user and password of your database before testing

    @Test
    public void testConnectionWithCorrectPort() {
        DriverConfig driverConfig = new DriverConfig();
        driverConfig.setJdbcDriver("mysql-connector-java-8.0.30.jar");
        driverConfig.setJdbcDriverClass("com.mysql.cj.jdbc.Driver");

        // use correct port
        driverConfig.setUrl("jdbc:mysql://localhost:3306/");


        assertDoesNotThrow(() -> {
            Connection conn = IDriverManager.getConnection(driverConfig.getUrl(), "root", "000107", driverConfig);
            assertNotNull(conn, "Connection should not be null with correct credentials");
        });
    }

    @Test
    public void testConnectionWithWrongPort1() {
        DriverConfig driverConfig = new DriverConfig();
        driverConfig.setJdbcDriver("mysql-connector-java-8.0.30.jar");
        driverConfig.setJdbcDriverClass("com.mysql.cj.jdbc.Driver");

        // use wrong port
        driverConfig.setUrl("jdbc:mysql://localhost:3307/");

        assertThrows(SQLException.class, () -> {
            Connection conn = IDriverManager.getConnection(driverConfig.getUrl(), "root", "000107", driverConfig);
        });
    }

    @Test
    public void testConnectionWithWrongPort2() {
        DriverConfig driverConfig = new DriverConfig();
        driverConfig.setJdbcDriver("mysql-connector-java-8.0.30.jar");
        driverConfig.setJdbcDriverClass("com.mysql.cj.jdbc.Driver");

        // use wrong port with a letter
        driverConfig.setUrl("jdbc:mysql://localhost:x/");

        assertThrows(SQLException.class, () -> {
            Connection conn = IDriverManager.getConnection(driverConfig.getUrl(), "root", "000107", driverConfig);
        });
    }

    @Test
    public void testConnectionWithWrongPort3() {
        DriverConfig driverConfig = new DriverConfig();
        driverConfig.setJdbcDriver("mysql-connector-java-8.0.30.jar");
        driverConfig.setJdbcDriverClass("com.mysql.cj.jdbc.Driver");

        // use wrong port, which exceeds the valid range of ports
        driverConfig.setUrl("jdbc:mysql://localhost:65536/");

        assertThrows(SQLException.class, () -> {
            Connection conn = IDriverManager.getConnection(driverConfig.getUrl(), "root", "000107", driverConfig);
        });
    }

    @Test
    public void testConnectionWithWrongPort4() {
        DriverConfig driverConfig = new DriverConfig();
        driverConfig.setJdbcDriver("mysql-connector-java-8.0.30.jar");
        driverConfig.setJdbcDriverClass("com.mysql.cj.jdbc.Driver");

        // use wrong port, which is a reserved port by the system
        driverConfig.setUrl("jdbc:mysql://localhost:1023/");

        assertThrows(SQLException.class, () -> {
            Connection conn = IDriverManager.getConnection(driverConfig.getUrl(), "root", "000107", driverConfig);
        });
    }


    @Test
    public void testConnectionWithNegativePort() {
        DriverConfig driverConfig = new DriverConfig();
        driverConfig.setJdbcDriver("mysql-connector-java-8.0.30.jar");
        driverConfig.setJdbcDriverClass("com.mysql.cj.jdbc.Driver");

        // use wrong port, with a negative number
        driverConfig.setUrl("jdbc:mysql://localhost:-1/");

        assertDoesNotThrow(() -> {
            Connection conn = IDriverManager.getConnection(driverConfig.getUrl(), "root", "000107", driverConfig);
            assertNotNull(conn, "Connection should not be null with correct credentials");
        });
    }

    @Test
    public void testConnectionWithNullPort() {
        DriverConfig driverConfig = new DriverConfig();
        driverConfig.setJdbcDriver("mysql-connector-java-8.0.30.jar");
        driverConfig.setJdbcDriverClass("com.mysql.cj.jdbc.Driver");

        // use null port
        driverConfig.setUrl("jdbc:mysql://localhost:/");


        assertDoesNotThrow(() -> {
            Connection conn = IDriverManager.getConnection(driverConfig.getUrl(), "root", "000107", driverConfig);
            assertNotNull(conn, "Connection should not be null with correct credentials");
        });
    }


}
