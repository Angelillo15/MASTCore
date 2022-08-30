package es.angelillo15.mast.database;

import net.md_5.bungee.api.ChatColor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class PluginConnection {

    private Connection connection;

    private Connection conn;
    private final String type;




    public PluginConnection(String host, int port, String database, String user, String password, String type, String absolutePath, Logger logger) {
        this.type = type;
        if (type.equalsIgnoreCase("SQLite")) {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                String path = absolutePath +"/database.db";
                // db parameters
                String url = "jdbc:sqlite:" + path;
                // create a connection to the database
                conn = DriverManager.getConnection(url);

                logger.info("Connection to SQLite has been established.");

            } catch (SQLException e) {
                logger.warning(ChatColor.translateAlternateColorCodes('&', "&6Error connecting to database"));
            }
        } else {
            try {
                synchronized (this) {
                    Class.forName("com.mysql.jdbc.Driver");
                    this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useUnicode=yes", user, password);
                    logger.info(ChatColor.translateAlternateColorCodes('&', "&b&6Successfully connected to Database"));
                }
            } catch (SQLException | ClassNotFoundException e) {
                logger.warning(ChatColor.translateAlternateColorCodes('&', "&6Error connecting to database"));
            }
        }
    }


    public Connection getConnection() {
        if(type.equalsIgnoreCase("SQLite")){
            return conn;
        }else {
            return connection;
        }
    }

}