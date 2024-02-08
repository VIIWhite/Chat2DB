package ai.chat2db.spi.sql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class SQLExecutorTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;
    @Mock
    private ResultSet mockResultSet;

    private SQLExecutor sqlExecutor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sqlExecutor = SQLExecutor.getInstance();
    }

    @Test
    void testExecuteQuery() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.execute(anyString())).thenReturn(true);
        when(mockStatement.getResultSet()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false); // assume a line data
        when(mockResultSet.getString(anyInt())).thenReturn("test result");

        // execute test method
        String result = sqlExecutor.execute(mockConnection, "SELECT * FROM table", rs -> {
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        });

        // verify result
        assertEquals("test result", result);

        // verify if the method is expected
        verify(mockStatement, times(1)).execute("SELECT * FROM table");
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getString(1);
    }

    @Test
    void testExecuteQueryThrowsException() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.execute(anyString())).thenThrow(new SQLException("Database error"));

        assertThrows(RuntimeException.class, () -> {
            sqlExecutor.execute(mockConnection, "SELECT * FROM table", rs -> null);
        });
    }

    @Test
    void testExecuteQueryWithEmptyResultSet() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.execute(anyString())).thenReturn(true);
        when(mockStatement.getResultSet()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // No data

        String result = sqlExecutor.execute(mockConnection, "SELECT * FROM table WHERE id = -1", rs -> {
            return null; // Expecting null because there's no data
        });

        assertNull(result);
    }

}
