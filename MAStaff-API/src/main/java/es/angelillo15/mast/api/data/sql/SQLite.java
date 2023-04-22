package es.angelillo15.mast.api.data.sql;

import es.angelillo15.mast.api.database.PluginConnection;

import java.sql.PreparedStatement;

public class SQLite extends CommonSQL {
    @Override
    public void userDataTable() {
        try (PreparedStatement statement =
                     PluginConnection.getConnection().prepareStatement("CREATE TABLE 'user_data' (\n" +
                             "   'ID'   INTEGER,\n" +
                             "   'username' TEXT,\n" +
                             "   'UUID'  TEXT,\n" +
                             "   'last-ip'   TEXT,\n" +
                             "   'reg-ip' TEXT,\n" +
                             "   'first_login'    INTEGER,\n" +
                             "   'last_login' INTEGER,\n" +
                             "   PRIMARY KEY('ID' AUTOINCREMENT)\n" +
                             ");")) {
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
