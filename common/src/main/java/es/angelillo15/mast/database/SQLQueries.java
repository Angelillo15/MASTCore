package es.angelillo15.mast.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLQueries {
    public static boolean staffDataCreate(Connection connection){
        try(PreparedStatement statement = connection.prepareStatement("SHOW TABLES LIKE 'staffData';")){
            ResultSet result = statement.executeQuery();
            boolean rb = result.next();
            statement.close();
            return rb;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
    public static void createStaffDataTableSQLite(Connection connection){
        try(PreparedStatement statement = connection.prepareStatement("CREATE TABLE \"staffdata\" (\"ID\"    INTEGER, \"UUID\"    TEXT, \"state\"    INTEGER, PRIMARY KEY(\"ID\" AUTOINCREMENT) );")){
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void createStaffDataTableMySQL(Connection connection){
        try(PreparedStatement statement = connection.prepareStatement("CREATE TABLE `staffdata` (`ID` INT(11) NOT NULL AUTO_INCREMENT, `UUID` VARCHAR(70) NULL DEFAULT '' COLLATE 'utf8mb4_general_ci', `state` INT(1) NULL DEFAULT '0', PRIMARY KEY (`ID`) USING BTREE)")){
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
