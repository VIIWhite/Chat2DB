package ai.chat2db.server.web.api.controller.sql;

import static org.junit.jupiter.api.Assertions.*;

import ai.chat2db.server.tools.base.wrapper.result.DataResult;
import ai.chat2db.server.web.api.controller.sql.request.SqlFormatRequest;
import org.junit.Test;
import org.junit.Before;
public class SqlControllerTest {

    private SqlController sqlController;

    @Before
    public void setUp() {
        // initialize sqlController
        sqlController = new SqlController();
    }

    @Test
    public void testSqlFormat() {
        // Test the SQL formatting feature: Verifies if a standard SQL statement is correctly formatted.
        SqlFormatRequest request = new SqlFormatRequest();
        request.setSql("SELECT * FROM terms ORDER BY terms_due_days DESC;");

        String expectedFormattedSql =
                "SELECT\n" +
                        "  *\n" +
                        "FROM\n" +
                        "  terms\n" +
                        "ORDER BY\n" +
                        "  terms_due_days DESC;";

        DataResult<String> result = sqlController.list(request);
        assertEquals(expectedFormattedSql, result.getData());
    }

    @Test
    public void testSqlWithRightFormat(){
        // The format is already a right format, and no change is expected
        String inputSql =
                "SELECT\n" +
                        "  *\n" +
                        "FROM\n" +
                        "  terms\n" +
                        "ORDER BY\n" +
                        "  terms_due_days DESC;";
        SqlFormatRequest request = new SqlFormatRequest();
        request.setSql(inputSql);

        String expectedFormattedSql =
                "SELECT\n" +
                        "  *\n" +
                        "FROM\n" +
                        "  terms\n" +
                        "ORDER BY\n" +
                        "  terms_due_days DESC;";
        DataResult<String> result = sqlController.list(request);
        assertEquals(expectedFormattedSql, result.getData());
    }

    @Test
    public void testSqlFormatWithEmptySql() {
        // Test handling of empty SQL input: Verifies the method can handle empty strings without errors.
        SqlFormatRequest request = new SqlFormatRequest();
        request.setSql("");
        DataResult<String> result = sqlController.list(request);
        assertEquals("", result.getData());
    }

    @Test
    public void testSqlFormatWithIncorrectSql() {
        // Test formatting with incorrect SQL syntax: Checks how the formatter handles SQL with syntax errors
        SqlFormatRequest request = new SqlFormatRequest();
        request.setSql("SELCT FROM WHERE");
        DataResult<String> result = sqlController.list(request);
        String expectedFormattedSql = "SELCT\n" +
                "FROM\n" +
                "WHERE";

        assertEquals(expectedFormattedSql, result.getData());

    }

    @Test
    public void testSqlFormatWithSpecialCharacters() {
        // Test SQL formatting with special characters: Verifies the formatter's handling of SQL containing special characters.
        SqlFormatRequest request = new SqlFormatRequest();
        request.setSql("...:''&^");
        DataResult<String> result = sqlController.list(request);
        String expectedFormattedSql = "...: '' & ^";

        assertEquals(expectedFormattedSql, result.getData());
    }
}