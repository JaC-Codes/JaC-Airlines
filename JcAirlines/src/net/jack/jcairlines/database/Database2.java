package net.jack.jcairlines.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database2 {

    private final String HOST = "localhost";
    private final int PORT = 3306;
    private final String DATABASE = "jc_airlines";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    private Connection connection;

    public void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" +
                DATABASE + "?useSSL=false", USERNAME, PASSWORD);
    }

    public boolean isConnected() {
        return  connection != null;
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
