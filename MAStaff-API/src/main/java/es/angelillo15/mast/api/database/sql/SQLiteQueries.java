package es.angelillo15.mast.api.database.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLiteQueries extends CommonQueries {
    private static Connection connection;

    public SQLiteQueries() {
        SQLiteQueries.connection = CommonQueries.getConnection();
    }
    @Override
    public void createTables() {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS \"staffdata\" (\"ID\"    INTEGER, \"UUID\"    TEXT, \"state\"    INTEGER, \"vanished\"    INTEGER, PRIMARY KEY(\"ID\" AUTOINCREMENT) );")) {
            statement.executeUpdate();
        } catch (SQLException ignored) {

        }
    }
}
