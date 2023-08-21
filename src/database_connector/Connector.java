package database_connector;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/dbMoneyPakyaw?serverTimezone=UTC";
    private static Connector instance;
    private Connection connection;

    private Connector() {
        try {
            connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "[Connector] Error connecting to database.");
        }
    }

    public static Connector getInstance() {
        if (instance == null) {
            instance = new Connector();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}